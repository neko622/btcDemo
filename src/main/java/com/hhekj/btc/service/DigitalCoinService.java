package com.hhekj.btc.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hhekj.btc.model.DigitalCoin;

import java.util.List;

/**
 * Author: JianXin
 * Description:
 * Date: 2019-10-31 10:20
 **/

public interface DigitalCoinService extends IService<DigitalCoin> {

    /**
     * 获取代币列表数据
     */
    List<DigitalCoin> list(QueryWrapper<DigitalCoin> queryWrapper);


    /**
     * 根据id获取代币信息
     * @param id 代币id
     * @param fields 字段值
     */
    DigitalCoin findById(Integer id, String... fields);

    /**
     * 根据合约地址查询
     * 2019-11-09 9:21 hux ++
     *
     * @param address 合约地址
     */
    DigitalCoin findByAddress(String address);

    /**
     * 获取主链代币id
     * @param main 主链类型
     */
    Integer findIdByMain(String main);


}
