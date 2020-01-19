package com.hhekj.btc.task;

import com.hhekj.btc.model.BtcWalletAccount;
import com.hhekj.btc.model.DigitalCoinAddress;
import com.hhekj.btc.service.DigitalCoinAddressService;
import com.hhekj.btc.tool.KeyTool;
import com.hhekj.btc.tool.NewDateKit;
import com.hhekj.btc.tool.WalletTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
@Slf4j
public class BTCAddressTask {



    // 主链类型
    private static final String main = "BTC";

    @Resource
    private DigitalCoinAddressService coinAddressService;

    // 收款地址生成器,每次生成100个,10s一次检测
//    @Async("EthAddressBuilderTask")
//    @Scheduled(fixedDelay = 10000)
    public void EthAddressBuilder() {
        try{
            String m = WalletTool.generateMnemonics(); //助记词
            log.info(m);
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
            coinAddress.setMnemonics(KeyTool.base64Encode(m)); //助记词加密
            coinAddressService.save(coinAddress);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
