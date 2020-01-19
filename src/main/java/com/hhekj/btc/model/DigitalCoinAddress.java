package com.hhekj.btc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * Table: digital_coin_address
 */
@Data
@TableName("btc_coin_address")
public class DigitalCoinAddress {
    /**
     * Column: id
     */
    @JsonIgnore
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * Column: user_id
     * Remark: 用户id =0代表还未分配
     */
    @JsonIgnore
    private int userId;

    /**
     * Column: main
     * Remark: 主链类型
     */
    private String main;

    /**
     * Column: address
     * Remark: 区块链收款地址
     */
    private String address;

    /**
     * Column: public_key
     * Remark: 公钥
     */
    @JsonIgnore
    private String publicKey;

    /**
     * Column: private_key
     * Remark: 私钥
     */
    @JsonIgnore
    private String privateKey;

    /**
     * Column: create_time
     * Remark: 创建时间
     */
    @JsonIgnore
    private String createTime;

    /**
     * Column: mnemonics
     * Remark: 助记词
     */

    private String mnemonics;

    /**
     * Column: create_time
     * Remark: 创建时间
     */
    @TableField(exist = false)
    private String qrCodeAddress;
}