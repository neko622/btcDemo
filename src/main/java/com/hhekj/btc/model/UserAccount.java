package com.hhekj.btc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Table: user_account
 */
@Data
public class UserAccount {
    /**
     * Column: id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * Column: type
     * Remark: 账户类型 1:币币账户 2:法币账户
     */
    private Integer type;

    /**
     * Column: user_id
     * Remark: 用户id
     */
    private Integer userId;

    /**
     * Column: coin_id
     * Remark: 数字货币id
     */
    private Integer coinId;

    /**
     * Column: freeze
     * Remark: 冻结代币数量
     */
    private BigDecimal freeze;

    /**
     * Column: amount
     * Remark: 账户可用代币数量
     */
    private BigDecimal amount;

    /**
     * 账户状态  0：未冻结   1已冻结
     */
    private Integer status;

    /**
     * Column: create_time
     * Remark: 创建时间
     */
    private String createTime;


    //折算成人名币
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private BigDecimal conversionRmb;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private String symbol;


}