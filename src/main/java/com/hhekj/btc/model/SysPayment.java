package com.hhekj.btc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * Table: sys_payment
 */
@Data
public class SysPayment {
    /**
     * Column: id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * Column: title
     * Remark: 标题
     */
    private String title;

    /**
     * Column: type
     * Remark: 类型 1：用于收款 2：用于付款  (冷热钱包区分)
     */
    @JsonIgnore
    private Boolean type;

    /**
     * Column: main
     * Remark: 主链类型
     */
    private String main;

    /**
     * Column: address
     * Remark: 收付款地址
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
}