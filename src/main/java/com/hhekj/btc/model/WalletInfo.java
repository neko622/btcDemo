package com.hhekj.btc.model;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Author: Angel
 * Description:
 * Date: 2020-01-17 17:21
 **/
@Data
public class WalletInfo {
    /** 钱包版本 */
    private BigInteger walletversion;
    /**钱包余额 */
    private BigDecimal balance;
    /** 钱包内交易数量 */
    private BigInteger txcount;
    /** 密钥池内最早密钥创建时间 */
    private long keypoololdest;
    /** 密钥池大小 */
    private BigInteger keypoolsize;
    /** 钱包解锁至何时，仅当钱包加密时有效 */
    private Integer unlocked_until;
}
