package com.hhekj.btc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhekj.btc.model.SysDictionary;

/**
 * anther : hux
 * datetime : 2019/11/8 14:49
 * description :
 */
public interface ISysDictionaryService extends IService<SysDictionary> {


    /**
     * 获取以太坊扫描过的最新区块号
     */
    Integer findBtcLatestScanBlock();

    /**
     * 修改以太坊扫描过的最新区块号
     * @param num 区块号
     */
    boolean updateBtcLatestScanBlock(Integer num);

}
