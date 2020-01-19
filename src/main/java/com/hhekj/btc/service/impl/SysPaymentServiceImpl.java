package com.hhekj.btc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hhekj.btc.mapper.SysPaymentMapper;
import com.hhekj.btc.model.SysPayment;
import com.hhekj.btc.service.SysPaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Author: JianXin
 * Description:
 * Date: 2019-11-01 11:35
 **/
@Service
public class SysPaymentServiceImpl implements SysPaymentService {

    @Resource
    private SysPaymentMapper mapper;

    @Override
    public SysPayment colletAddress(String main) {
        QueryWrapper<SysPayment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",1);
        queryWrapper.eq("main",main);
        return mapper.selectOne(queryWrapper);
    }

    @Override
    public SysPayment expendAddress(String main) {
        QueryWrapper<SysPayment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",2);
        queryWrapper.eq("main",main);
        return mapper.selectOne(queryWrapper);
    }
}
