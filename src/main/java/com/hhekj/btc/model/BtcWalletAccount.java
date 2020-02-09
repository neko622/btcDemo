package com.hhekj.btc.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Author: Angel
 * Description:
 * Date: 2020-01-13 14:50
 **/
@Data
@Builder
public class BtcWalletAccount {
    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 助记词
     */
    private String mnemonic;

    /**
     * 地址
     */
    private String address;



}
