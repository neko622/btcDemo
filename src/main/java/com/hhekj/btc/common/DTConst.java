package com.hhekj.btc.common;

/**
 * Description: 数据库表常量
 * Date: 2019-09-23 10:31
 **/

public class DTConst {

    // 用户表
    public static final String User = "user_message";

    // 用户收款表
    public static final String UserPayment = "user_payment";

    // 平台用户的法币、币币账户余额(内部账本)
    public static final String UserAccount = "user_account";

    // 各大主链数字货币表
    public static final String Coin = "digital_coin";

    // 数字货币收款地址
    public static final String CoinAddress = "digital_coin_address";

    // 入账表(区块链账本)
    public static final String BlockInto = "block_transfer_into";

    // 出账表(区块链账本)
    public static final String BlockOut = "block_transfer_out";

    // 币币交易对
    public static final String Pair = "bb_pair";

    // 币币交易订单
    public static final String BbOrder = "bb_order";

    // 交易对深度
    public static final String PairDepth = "bb_pair_depth";

    // 币币交易子订单
    public static final String BbOrderRecord = "bb_order_record";

    // 用户反馈
    public static final String FeedBack = "user_feedback";

    // 系统公告
    public static final String Notice = "sys_notice";


}
