package com.hhekj.btc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;

/**
 * anther : hux
 * datetime : 2019/11/8 18:14
 * description :
 */
@Data
public class BlockTransferInto {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 代币id
     */
    private Integer coinId;

    /**
     * 链上交易哈希
     */
    private String txHash;

    /**
     * 矿工费用
     */
    private BigDecimal service;

    /**
     * 转入代币数量
     */
    private BigDecimal amount;

    /**
     * 来源地址
     */
    private String fromAddress;

    /**
     * 来源id，若不是平台用户，此字段值为null
     */
    private Integer fromId;

    /**
     * 到达地址
     */
    private String toAddress;

    /**
     * 到达的用户id
     */
    private Integer toId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 转账状态 0：核对中 1：转账成功 2：转账失败
     */
    private Integer status;

    /**
     * 完成时间
     */
    private String finishTime;

    /**
     * 是否已资金归集 1：是
     */
    @JsonIgnore
    private Integer collect;

    /**
     * 资金归集时间
     */
    @JsonIgnore
    private String collectTime;

    /**
     * 审核状态
     */
    private String audit;

    @TableField(exist = false)
    private String symbol;
}