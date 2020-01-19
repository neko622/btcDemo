package com.hhekj.btc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhekj.btc.mapper.DigitalCoinAddressMapper;
import com.hhekj.btc.model.DigitalCoinAddress;
import com.hhekj.btc.service.DigitalCoinAddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: JianXin
 * Description:
 * Date: 2019-10-31 19:57
 **/
@Service
public class DigitalCoinAddressServiceImpl
        extends ServiceImpl<DigitalCoinAddressMapper, DigitalCoinAddress> implements DigitalCoinAddressService {

    @Resource
    private DigitalCoinAddressMapper mapper;

    /**
     * 根据钱包地址查询用户id
     *
     * @param address 钱包地址
     */
    @Override
    public Integer findUserIdByAddress(String address) {
        return mapper.findUserIdByAddress(address);
    }


    @Override
    public DigitalCoinAddress find(QueryWrapper<DigitalCoinAddress> queryWrapper) {
        return mapper.selectOne(queryWrapper);
    }

}
