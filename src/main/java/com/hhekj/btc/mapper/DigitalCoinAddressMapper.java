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
    @Select("select user_id from btc_coin_address where address = #{address} ")
    Integer findUserIdByAddress(@Param("address") String address);


}