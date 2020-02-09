package com.hhekj.btc.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hhekj.btc.common.TxState;
import com.hhekj.btc.model.*;
import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Angel
 * Description: 扫描区块
 * Date: 2020-01-13 18:13
 **/

public class BTCScanTool {


    private static RpcKit walletKit ;

    static {
        try {
            walletKit = RpcKit.getInstance();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }





    /**
     * 转账
     * @param
     *  toAddress 转账地址
     * @param money 转账金额
     * @param privateKey 私钥
     * */

    public static boolean transferToAddress(String toAddress,String money,String privateKey){
        MemPoolInfo memPoolInfo = BTCScanTool.getMemPoolInfo();
        BigDecimal fees = new BigDecimal(memPoolInfo.getMempoolminfee());
        setTxFrees(fees.doubleValue());
        String txid = sendToAddress(toAddress,new BigDecimal(money)); //返回交易id
        if (!txid.isEmpty()){
            return true;
        }


        return false;
    }

    /**
     * 转账
     * @param
     *  toAddress 转账地址
     * @param privateKey 私钥
     * */
    public static boolean transfer(String toAddress,String address,String privateKey){


        //获取钱包utxo
        ArrayList<WalletUTXO> utxos = listUnspent(address);

        MemPoolInfo memPoolInfo = BTCScanTool.getMemPoolInfo();
        BigDecimal fees = new BigDecimal(memPoolInfo.getMempoolminfee());

        if(utxos.size() > 0){
            //创建裸交易
            JSONObject obj = new JSONObject();
            String txid = utxos.get(0).getTxid();
            obj.put("txid",txid);
            obj.put("vout",utxos.get(0).getVout());
            Object[] arr = new Object[]{obj};
            JSONObject map = new JSONObject();
            map.put(toAddress,utxos.get(0).getAmount().subtract(fees));
            String transactionStr = createRawTransaction(arr,map);


            String[] privateKeyArr = new String[]{privateKey};

            //签名裸交易
            TransferInfo info = signRawTransaction(transactionStr,privateKeyArr);

            //广播裸交易
            String s = sendRawTransaction(info.getHex());
            if (!s.isEmpty()){
                return  true;
            }
        }



        return false;
    }



    /**
     * 导入私钥
     * */
    public static boolean importPrivKey(String privateKey){
        try{
           Object result = walletKit.importPrivKey(privateKey);
            System.out.println(result);
            if (null == result)
                return true;
        } catch(Throwable e){
            e.printStackTrace();
            return true;
        }
        return false;
    }


    /**
     * 查询钱包UTXO
     * @param
     *  address 钱包地址
     * */
    public static ArrayList<WalletUTXO> listUnspent(String address){
        ArrayList<WalletUTXO> walletUTXOs = new ArrayList<>();
        try{
            String result = walletKit.listUnspent(6,9999999,address);
            System.out.println(result);
            JSONArray jsonObject = JSONArray.parseArray(result);
            int size = jsonObject.size();
            for (int i = 0; i <size; i++){
                WalletUTXO utxo = JSONObject.parseObject(jsonObject.get(i).toString(),WalletUTXO.class);
                walletUTXOs.add(utxo);
            }
        }catch (Throwable e){
            e.printStackTrace();
        }

        return walletUTXOs;
    }



    /**
     * 广播裸交易
     * */
    public static String sendRawTransaction(String transfer){
        try{
            String result = walletKit.sendRawTransaction(transfer);
            System.out.println(result);
            return result;
        }catch(Throwable e){
            return "";
        }
    }



    /**
     * 签名裸交易
     * @param
     *  hexstring 未签名
     *  privaKey 私钥数组
     *
     * */
    public static TransferInfo signRawTransaction(String hexstring,Object[] privateKey){
        try{
            String result = walletKit.signRawTransaction(hexstring,privateKey);
            JSONObject jsonObject = JSONObject.parseObject(result);
            TransferInfo transaction = jsonObject.toJavaObject(TransferInfo.class);
            System.out.println(transaction);
            return transaction;
        }catch(Throwable e){
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 创建裸交易
     * */
    public static String createRawTransaction(Object[] obj, Map map){
        try{
            String result = walletKit.createRawTransaction(obj,map);
            return result;
        }catch(Throwable e){
            return "";
        }
    }


   public static String getRawChangeAddress(){
        try{
            String address = walletKit.getRawChangeAddress();
            System.out.println(address);
            return address;
        }catch (Throwable e){
            e.printStackTrace();
        }

        return "";
   }


    /**
     * 返回指定交易id的交易
     * */
    public static BtcTransaction scanTxidTransaction(String txid){
       try{
           String result = walletKit.getrawtransaction(txid);
           JSONObject jsonObject = JSONObject.parseObject(result);
           BtcTransaction transaction = jsonObject.toJavaObject(BtcTransaction.class);
           return transaction;
       }catch (Throwable e){
           e.printStackTrace();
           return null;
       }

    }


    /**
     * 扫描指定区块
     * */
    public static BtcBlock scanNumberBlock(BigInteger number){
       try{
        String numberHash = walletKit.getblockhash(number);
        String result = walletKit.getBlock(numberHash);
        JSONObject jsonObject = JSONObject.parseObject(result);
        BtcBlock block = jsonObject.toJavaObject(BtcBlock.class);
        return block;
       }catch (Throwable e){
           e.printStackTrace();
           return null;
       }
    }

    /**
     * 根据区块哈希获取区块
     * */
    public static BtcBlock getBlock(String numberHash){
        try{
            String result = walletKit.getBlock(numberHash);
            JSONObject jsonObject = JSONObject.parseObject(result);
            BtcBlock block = jsonObject.toJavaObject(BtcBlock.class);
            return block;
        }catch (Throwable e){
            e.printStackTrace();
            return null;
        }
    }



    public static WalletInfo getWalletInfo()  {
        try {
            String result = walletKit.getWalletInfo();
            JSONObject jsonObject = JSONObject.parseObject(result);
            WalletInfo walletInfo = jsonObject.toJavaObject(WalletInfo.class);
            return walletInfo;
        }catch (Throwable e){
            e.printStackTrace();
            return null;
        }

    }





    public static boolean validBtcAddress(String address){
        try{
            String result = walletKit.validBtcAddress(address);
           JSONObject obj = JSONObject.parseObject(result);
            if("true".equals(obj.get("isvalid").toString())){
                return true;
            }
        }catch (Throwable e){
            return  false;
        }
        return false;
    }


    public static BigDecimal getBalance(String address) {
        try{
            String account = walletKit.getaccount(address);
            String balance = walletKit.getbalance(account);
            BigDecimal bigDecimal = new BigDecimal(balance);
            if (bigDecimal != null)
                return bigDecimal;
        }catch(Throwable e){
            return BigDecimal.valueOf(0.0);
        }
        return BigDecimal.valueOf(0.0);

    }

    public static boolean setTxFrees(double d){
        try {
            String str = walletKit.setTxFee(d);
            if (str.equals("true")){
                return true;
            }
        }catch (Throwable e){
            e.printStackTrace();
        }
        return false;
    }



    /**
     * 根据交易哈希查询交易状态
     *
     * @param txHash 交易哈希
     * @return 交易状态枚举
     */
    public static TxState queryTx(String txHash) {
        try{
            String resource = walletKit.getTransaction(txHash);
            JSONObject jsonObject = JSONObject.parseObject(resource);
            BtcTransaction transaction = jsonObject.toJavaObject(BtcTransaction.class);
            BigInteger b = transaction.getConfirmations();
            if (b.intValue() >= 6 ){
                return TxState.CONFIRMED;
            }
        }catch(Throwable e){

        }
        return TxState.FAIL;
    }



    public static MemPoolInfo getMemPoolInfo(){
        MemPoolInfo poolInfo = null;
        try{
            String result = walletKit.getMemPoolInfo();
            JSONObject jsonObject = JSONObject.parseObject(result);
            poolInfo = JSONObject.toJavaObject(jsonObject,MemPoolInfo.class);
        }catch(Throwable e){
            e.printStackTrace();
        }
        return poolInfo;
    }

    public static BigDecimal getFees(){
        BigDecimal frees = BigDecimal.valueOf(0.0001);
        try{
            BtcBlock btcBlock =  scanNewBlock();
            System.out.println(btcBlock);
           List<String> arr = btcBlock.getTx();
           if (arr != null && arr.size() != 0){
               BtcTransaction transaction = scanTxidTransaction(arr.get(0));
               List ins = transaction.getVin();
               List outs = transaction.getVout();
               int account = 148*ins.size()+34*outs.size()+10;
                frees = frees.multiply(BigDecimal.valueOf(account));
           }
        }catch(Throwable e){
            e.printStackTrace();
        }
        return frees;
    }


    public static String sendToAddress(String address, BigDecimal amount){
        try{
            return walletKit.sendtoaddress(address,amount.doubleValue());
        }catch (Throwable e){
            e.printStackTrace();
            return "";
        }

    }



    /**
     * 返回最新块
     */
    public static BtcBlock scanNewBlock() {
       try{
        String bestHash = walletKit.getbestblockhash();
        String result = walletKit.getBlock(bestHash);
        JSONObject jsonObject = JSONObject.parseObject(result);
        BtcBlock block = jsonObject.toJavaObject(BtcBlock.class);
        return block;
       }catch (Throwable e){
           e.printStackTrace();
           return null;
       }
    }


}
