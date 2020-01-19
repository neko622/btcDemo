package com.hhekj.btc.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Author: Angel
 * Description:
 * Date: 2020-01-14 09:31
 **/
@Data
public class BlockIn {

    /** 前向交易的输出位置 */
    private Integer vout;
    /** 前向交易哈希 */
    private String txid;
    /** */
    private ArrayList<String> txinwitness;
    /**  */
    private String script_asm;
    /**  */
    private String sequence;

}
