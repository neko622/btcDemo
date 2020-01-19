package com.hhekj.btc.tool;

import lombok.Data;

import java.util.Base64;
import java.util.Objects;

import static com.alibaba.druid.util.Utils.md5;


/**
 * Author: Angel
 * Description:
 * Date: 2020-01-13 15:02
 **/
@Data
public class KeyTool {

    /**
     * 私钥加密
     * */
    public static String privateKeyEncode(String salt,String privateKey){
        String md5Salt = md5_16(salt);  //
        String prefix = privateKey.substring(0, 20);
        String afterfix = privateKey.substring(20);
        String encode = prefix + md5Salt + afterfix;
        return base64Encode(encode);
    }

    /**
     * 加密-16位小写
     */
    public static String md5_16(String encryptStr) {
        return Objects.requireNonNull(md5(encryptStr)).substring(8, 24);
    }


    public static String privateKeyDecode(String encodeStr) {
        String base64Decode = base64Decode(encodeStr);
        String prefix = base64Decode.substring(0, 20);
        String salt = base64Decode.substring(20, 36);
        String afterfix = base64Decode.substring(36);
        return prefix + afterfix;
    }


    // base64加密
    public static String base64Encode(String str) {
        // BASE64加密
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] data = encoder.encode(str.getBytes());
        return new String(data);
    }

    // base64解密
    public static String base64Decode(String str) {
        // BASE64解密
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(str);
        return new String(bytes);
    }





}
