package com.hhekj.btc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhekj.btc.mapper.SysDictionaryMapper;
import com.hhekj.btc.model.SysDictionary;
import com.hhekj.btc.service.ISysDictionaryService;
import org.springframework.stereotype.Service;

/**
 * anther : hux
 * datetime : 2019/11/8 14:49
 * description :
 */
@Service
public class SysDictionaryServiceImpl
        extends ServiceImpl<SysDictionaryMapper, SysDictionary> implements ISysDictionaryService {

    /**
     * 数据库中以太坊扫块的时候扫过的最新的区块号对应的name
     */
    private final String btcScanLatestBlockNum = "btc_scan_latest_block_num";

    /**
     * 获取BTC扫描过的最新区块号
     */
    @Override
    public Integer findBtcLatestScanBlock() {
        return Integer.valueOf(lambdaQuery().eq(SysDictionary::getName, btcScanLatestBlockNum).one().getValue());
    }

    /**
     * 修改以太坊扫描过的最新区块号
     *
     * @param num 区块号
     */
    @Override
    public boolean updateBtcLatestScanBlock(Integer num) {
        SysDictionary dictionary = new SysDictionary();
        dictionary.setValue(num.toString());
        return lambdaUpdate().eq(SysDictionary::getName, btcScanLatestBlockNum).update(dictionary);
    }
}
