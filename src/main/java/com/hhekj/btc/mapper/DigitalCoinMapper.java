package com.hhekj.btc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hhekj.btc.common.DTConst;
import com.hhekj.btc.model.DigitalCoin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DigitalCoinMapper extends BaseMapper<DigitalCoin> {


    /**
     * 根据id获取代币信息
     *
     * @param id 代币id
     */
    @Select("select * from " + DTConst.Coin + " where id=#{id} and status=1 and isdel=0 limit 1")
    DigitalCoin findById(@Param("id") Integer id);

    /**
     * 根据id获取法币信息
     *
     * @param id 法币id
     */
    @Select("select * from " + DTConst.Coin + " where id=#{id} and type=2 and status=1 and isdel=0 limit 1")
    DigitalCoin findFbById(@Param("id") Integer id);

    /**
     * 查找代币名称
     *
     * @param id 代币id
     */
    @Select("select title from " + DTConst.Coin + " where id=#{id} and status=1 and isdel=0 limit 1")
    String findTitleById(@Param("id") Integer id);

    /**
     * 查找代币名称
     *
     * @param id 代币id
     */
    @Select("select symbol from " + DTConst.Coin + " where id=#{id} and status=1 and isdel=0 limit 1")
    String findSymbolById(@Param("id") Integer id);

    /**
     * 查找所有法币名称
     */
    @Select("select * from " + DTConst.Coin + " where type=2 and status=1 and isdel=0")
    List<DigitalCoin> getDigitalCoinType();

    /**
     * 根据id获取代币信息
     *
     * @param id     代币id
     * @param fields 字段值
     */
    @Select("select ${fields} from " + DTConst.Coin + " where id=#{id} limit 1")
    DigitalCoin findFieldsById(@Param("id") Integer id, @Param("fields") String fields);

    /**
     * 获取主链代币id
     * @param main 主链类型
     */
    @Select("select id from "+DTConst.Coin+" where main=#{main} and symbol=#{main} and type=1 limit 1")
    Integer findIdByMain(@Param("main") String main);

}