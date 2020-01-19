package com.hhekj.btc.model;

import lombok.Data;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Author: Angel
 * Description:区块交易
 * Date: 2020-01-13 19:32
 **/
@Data
public class BtcTransaction {

    /**  */
    private String txid;
    /** 交易哈希 */
    private String hash;
    /** 所在区块的确认数 */
    private BigInteger confirmations;

    /** 交易体积 */
    private BigInteger size;
    /** 交易版本号 */
    private Integer version;
    /** 交易版本号 */
    private Integer vsize;

    /** 输入  */
    private ArrayList<BlockIn> vin;
    /** 输出 */
    private ArrayList<BlockOut> vout;

    /** 所在区块的哈希值 */
    private String blockhash;

    /** 所在区块的出块时间 */
    private Integer blocktime;




}
