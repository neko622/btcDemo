package com.hhekj.btc;

import com.hhekj.btc.tool.BTCScanTool;
import com.hhekj.btc.tool.KeyTool;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;

@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
public class BtcApplication {



    public static void main(String[] args) throws UnreadableWalletException {
        SpringApplication.run(BtcApplication.class, args);





//        System.out.println(BTCScanTool.getWalletInfo());


//        boolean bool = BTCScanTool.transfer("mgjPFp1o9XKqi16Kie2tnmjFgHW1jfBaY5",0.0002,"miDB91wjotE2RvQhoT2kzovsRGCysdHu5x","92f3P4tokH58C27YwTQBzuFDexpojqKULN6jzmVfz2jMgYmXW5N");
//        boolean bool = BTCScanTool.transferToAddress("mpdxsb2m6UmgwE66YhqinijoQRo456he9N  ","1000","92peskf1VwV9ZxQmTuMumLsUW67ciC1bViTTfRhD5y1i5VA95yQ");
//        System.out.println(bool);


//        System.out.println(BTCScanTool.importPrivKey("cMxRoAW8efpriuK7SYBFzi5DKxCsiCEwe5b1Fr1WsCiTqmJuZotw"));

//        System.out.println(BTCScanTool.getWalletInfo());
//        BtcTransaction transaction = BTCScanTool.scanTxidTransaction("mpdxsb2m6UmgwE66YhqinijoQRo456he9N");
//        System.out.println(BTCScanTool.getBlock(transaction.getBlockhash()));

//        BtcWalletAccount account = WalletTool.createBtcWallet(WalletTool.generateMnemonics());
//        System.out.println(account);

//        System.out.println(BTCScanTool.listUnspent("msij9yWro5YFGuokxFdrzMHervuery36Bt").size());
//        System.out.println(BTCScanTool.listUnspent("mgjPFp1o9XKqi16Kie2tnmjFgHW1jfBaY5").size());
//        System.out.println(BTCScanTool.listUnspent("mpdxsb2m6UmgwE66YhqinijoQRo456he9N").size());

//        BigDecimal hasBtc = BTCScanTool.getBalance("miDB91wjotE2RvQhoT2kzovsRGCysdHu5x");
//        System.out.println(hasBtc);


//        System.out.println(BTCScanTool.transfer("mt3bmUYzHpmGefJBUBBqekWdmqXn96Uy6U ","mpdxsb2m6UmgwE66YhqinijoQRo456he9N ","cVZ8ECBe7L2Z5LtnBmsmr3WjKT9ohzc2otY1u1ftETmRG3wH46F6"));

//        String encode = KeyTool.privateKeyEncode("","cVZ8ECBe7L2Z5LtnBmsmr3WjKT9ohzc2otY1u1ftETmRG3wH46F6");
//        System.out.println(encode);
//        String encode = "Y014Um9BVzhlZnByaXVLN1NZQkY4ZjAwYjIwNGU5ODAwOTk4emk1REt4Q3NpQ0V3ZTViMUZyMVdzQ2lUcW1KdVpvdHc=";
//        System.out.println(KeyTool.privateKeyDecode(encode));
    }




}
