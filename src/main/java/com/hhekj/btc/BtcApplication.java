package com.hhekj.btc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hhekj.btc.model.MemPoolInfo;
import com.hhekj.btc.tool.BTCScanTool;
import com.hhekj.btc.tool.KeyTool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
public class BtcApplication {



    public static void main(String[] args) {
        SpringApplication.run(BtcApplication.class, args);


//        BigDecimal decimal = new BigDecimal("0.00001");
//        BTCScanTool.sendToAddress("mgjPFp1o9XKqi16Kie2tnmjFgHW1jfBaY5",decimal);

//        MemPoolInfo memPoolInfo = BTCScanTool.getMemPoolInfo();
//        BigDecimal fees = new BigDecimal(memPoolInfo.getMempoolminfee());//手续费
//        System.out.println(fees);

        JSONObject obj = new JSONObject();
        obj.put("txid","1eb590cd06127f78bf38ab4140c4cdce56ad9eb8886999eb898ddf4d3b28a91d");
        obj.put("vout",0);
        Object[] arr = new Object[]{obj};
        JSONObject map = new JSONObject();
        map.put("mgnucj8nYqdrPFh2JfZSB1NmUThUGnmsqe",0.13);

        String str = BTCScanTool.createRawTransaction(arr,map);
        System.out.println(str);
    }

}
