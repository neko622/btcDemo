package com.hhekj.btc.tool;


import com.alibaba.fastjson.JSON;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.util.Base64;

import java.math.BigInteger;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Angel
 * Description: 比特币钱包
 * Date: 2019-11-20 18:36
 **/
@Slf4j
public class RpcKit {

    // BitCoin.conf配置
    private final static String RPCUSER = "test";
    private final static String RPCPASSWORD = "test";

    private final static String url = "http://35.185.181.99:8332";


    private JsonRpcHttpClient client;

    private static RpcKit instance;




    private static void init() throws Throwable {
        if (null == instance) {
            instance = new RpcKit();
        }
    }

    public static RpcKit getInstance() throws Throwable {
        init();
        return instance;
    }

    public RpcKit() throws Throwable {
        // 身份认证
        String cred = Base64.encodeBytes((RPCUSER + ":" + RPCPASSWORD).getBytes());
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Authorization", "Basic " + cred);
        client = new JsonRpcHttpClient(new URL(url), headers);
        client.setConnectionTimeoutMillis(5000000);

    }


    public String walletPassPhrase(String password)throws  Throwable{
        Object obj = client.invoke("walletpassphrase",new Object[]{password},Object.class);
        return JSON.toJSONString(obj);
    }


    public String encryptWallet(String password)throws  Throwable{
        Object obj = client.invoke("encryptwallet",new Object[]{password},Object.class);
        return JSON.toJSONString(obj);
    }


    /**
     * 创建裸交易
     * */
    public String createRawTransaction(Object[] obj,Map map)throws Throwable{
        return client.invoke("createrawtransaction", new Object[]{obj,map},Object.class).toString();
    }


    /**
     * 签名裸交易
     * */
    public String signRawTransaction(String hexstring,Object[] privateKey)throws Throwable{
        Object obj =  client.invoke("signrawtransactionwithkey", new Object[]{hexstring,privateKey},Object.class);
        return JSON.toJSONString(obj);
    }


    /**
     * 广播裸交易
     * */
    public String sendRawTransaction(String transaction)throws Throwable{
        return client.invoke("sendrawtransaction", new Object[]{transaction},Object.class).toString();
    }


    /**
     *返回最高位的区块哈希。
     * */
    public String getbestblockhash()throws  Throwable{
        return client.invoke("getbestblockhash", new Object[]{},Object.class).toString();
    }


    /**
     * 获取指定哈希的区块
     *
     * @return 地址
     */
    public String getBlock(String headerHash) throws Throwable {
        Object obj = client.invoke("getblock", new Object[]{headerHash},Object.class);

        return JSON.toJSONString(obj);
    }





    /**
     * 生成新的找零地址
     * */
    public String getRawChangeAddress()throws  Throwable{
        Object obj = client.invoke("getrawchangeaddress", new Object[]{},Object.class);

        return JSON.toJSONString(obj);
    }

    /**
     * 获取指定区块高度的区块哈希
     *
     * @return 地址
     */
    public String getblockhash(BigInteger height) throws Throwable {
        return client.invoke("getblockhash", new Object[]{height},Object.class).toString();
    }


    /**
     * 获取指定交易id的交易详情
     * */
    public String getrawtransaction(String txid)throws  Throwable{
        Object obj = client.invoke("getrawtransaction", new Object[]{txid,1},Object.class);

        return JSON.toJSONString(obj);
    }




    /**
     * 验证地址是否存在
     *
     * @param address 地址
     */
    public String validBtcAddress(String address) throws Throwable {
        Object obj = client.invoke("validateaddress", new Object[]{address}, Object.class);
        return JSON.toJSONString(obj);
    }


    /**
     * 导出指定地址的私钥
     * */
    public String dumpPrivKey(String address) throws Throwable{
        return client.invoke("dumpprivkey", new Object[]{address}, Object.class).toString();
    }


    /**
     * 如果钱包加密需要临时解锁钱包
     *
     * @param password 密码
     * @param time     解锁时间
     */
    public String unlockWallet(String password, int time) throws Throwable {
        return client.invoke("walletpassphase", new Object[]{password, time}, Object.class).toString();
    }

    /**
     * 转账到制定的账户中
     *
     * @param address 地址
     * @param amount  代币数量
     */
    public String sendtoaddress(String address, double amount) throws Throwable {
        return client.invoke("sendtoaddress", new Object[]{address, amount,"","",true}, Object.class).toString();
    }


    /**
     * 设置交易手续费率
     * */
    public String setTxFee(double feePerKB)throws  Throwable{
        return client.invoke("settxfee", new Object[]{feePerKB}, Object.class).toString();
    }


    /**
     * 查询指定交易
     * */
    public String getTransaction(String txid) throws  Throwable{
        Object obj = client.invoke("gettransaction", new Object[]{txid}, Object.class);
        return JSON.toJSONString(obj);
    }

    /**
     * 获取交易池信息
     * */
    public String getMemPoolInfo() throws Throwable{
        Object obj = client.invoke("getmempoolinfo", new Object[]{}, Object.class);
        return JSON.toJSONString(obj);
    }

    /**
     * 查询账户下的交易记录
     *
     * @param account 账户
     * @param count   数量
     * @param offset  条数
     */
    public String listtransactions(String account, int count, int offset) throws Throwable {
        return client.invoke("listtransactions", new Object[]{account, count, offset}, Object.class).toString();
    }

    /**
     * 查询钱包UTXO
     *
     * @return String
     */
    public String listUnspent(int minconf, int maxconf, String address) throws Throwable {
        String[] addresss = new String[]{address};
        Object obj =  client.invoke("listunspent", new Object[]{minconf, maxconf, addresss}, Object.class);
        return JSON.toJSONString(obj);
    }

    /**
     * 获取钱包信息
     */
    public String getWalletInfo() throws Throwable {
        Object obj = client.invoke("getwalletinfo", new Object[]{}, Object.class);
        return JSON.toJSONString(obj);
    }

    /**
     * 获取指定地址所属账户
     */
    public String getaccount(String address) throws Throwable {
        return client.invoke("getaccount", new Object[]{address}, Object.class).toString();
    }

    /**
     * 查询钱包余额
     */
    public String getbalance(String account) throws Throwable {
        return client.invoke("getbalance", new Object[]{account}, Object.class).toString();
    }



    /**
     * 导入私钥
     * */
    public Object importPrivKey(String privateKey)throws Throwable{
        return client.invoke("importprivkey", new Object[]{privateKey,"",true}, Object.class);
    }

}

