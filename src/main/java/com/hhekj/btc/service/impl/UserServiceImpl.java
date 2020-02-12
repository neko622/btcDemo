package com.hhekj.btc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhekj.btc.mapper.UserMapper;
import com.hhekj.btc.model.User;
import com.hhekj.btc.service.UserService;
import com.hhekj.btc.tool.NewDateKit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: JianXin
 * Description:
 * Date: 2019-10-28 16:05
 **/

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {


    @Resource
    private UserMapper mapper;





    @Override
    public boolean updateById(User user) {
        return mapper.updateById(user) > 0;
    }

    @Override
    public User findById(Integer userId) {
        return mapper.selectById(userId);
    }



}
