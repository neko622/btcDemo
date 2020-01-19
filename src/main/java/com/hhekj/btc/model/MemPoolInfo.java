package com.hhekj.btc.model;

import lombok.Data;

import java.math.BigInteger;

/**
 * Author: Angel
 * Description:交易池信息
 * Date: 2020-01-18 14:19
 **/
@Data
public class MemPoolInfo {

    /** 池内交易数量 */
    private BigInteger size;
    /** 池内交易占用字节数 */
    private BigInteger bytes;
    /** 交易池占用的内存空间字节数 */
    private BigInteger usage;
    /** 交易池最大可用内存 */
    private BigInteger maxmempool;
    /** 交易池最低交易费率 */
    private String mempoolminfee;


}
