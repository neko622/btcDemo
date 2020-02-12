package com.hhekj.btc.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hhekj.btc.model.*;
import com.hhekj.btc.service.*;
import com.hhekj.btc.tool.BTCScanTool;
import com.hhekj.btc.tool.NewDateKit;
import jnr.ffi.annotations.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Author: Angel
 * Description: 比特币扫块，用于监听用户的充值
 * Date: 2020-01-13 15:53
 **/
@Slf4j
@Configuration
@Transactional(rollbackFor = Throwable.class)
public class BTCRechargeScanBlockTask {

    @Resource
    private ISysDictionaryService dictionaryService;

    @Resource
    private DigitalCoinService digitalCoinService;

    @Resource
    private DigitalCoinAddressService digitalCoinAddressService;

    private static final  String BTC_COIN_ADDRESS = "BTC";

    private static final Integer IS_OPEN_RECHARGE_OK = 1;

    @Resource
    private IBlockTransferIntoService blockTransferIntoService;

    @Resource
    private UserAccountService userAccountService;


    /**
     * 间隔指定的时间去以太坊网络查询有没有本平台的用户充值记录，有的话就只管保存下来，会有另外的任务去对账
     */
//    @Scheduled(cron = "*/1 * * * * ?")
    @Async
    @Scheduled(fixedDelay=3*60*1000)
    public  void scan() {
        log.info("扫块");
        BtcBlock newBlock = BTCScanTool.scanNewBlock();
        BigInteger nowBlockNum = new BigInteger(newBlock.getHeight()); //当前区块高度
        Integer latestScanBlockNum = dictionaryService.findBtcLatestScanBlock(); //扫描过的最新区块号

        log.info("保存的高度： "+latestScanBlockNum);
        //如果上一次的区块高度是null的话就从最新的开始扫
        if (null == latestScanBlockNum ) {
            //修改为最新区块号码
            dictionaryService.updateBtcLatestScanBlock(nowBlockNum.intValue());
            return;
        }
        int futureBlockNum = nowBlockNum.intValue() + 1; // 扫描的高度

        if(futureBlockNum - latestScanBlockNum.intValue() > 20){
            futureBlockNum = latestScanBlockNum.intValue()+20;
        }

        for (int i = latestScanBlockNum.intValue(); i < futureBlockNum; i++) {
            //根据区块号获取区块数据
            BtcBlock block = BTCScanTool.scanNumberBlock(BigInteger.valueOf(i));
            if (null == block) {
                continue;
            }
            List<String> txids = block.getTx();
            //遍历区块中的所有交易
            if(txids == null){
                continue;
            }
            for(String txId : txids){
                BtcTransaction transaction = BTCScanTool.scanTxidTransaction(txId);
                if (transaction != null){

                    ArrayList<BlockOut> arrayList = transaction.getVout();
                    for (BlockOut out : arrayList){
                        BigDecimal value = out.getValue(); //金额
                        ArrayList<String> addess = out.getScriptPubKey().getAddresses();
                        if (addess != null && addess.size() != 0){ //address为null则为挖矿所得
                            for (String s :addess){

//                                //判断收款地址是否在本平台(与数据库的钱包地址比对)，不在就return
                                Integer userId = digitalCoinAddressService.findUserIdByAddress(s);

                                if (null == userId) {
                                    continue;
                                }
                                System.out.println(userId);

                                //查询收款地址在本平台是否存在,如果不存在的话就直接跳过
                                DigitalCoin coin = digitalCoinService.findByAddress(BTC_COIN_ADDRESS);
                                if (null == coin) {
                                    log.info("充值的币种不支持，币种合约地址：{}", BTC_COIN_ADDRESS);
                                    return;
                                }
                                if (!Objects.equals(IS_OPEN_RECHARGE_OK, coin.getOpen())) {
                                    log.info("充值的币种暂时没有开放充值");
                                    return;
                                }
                                log.info(s+"  address");
                                BlockTransferInto into = new BlockTransferInto();
                                into.setToId(userId);
                                into.setAmount(value);
                                into.setToAddress(s);
                                into.setCoinId(coin.getId());
                                into.setCreateTime(NewDateKit.now());
                                into.setCollect(0);
                                into.setTxHash(txId);
                                into.setStatus(1);
                                into.setAudit("ok");
                                into.setFromAddress("");

                                QueryWrapper<BlockTransferInto> wrapper = new QueryWrapper<>();
                                wrapper.eq("tx_hash", txId);
                                wrapper.eq("coin_id", coin.getId());
                                BlockTransferInto transferInto = blockTransferIntoService.find(wrapper);
                                if (transferInto != null){
                                    continue;
                                }

                                boolean bool = blockTransferIntoService.save(into); // 保存充值记录

                                UserAccount userAccount = new UserAccount();
                                userAccount.setCoinId(coin.getId());
                                userAccount.setType(1);
                                userAccount.setUserId(userId);
                                userAccount.setCreateTime(NewDateKit.now());
                                userAccountService.idempotentSave(userAccount);

                                QueryWrapper<UserAccount> queryWrapper = new QueryWrapper<>();
                                queryWrapper.eq("coin_id", coin.getId());
                                queryWrapper.eq("user_id", userId);
                                queryWrapper.eq("type", 1);
                                UserAccount user = userAccountService.find(queryWrapper);
                                BigDecimal total = user.getAmount().add(value);
                                user.setAmount(total);
                                userAccountService.updateById(user);//修改用户金额

                            }
                        }
                    }
                }

            }
        }
        //扫描完成后把数据库的区块号改成最新的
        boolean isSuccess = dictionaryService.updateBtcLatestScanBlock(nowBlockNum.intValue());
        log.info("now "+nowBlockNum.intValue());
//        if(!isSuccess){
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//        }

    }

}
