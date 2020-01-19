package com.hhekj.btc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Table: digital_coin
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DigitalCoin {
    /**
     * Column: id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * Column:  logo2x
     * Remark: 货币 logo
     */
    private String  logo2x;

    /**
     * Column:  logo3x
     * Remark: 货币 logo
     */
    private String  logo3x;

    /**
     * Column: type
     * Remark: 类型 1：普通 2：法币类型 用于法币账户显示
     */
    private String type;

    /**
     * Column: title
     * Remark: 数字货币名称
     */
    private String title;

    /**
     * Column: main
     * Remark: 主链类型
     */
    private String main;

    /**
     * Column: symbol
     * Remark: 数字货币简称
     */
    private String symbol;

    /**
     * Column: decimal
     * Remark: 货币小数位
     */
    @TableField("`decimal`")
    private Integer decimal;

    //开盘价，以本代币为单位
    private BigDecimal price;

    /**
     * Column: address
     * Remark: 合约地址
     */
    private String address;

    /**
     * Column: paper
     * Remark: 白皮书地址
     */
    private String paper;

    /**
     * Column: website
     * Remark: 官网地址
     */
    private String website;

    /**
     * Column: blockchain
     * Remark: 区块链查询地址
     */
    private String blockchain;

    /**
     * Column: issue_time
     * Remark: 代币发行时间
     */
    private String issueTime;

    /**
     * Column: issue_amount
     * Remark: 代币发行总量
     */
    private BigDecimal issueAmount;

    /**
     * Column: circulate_amount
     * Remark: 代币流通总量
     */
    private BigDecimal circulateAmount;


    /**
     * Column: service
     * Remark: 手续费
     */
    private BigDecimal service;

    /**
     * Column: create_time
     * Remark: 发布时间
     */
    private String createTime;

    /**
     * Column: status
     * Remark: 状态 1：正常 2：下架
     */
    private Integer status;

    /**
     * Column: isdel
     * Remark: 是否删除 1：删除
     */
    private Integer isdel;

    /**
     * Column: isdel
     * Remark: 是否开放充值 1：是 0：否
     */
    private Integer open;

    /**
     * Column: desc
     * Remark: 货币描述
     */
    @TableField("`desc`")
    private String desc;

    /**
     * Column: sellService
     * Remark: 卖方手续费
     */
    private BigDecimal sellService;

    /**
     * Column: buyService
     * Remark: 买方手续费
     */
    private BigDecimal buyService;

    /**
     * Column: collectLimit
     * Remark: 代币归集阈值
     */
    @JsonIgnore
    private BigDecimal collectLimit;

    /**
     * Column: in_min_limit
     * Remark: 充币阈值最小值
     */
    private BigDecimal inMinLimit;

    /**
     * Column: in_approve_limit
     * Remark: 充币审核数量
     */
    @JsonIgnore
    private BigDecimal inApproveLimit;

    /**
     * Column: out_min_limit
     * Remark: 提币阈值最小值
     */
    private BigDecimal outMinLimit;

    /**
     * Column: out_approve_limit
     * Remark: 提币审核数量
     */
    @JsonIgnore
    private BigDecimal outApproveLimit;

    /**
     * Column: archive
     * Remark: 解封代币标记  1：解封代币 0：币币/法币
     */
    private Integer archive;


    // ===== 非数据库字段 ==== //
    // 内部账户拥有可用代币数额

    @TableField(exist = false)
    private Integer coinId;

    @TableField(exist = false)
    private Integer userId;

    @TableField(exist = false)
    private String amount;

    @TableField(exist = false)
    private String pmAmount;

    // 内部账户拥有冻结代币数额
    @TableField(exist = false)
    private String freeze;

    // 内部账户折合人民币
    @TableField(exist = false)
    private String cny;

    // 内部账户折合人民币
    @TableField(exist = false)
    private String conversionRmb;

    @JsonIgnore
    @TableField(exist = false)
    private Boolean hasVal;

    @TableField(exist = false)
    private BigDecimal unitPriceCny;

    /**
     * Remark: 众筹价格
     */
    @TableField(exist = false)
    private String crowdPrice;

}