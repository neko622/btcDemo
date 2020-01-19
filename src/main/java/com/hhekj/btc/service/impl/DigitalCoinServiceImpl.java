package com.hhekj.btc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhekj.btc.mapper.DigitalCoinMapper;
import com.hhekj.btc.model.DigitalCoin;
import com.hhekj.btc.service.DigitalCoinService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: JianXin
 * Description:
 * Date: 2019-10-31 10:20
 **/
@Service
public class DigitalCoinServiceImpl
        extends ServiceImpl<DigitalCoinMapper, DigitalCoin> implements DigitalCoinService {

    @Resource
    private DigitalCoinMapper mapper;

    @Override
    public List<DigitalCoin> list(QueryWrapper<DigitalCoin> queryWrapper) {
        queryWrapper.eq("isdel", 0);
        queryWrapper.eq("status", 1);
        return mapper.selectList(queryWrapper);
    }


    @Override
    public DigitalCoin findById(Integer id, String... fields) {
        return mapper.findFieldsById(id, String.join(",", fields));
    }

    /**
     * 根据合约地址查询
     * 2019-11-09 9:21 hux ++
     *
     * @param address 合约地址
     */
    @Override
    public DigitalCoin findByAddress(String address) {
        return lambdaQuery().select(DigitalCoin::getId,DigitalCoin::getOpen,DigitalCoin::getInApproveLimit,DigitalCoin::getOutApproveLimit).eq(DigitalCoin::getAddress, address).eq(DigitalCoin::getType,1).one();
    }

    @Override
    public Integer findIdByMain(String main) {
        return mapper.findIdByMain(main);
    }
}
