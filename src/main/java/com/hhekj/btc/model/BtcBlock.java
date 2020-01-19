package com.hhekj.btc.model;

import lombok.Data;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Author: Angel
 * Description:
 * Date: 2020-01-17 11:50
 **/
@Data
public class BtcBlock {
    /** 区块哈希  */
    private String hash;
    /** 确认数  */
    private String confirmations;
    /** 剔除隔离见证数据后的区块字节数 */
    private String strippedsize;
    /**  区块字节数*/
    private String size;
    /**BIP141定义的区块权重  */
    private String weight;
    /** 区块高度 */
    private String height;
    /** 版本 */
    private String version;
    /** 16进制表示的版本 */
    private String versionHex;
    /** 区块的默克尔树根 */
    private String merkleroot;
    /** 区块内所有交易组成的数组，成员为交易id */
    private ArrayList<String> tx;
    /** 区块创建时间戳 */
    private long time;
    /** 区块中值时间戳 */
    private long mediantime;
    /** nonce值 */
    private String nonce;
    /**  */
    private String bits;
    /** 难度 */
    private String difficulty;
    /**  */
    private String chainwork;
    /** 前一区块的哈希 */
    private String previousblockhash;
    /** 下一区块的哈希 */
    private String nextblockhash;
}
