package com.hhekj.btc;

import com.hhekj.btc.tool.KeyTool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
    }

}
