package com.hhekj.btc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hhekj.btc.common.DTConst;
import com.hhekj.btc.model.DigitalCoinAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DigitalCoinAddressMapper extends BaseMapper<DigitalCoinAddress> {

    /**
     * 根据钱包地址查询用户id
     *
     * @param address 钱包地址
     */
    Integer findUserIdByAddress(@Param("address") String address);

    /**
     * 查询没有区块链地址的用户id
     *
     * @param main 主链类型
     */
    @Select("SELECT id FROM " + DTConst.User + " WHERE id NOT IN ( SELECT user_id FROM " + DTConst.CoinAddress + " where main=#{main})")
    List<Integer> noneAddressUserId(@Param("main") String main);

    /**
     * 绑定地址
     *
     * @param userId 用户id
     * @param main   主链类型
     */
    @Update("update " + DTConst.CoinAddress + " set user_id=#{userId} where user_id=0 and main=#{main} limit 1")
    Boolean bindAddress(@Param("userId") Integer userId, @Param("main") String main);

    /**
     * 获取某个主链的区块链地址
     * @param userId 用户id
     * @param main 主链类型
     */
    @Select("select "+DTConst.CoinAddress+" where user_id=#{userId} and main=#{main} limit 1")
    String findAddressById(@Param("userId") Integer userId, @Param("main") String main);
}