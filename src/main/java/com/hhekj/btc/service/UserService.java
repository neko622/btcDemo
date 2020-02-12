package com.hhekj.btc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhekj.btc.model.User;

/**
 * Author: JianXin
 * Description:
 * Date: 2019-10-28 16:04
 **/

public interface UserService extends IService<User> {



    /**
     * 根据id更新用户信息
     * @param user 用户实体
     * @return boolean
     */
    boolean updateById(User user);

    /**
     * 根据用户id获取用户信息
     * @param userId 用户id
     * @return User
     */
    User findById(Integer userId);




}
