package com.hhekj.btc.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hhekj.btc.model.BtcWalletAccount;
import com.hhekj.btc.model.DigitalCoin;
import com.hhekj.btc.model.DigitalCoinAddress;
import com.hhekj.btc.model.User;
import com.hhekj.btc.service.DigitalCoinAddressService;
import com.hhekj.btc.service.UserService;
import com.hhekj.btc.tool.BTCScanTool;
import com.hhekj.btc.tool.KeyTool;
import com.hhekj.btc.tool.NewDateKit;
import com.hhekj.btc.tool.WalletTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
@Slf4j
public class BTCAddressTask {



    // 主链类型
    private static final String main = "BTC";

    @Resource
    private DigitalCoinAddressService coinAddressService;

    @Resource
    private UserService userService;

    // 收款地址生成器
    @Async
    @Scheduled(fixedDelay=5000)
    public void EthAddressBuilder() {
        try{
            QueryWrapper<DigitalCoinAddress> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("main", main);
            queryWrapper.eq("user_id", 0);
            List<DigitalCoinAddress> digitalCoinAddresses = coinAddressService.findAll(queryWrapper);
            if (digitalCoinAddresses.size() >= 30){
                return;
            }
            String m = WalletTool.generateMnemonics(); //助记词

            BtcWalletAccount account = WalletTool.createBtcWallet(m);
            log.info(account+"");
            DigitalCoinAddress coinAddress = new DigitalCoinAddress();
            coinAddress.setAddress(account.getAddress());
            coinAddress.setMain(main);

            String privateKey = account.getPrivateKey();

            privateKey = KeyTool.privateKeyEncode("",privateKey); //私钥加密
            coinAddress.setPrivateKey(privateKey);
            coinAddress.setPublicKey(account.getPublicKey());
            coinAddress.setCreateTime(NewDateKit.now());
//            coinAddress.setMnemonics(KeyTool.base64Encode(m)); //助记词加密
            coinAddressService.save(coinAddress);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     * 导入私钥
     * */
    @Async
//  @Scheduled(cron = "0 0 */3 * * ?")
    public void importKey() {
        try{
            QueryWrapper<DigitalCoinAddress> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("main", main);
            queryWrapper.eq("salt", 0);
            List<DigitalCoinAddress> digitalCoinAddresses = coinAddressService.findAll(queryWrapper);
            if (digitalCoinAddresses.size() == 0)
                return;
            DigitalCoinAddress digitalCoinAddress = digitalCoinAddresses.get(0);
            String privateKey = digitalCoinAddress.getPrivateKey();
            privateKey = KeyTool.privateKeyDecode(privateKey);
            log.info("导入 "+privateKey);
            BTCScanTool.importPrivKey(privateKey);
            digitalCoinAddress.setSalt(1);
            coinAddressService.updateById(digitalCoinAddress);
        }catch (Exception e){
            e.printStackTrace();
        }

    }





    // 收款地址绑定用户
    @Async
    @Scheduled(fixedDelay=5000)
    public void EthAddressForUser() {
        try{
            QueryWrapper<DigitalCoinAddress> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("main", main);
            queryWrapper.eq("user_id", 0);
            List<DigitalCoinAddress> digitalCoinAddressess = coinAddressService.findAll(queryWrapper);
            if (digitalCoinAddressess.size() <= 0){
                return;
            }

            List<User> users = userService.list();
            for (User user : users){
                QueryWrapper<DigitalCoinAddress> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("main", main);
                queryWrapper2.eq("user_id", user.getId());
                DigitalCoinAddress digitalCoinAddresses = coinAddressService.find(queryWrapper2);
                if (digitalCoinAddresses == null && digitalCoinAddressess.size() != 0){
                    DigitalCoinAddress digitalCoinAddress = digitalCoinAddressess.get(0);
                    digitalCoinAddress.setUserId(user.getId());
                    coinAddressService.updateById(digitalCoinAddress);
                    digitalCoinAddressess.remove(0);
                }
            }



        }catch (Exception e){
            e.printStackTrace();
        }

    }





}
