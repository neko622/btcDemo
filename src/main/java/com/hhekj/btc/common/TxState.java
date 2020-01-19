package com.hhekj.btc.common;

import lombok.Getter;
import lombok.Setter;

/**
 * anther : hux
 * datetime : 2019/10/30 11:50
 * description : 以太坊交易状态
 */
public enum TxState {

    //查询错误
    ERR(1),
    //交易失败
    FAIL(2),
    //等待确认
    PENDING(3),
    //确认中
    CONFIRMING(4),
    //确认完成（交易成功）
    CONFIRMED(5);

    @Getter
    @Setter
    private int name;

    TxState(int name) {
        this.name = name;
    }

    public int value() {
        return name;
    }

}
