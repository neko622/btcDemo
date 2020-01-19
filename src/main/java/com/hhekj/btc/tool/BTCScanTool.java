package com.hhekj.btc.tool;

import com.alibaba.fastjson.JSONObject;
import com.hhekj.btc.common.TxState;
import com.hhekj.btc.model.BtcBlock;
import com.hhekj.btc.model.BtcTransaction;
import com.hhekj.btc.model.MemPoolInfo;
import com.hhekj.btc.model.WalletInfo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
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
     * 返回指定交易id的交易
     * */
    public static BtcTransaction scanTxidTransaction(String txid){
       try{
           String result = walletKit.getrawtransaction(txid);
           JSONObject jsonObject = JSONObject.parseObject(show(result));
           BtcTransaction transaction = jsonObject.toJavaObject(BtcTransaction.class);
           System.out.println(transaction);
           return transaction;
       }catch (Throwable e){
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
        JSONObject jsonObject = JSONObject.parseObject(show(result));
        BtcBlock block = jsonObject.toJavaObject(BtcBlock.class);
        return block;
       }catch (Throwable e){
           e.printStackTrace();
           return null;
       }
    }


    public static WalletInfo getWalletInfo() throws Throwable {
        String result = walletKit.getWalletInfo();
        JSONObject jsonObject = JSONObject.parseObject(result);
        WalletInfo walletInfo = jsonObject.toJavaObject(WalletInfo.class);
        return walletInfo;
    }


    public static boolean validBtcAddress(String address){
        try{
            String result = walletKit.validBtcAddress(address);
            result = show(result);
           JSONObject obj = JSONObject.parseObject(result);
            if(obj.get("isvalid").equals("true")){
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
            resource = show(resource);
            JSONObject jsonObject = JSONObject.parseObject(resource);
            BtcTransaction transaction = jsonObject.toJavaObject(BtcTransaction.class);
            BigInteger b = transaction.getConfirmations();
            if (b.intValue() <= 6 ){
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
            result = show(result);
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
           System.out.println(result);
        JSONObject jsonObject = JSONObject.parseObject(show(result));
        BtcBlock block = jsonObject.toJavaObject(BtcBlock.class);
        return block;
       }catch (Throwable e){
           e.printStackTrace();
           return null;
       }
    }




    public static String show(String resouces){
        resouces = resouces.replaceAll("=",":");
        resouces = resouces.replaceAll("_","");
        resouces = resouces.replaceAll(" ","");
        String pattern  = "[a-zA-Z]*:";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(resouces);
        while (m.find()) {
            String str = m.group(0);
            String str2 = str.replace(":","");
            resouces = resouces.replaceFirst(str,"\""+str.replace(":","")+"\""+":");

        }

        pattern  = "[A-Za-z0-9_\\.-]+\\,";
        r = Pattern.compile(pattern);
        Matcher m2 = r.matcher(resouces);
        while (m2.find()) {
            String str = m2.group(0);
            String str2 = str.replace(",","");
            resouces = resouces.replaceFirst(str,"\""+str2+"\",");
        }

        pattern  = ":[a-zA-Z0-9\\.-]+";
        r = Pattern.compile(pattern);
        m2 = r.matcher(resouces);
        while (m2.find()) {
            String str = m2.group(0);
            String str2 = str.replace(":","");
            resouces = resouces.replaceFirst(str,":\""+str2+"\"");
        }
        pattern  = "[a-zA-Z0-9\\.]+]";
        r = Pattern.compile(pattern);
        m2 = r.matcher(resouces);
        while (m2.find()) {
            String str = m2.group(0);
            String str2 = str.replace("]","");
            resouces = resouces.replaceFirst(str,"\""+str2+"\"]");
        }
        return resouces;
    }


}
