package com.hhekj.btc.model;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Author: Angel
 * Description:
 * Date: 2020-01-14 09:33
 **/
@Data
public class BlockOut {
    /**输出地址*/
    private ScriptPubKey scriptPubKey;
    /**输出金额*/
    private BigDecimal value;

    private BigInteger n;

}
