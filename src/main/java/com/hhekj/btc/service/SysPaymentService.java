package com.hhekj.btc.service;


import com.hhekj.btc.model.SysPayment;

/**
 * Author: JianXin
 * Description:
 * Date: 2019-11-01 11:35
 **/

public interface SysPaymentService {

    /**
     * 获取平台收款地址
     * @param main 主链类型
     */
    SysPayment colletAddress(String main);

    /**
     * 获取平台付款地址
     * @param main 主链类型
     */
    SysPayment expendAddress(String main);
}
