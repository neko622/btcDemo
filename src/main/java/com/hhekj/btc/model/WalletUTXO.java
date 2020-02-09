package com.hhekj.btc.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Angel
 * @version 1.0
 * @date 2020/2/1 13:33
 */


@Data
public class WalletUTXO {
    /** 交易id*/
    private String txid;
    /** 输出序号*/
    private Integer vout;
    /**地址*/
    private String address;
    /** 关联账户 */
    private String account;
    /**公钥脚本 */
    private String scriptPubKey;
    /**赎回脚本 */
    private String redeemScript;
    /**金额 */
    private BigDecimal amount;
    /**确认数 */
    private Integer confirmations;
    /** 是否可消费*/
    private boolean spendable;

}
