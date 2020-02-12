package com.hhekj.btc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhekj.btc.mapper.UserAccountMapper;
import com.hhekj.btc.model.DigitalCoin;
import com.hhekj.btc.model.SysPayment;
import com.hhekj.btc.model.UserAccount;
import com.hhekj.btc.service.DigitalCoinService;
import com.hhekj.btc.service.SysPaymentService;
import com.hhekj.btc.service.UserAccountService;
import com.hhekj.btc.tool.NewDateKit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Author: JianXin
 * Description:
 * Date: 2019-10-30 20:00
 **/

@Service
public class UserAccountServiceImpl
        extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {

    @Resource
    private UserAccountMapper mapper;


    @Resource
    private DigitalCoinService coinService;



    @Resource
    private SysPaymentService sysPaymentService;


    /**
     * 新增用户账户
     * <p>
     * 2019-11-09 12:03 hux ++
     *
     * @param userAccount 账户信息
     */
    @Override
    public void idempotentSave(UserAccount userAccount) {
        //如果账户不存在才创建
        if (!isExistsAccount(userAccount.getUserId(), userAccount.getType(), userAccount.getCoinId())) {
            save(userAccount);
        }
    }

    /**
     * 指定的类型的和币种的账户是否存在
     * <p>
     * 2019-11-09 12:06 hux ++
     *
     * @param userId 用户id
     * @param type   类型
     * @param coinId 币种
     */
    @Override
    public boolean isExistsAccount(Integer userId, Integer type, Integer coinId) {
        return lambdaQuery()
                .eq(UserAccount::getType, type)
                .eq(UserAccount::getUserId, userId)
                .eq(UserAccount::getCoinId, coinId).count() > 0;
    }

    @Override
    public UserAccount find(QueryWrapper<UserAccount> queryWrapper) {
        return mapper.selectOne(queryWrapper);
    }


}
