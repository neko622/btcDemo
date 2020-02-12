package com.hhekj.btc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
public class BtcApplication {



    public static void main(String[] args){
        SpringApplication.run(BtcApplication.class, args);







    }




}
