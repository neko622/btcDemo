package com.hhekj.btc.model;

import lombok.Data;

/**
 * @author Angel
 * @version 1.0
 * Description: 签名后的交易信息
 * @date 2020/2/3 10:32
 */
@Data
public class TransferInfo {

    /** 签名后的交易序列字符串 */
    private String hex;

    /** 交易是否具备完整签名 */
    private boolean complete;
}
