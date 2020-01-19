package com.hhekj.btc.common;

import lombok.Setter;

/**
 * anther : hux
 * datetime : 2019/11/9 10:34
 * description :
 */
public enum TransferStatus {

    //核对中
    PENDING(0),
    //成功
    OK(1),
    //失败
    NO(2);

    @Setter
    private Integer value;

    TransferStatus(int statue) {
        this.value = statue;
    }

    public Integer value() {
        return value;
    }
}
