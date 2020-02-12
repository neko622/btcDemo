package com.hhekj.btc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hhekj.btc.common.DTConst;
import com.hhekj.btc.model.UserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Author: JianXin
 * Description:
 * Date: 2019-10-30 19:59
 **/
@Mapper
public interface UserAccountMapper extends BaseMapper<UserAccount> {


    /**
     * 币币账户资产
     *
     * @param userId 用户id
     */
    @Select("select * from " + DTConst.UserAccount + " where user_id=#{userId} and type=1")
    List<UserAccount> bbAsset(@Param("userId") Integer userId);


    /**
     * 币币账户某一代币资产明细
     *
     * @param userId 用户id
     * @param coinId 代币id
     */
    @Select("(select 1 as type,amount,`status`,create_time from " + DTConst.BlockInto + " where to_id=#{userId} and coin_id=#{coinId})" +
            " union all " +
            "(select 2 as type,amount,`status`,create_time from " + DTConst.BlockOut + " where to_id=#{userId}  and coin_id=#{coinId})" +
            " limit #{start},#{limit}")
    List<Map<String, Object>> bbAccountRecord(@Param("userId") Integer userId, @Param("coinId") Integer coinId, @Param("start") Integer start, @Param("limit") Integer limit);


    /**
     * 币币账户某一代币资产转入明细
     *
     * @param userId 用户id
     * @param coinId 代币id
     */
    @Select("select amount,`status`,create_time from " + DTConst.BlockInto + " where to_id=#{userId} and coin_id=#{coinId} order by id desc limit #{start},#{limit}")
    List<Map<String, Object>> bbAccountRecordInto(@Param("userId") Integer userId, @Param("coinId") Integer coinId, @Param("start") Integer start, @Param("limit") Integer limit);

    /**
     * 统计某一代币资产数量
     * @param userId 用户id
     * @param coinId 代币id
     */
    @Select("select count(id) from "+DTConst.BlockInto+"  where to_id=#{userId} and coin_id=#{coinId}")
    Long bbAccountRecordIntoCount(@Param("userId") Integer userId, @Param("coinId") Integer coinId);

    /**
     * 统计某一代币资产数量
     * @param userId 用户id
     * @param coinId 代币id
     */
    @Select("select count(id) from "+DTConst.BlockOut+"  where to_id=#{userId} and coin_id=#{coinId}")
    Long bbAccountRecordOutCount(@Param("userId") Integer userId, @Param("coinId") Integer coinId);

    /**
     * 币币账户某一代币资产转出明细
     *
     * @param userId 用户id
     * @param coinId 代币id
     */
    @Select("select amount,`status`,create_time from " + DTConst.BlockOut + " where to_id=#{userId}  and coin_id=#{coinId} order by id desc limit #{start},#{limit}")
    List<Map<String, Object>> bbAccountRecordOut(@Param("userId") Integer userId, @Param("coinId") Integer coinId, @Param("start") Integer start, @Param("limit") Integer limit);

    /**
     * 法币账户资产
     *
     * @param userId 用户id
     */
    @Select("select * from " + DTConst.UserAccount + " where user_id=#{userId} and type=2")
    List<UserAccount> fbAsset(@Param("userId") Integer userId);

    @Select("select * from " + DTConst.UserAccount + " where user_id=#{userId} and type=102")
    List<UserAccount> leverAsset(@Param("userId") Integer userId);

    /**
     * 获取某个账户下某个币种资产
     *
     * @param coinId 币种id
     * @param type   账户类型
     */
    @Select("select freeze,amount from " + DTConst.UserAccount + " where user_id=#{userId} and coin_id=#{coinId} and type=#{type} limit 1")
    Map<String, BigDecimal> coinAssetById(@Param("userId") Integer userId, @Param("coinId") Integer coinId, @Param("type") Integer type);


    /**
     * 判断用户对应账户内是否有足够金额
     *
     * @param userId 用户id
     * @param type   账户类型 1:币币账户 2:法币账户 3:解封账户
     * @param coinId 代币id
     * @return boolean
     */
    @Select("select amount from " + DTConst.UserAccount + " where user_id=#{userId} and type=#{type} and coin_id=#{coinId} limit 1")
    BigDecimal isSufficientAmount(@Param("userId") Integer userId, @Param("type") Integer type, @Param("coinId") Integer coinId);

    /**
     * 减少用户指定账户中指定代币的数额
     *
     * @param userId 用户id
     * @param type   账户类型
     * @param coinId 代币id
     * @param amount 扣除数额
     */
    @Update("update " + DTConst.UserAccount + " set amount=amount-#{amount} where user_id=#{userId} and type=#{type} and coin_id=#{coinId}")
    boolean decrAmount(@Param("userId") Integer userId, @Param("type") Integer type, @Param("coinId") Integer coinId, @Param("amount") BigDecimal amount);

    /**

     * 查询可供售出的法币
     * @param userId
     * @return
     */
    @Select("select amount from "+ DTConst.UserAccount+" where user_id=#{userId} and type=2 and coin_id=#{coinId}")
    BigDecimal canSellFb(@Param("userId") Integer userId, @Param("coinId") Integer coinId);
    /**
     * 冻结用户代币
     * @param userId 用户id
     * @param coinId 代币id
     * @param amount 冻结数量
     */
    @Update("update "+DTConst.UserAccount+" set freeze=freeze+#{amount},amount=amount-#{amount} where user_id=#{userId} and type=#{type} and coin_id=#{coinId}")
    boolean freezeAmount(@Param("userId") Integer userId, @Param("type") Integer type, @Param("coinId") Integer coinId, @Param("amount") BigDecimal amount);

    /**
     * 解除冻结用户代币
     * @param userId 用户id
     * @param coinId 代币id
     * @param amount 解除冻结数量
     */
    @Update("update "+DTConst.UserAccount+" set freeze=freeze-#{amount},amount=amount+#{amount} where user_id=#{userId} and type=#{type} and coin_id=#{coinId}")
    boolean unfreezeAmount(@Param("userId") Integer userId, @Param("type") Integer type, @Param("coinId") Integer coinId, @Param("amount") BigDecimal amount);

    /**
     * 减少冻结的代币数量
     * @param userId 用户id
     * @param type 账户类型
     * @param coinId 代币id
     * @param amount 解除冻结数量
     */
    @Update("update "+DTConst.UserAccount+" set freeze=freeze-#{amount} where user_id=#{userId} and type=#{type} and coin_id=#{coinId}")
    boolean dercfreeze(@Param("userId") Integer userId, @Param("type") Integer type, @Param("coinId") Integer coinId, @Param("amount") BigDecimal amount);

    /**
     * 增加冻结的代币数量
     * @param userId 用户id
     * @param type 账户类型
     * @param coinId 代币id
     * @param amount 解除冻结数量
     */
    @Update("update "+DTConst.UserAccount+" set freeze=freeze+#{amount} where user_id=#{userId} and type=#{type} and coin_id=#{coinId}")
    boolean incrfreeze(@Param("userId") Integer userId, @Param("type") Integer type, @Param("coinId") Integer coinId, @Param("amount") BigDecimal amount);


    /**
     * 增加可用的代币数量
     * @param userId 用户id
     * @param type 账户类型
     * @param coinId 代币id
     * @param amount 解除冻结数量
     */
    @Update("update "+DTConst.UserAccount+" set amount=amount+#{amount} where user_id=#{userId} and type=#{type} and coin_id=#{coinId}")
    boolean incrAmount(@Param("userId") Integer userId, @Param("type") Integer type, @Param("coinId") Integer coinId, @Param("amount") BigDecimal amount);

    @Update("update "+DTConst.UserAccount+" set amount=amount-#{amount} where user_id=#{userId} and type=#{type} and coin_id=#{coinId}")
    boolean supportCrowdFunding(@Param("userId") Integer userId, @Param("type") Integer type, @Param("coinId") Integer coinId, @Param("amount") BigDecimal amount);

    @Update("update "+DTConst.UserAccount+" set amount=amount+#{amount} where user_id=#{userId} and type=#{type} and coin_id=#{coinId}")
    boolean returnCrowdFunding(@Param("userId") Integer userId, @Param("type") Integer type, @Param("coinId") Integer coinId, @Param("amount") BigDecimal amount);
}
