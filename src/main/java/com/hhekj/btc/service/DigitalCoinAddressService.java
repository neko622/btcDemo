package com.hhekj.btc.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hhekj.btc.model.DigitalCoinAddress;

import java.util.List;

/**
 * Author: JianXin
 * Description:
 * Date: 2019-10-31 19:57
 **/

public interface DigitalCoinAddressService extends IService<DigitalCoinAddress> {

    /**
     * 根据钱包地址查询用户id
     *
     * @param address 钱包地址
     */
    Integer findUserIdByAddress(String address);

   /**
     * 查找单条数据
     * @param queryWrapper 条件组
     */
    DigitalCoinAddress find(QueryWrapper<DigitalCoinAddress> queryWrapper);


    List<DigitalCoinAddress> findAll(QueryWrapper<DigitalCoinAddress> queryWrapper);

}
