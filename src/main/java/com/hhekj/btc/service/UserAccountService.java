package com.hhekj.btc.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hhekj.btc.model.DigitalCoinAddress;
import com.hhekj.btc.model.UserAccount;

import java.math.BigDecimal;
import java.util.Map;


/**
 * Author: JianXin
 * Description:
 * Date: 2019-10-30 20:00
 **/

public interface UserAccountService extends IService<UserAccount> {



    /**
     * 新增用户账户
     * <p>
     * 2019-11-09 12:03 hux ++
     *
     * @param userAccount 账户信息
     */
    void idempotentSave(UserAccount userAccount);

    /**
     * 指定的类型的和币种的账户是否存在
     * <p>
     * 2019-11-09 12:06 hux ++
     *
     * @param userId 用户id
     * @param type   类型
     * @param coinId 币种
     */
    boolean isExistsAccount(Integer userId, Integer type, Integer coinId);



    /**
     * 查找单条数据
     * @param queryWrapper 条件组
     */
    UserAccount find(QueryWrapper<UserAccount> queryWrapper);

}
