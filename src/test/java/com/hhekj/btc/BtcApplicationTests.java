package com.hhekj.btc;

import com.hhekj.btc.task.BTCAddressTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BtcApplicationTests {

    @Autowired
    BTCAddressTask task;

    @Test
    void contextLoads() {
//        task.EthAddressBuilder();
    }

}
