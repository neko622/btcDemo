package com.hhekj.btc.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hhekj.btc.common.TxState;
import com.hhekj.btc.model.DigitalCoinAddress;
import com.hhekj.btc.model.MemPoolInfo;
import com.hhekj.btc.service.DigitalCoinAddressService;
import com.hhekj.btc.service.DigitalCoinService;
import com.hhekj.btc.service.IBlockTransferIntoService;
import com.hhekj.btc.service.SysPaymentService;
import com.hhekj.btc.tool.BTCScanTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Author: Angel
 * Description:
 * Date: 2020-01-14 11:00
 **/
@Slf4j
//@Component
@Transactional(rollbackFor = Exception.class)
public class BTCBlockAssetCollectTask {

    @Resource
    private DigitalCoinService coinService;

    @Resource
    private SysPaymentService sysPaymentService;

    @Resource
    private IBlockTransferIntoService intoService;


    @Resource
    private DigitalCoinAddressService addressService;

    private static final String address = "BTC";



    // 资金归集
//    @Scheduled(fixedDelay = 30000)
//    @Scheduled(cron = "*/60 * * * * ?")
    public  void assetCollect()  {
        log.info("归集");
        MemPoolInfo memPoolInfo = BTCScanTool.getMemPoolInfo();
        BigDecimal fees = new BigDecimal(memPoolInfo.getMempoolminfee());//手续费
        System.out.println(fees);

        Integer mainId = coinService.findIdByMain(address);
        // 统计用户未资金归集的数量
        List<com.hhekj.btc.model.BlockTransferInto> list = intoService.userNoneCollectNum();
        if (list.size() <= 0) {
             log.info("没有需要归集的用户");
            return;
        }
        // 获取收付款的账户
        com.hhekj.btc.model.SysPayment expend = sysPaymentService.expendAddress(address);
        if (expend == null) {
             log.error("未获取到付款账户");
            return;
        }

        for (com.hhekj.btc.model.BlockTransferInto into : list) {
            if (Objects.equals(into.getCoinId(), mainId)) {
                continue;
            }
            // 获取代币的归集阈值
            com.hhekj.btc.model.DigitalCoin coin = coinService.findById(into.getCoinId(), "address", "collect_limit", "`decimal`");

            if (coin == null || coin.getCollectLimit() == null) {
                continue;
            }
            // 先判断是否超过归集的设定值
            if (coin.getCollectLimit().compareTo(into.getAmount()) == 1) {
                // 当前拥有数量不超过设定值，不做归集
                continue;
            }
            // 获取用户BTC地址
            QueryWrapper<DigitalCoinAddress> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("main", address);
            queryWrapper.eq("user_id", into.getToId());
            com.hhekj.btc.model.DigitalCoinAddress coinAddress = addressService.find(queryWrapper);
            if (null == coinAddress) {
                 log.warn("用户没有对应的地址,用户id:{}", into.getToId());
                continue;
            }
            if(!BTCScanTool.validBtcAddress(coinAddress.getAddress())){
                log.warn("地址错误");
                continue;
            }

            // 核对用户链上拥有的btc数量
            BigDecimal hasBtc = BTCScanTool.getBalance(coinAddress.getAddress());
            if (hasBtc.compareTo(BigDecimal.ZERO) < 1) {
                // 资金数小于等于0,不做归集
                 log.warn("链上余额为0,不做归集");
                continue;
            }

            //设置交易手续费
            BTCScanTool.setTxFrees(fees.doubleValue());

            String txHash = BTCScanTool.sendToAddress(expend.getAddress(),hasBtc); //btc转账
            // 等待到btc到账后再做归集,更改状态为归集核对中
            boolean isSuccess = intoService.collectAsset(into.getToId(), into.getCoinId(), 3, txHash);
            if (!isSuccess) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
    }


    // 资金归集确认
//    @Scheduled(fixedDelay = 60000)
    public void collectVerify() {
        // 获取需要确认的列表
        try {
            List<Map<String, Object>> verifyList = intoService.collectVerify();
            if (verifyList.size() <= 0) return;
            for (Map<String, Object> map : verifyList) {
                String hash = map.get("collect_hash").toString();
                if (hash == null || hash.equals("")) continue;
                String collect = map.get("collect").toString();
                if (collect.equals("3")) {
                    // 开始对账
                    TxState txState = BTCScanTool.queryTx(hash);
                    switch (txState) {
                        case FAIL: // 归集核对失败,更改状态为等待归集
                            intoService.collectAssetByHash(hash, 0);
                            break;
                        case CONFIRMED: // 归集核对成功
                            intoService.collectAssetByHash(hash, 1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }
}

