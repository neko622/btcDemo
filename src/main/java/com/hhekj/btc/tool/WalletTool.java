package com.hhekj.btc.tool;

import com.hhekj.btc.model.BtcWallet;
import com.hhekj.btc.model.BtcWalletAccount;
import io.github.novacrypto.bip39.MnemonicGenerator;
import io.github.novacrypto.bip39.Words;
import io.github.novacrypto.bip39.wordlists.English;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Author: Angel
 * Description:
 * Date: 2020-01-13 10:51
 **/
@Slf4j
public class WalletTool {

    private final static String BTCTYPE = "44H / 1H / 0H / 0 / 0";


    /*1LYD62rK2qR6BtUiWQqzeTKyGd8hcVNoej*/



    /**
     * 生成一组随机的助记词
     *
     * @return 助记词
     */
    public static String generateMnemonics() {
        StringBuilder sb = new StringBuilder();
        byte[] entropy = new byte[Words.TWELVE.byteLength()];
        new SecureRandom().nextBytes(entropy);
        new MnemonicGenerator(English.INSTANCE).createMnemonic(entropy, sb::append);
         log.info(sb.toString());
        return sb.toString();
    }





    /**
     * 生成BTC钱包
     * */
    public static BtcWalletAccount createBtcWallet(String mnemonics) throws UnreadableWalletException {

        DeterministicSeed deterministicSeed = new DeterministicSeed(mnemonics, null, "", 0L);
        DeterministicKeyChain deterministicKeyChain = DeterministicKeyChain.builder().seed(deterministicSeed).build();
        //BIP44规则生成账户
        BigInteger privKeyBTC  = deterministicKeyChain.getKeyByPath(HDUtils.parsePath(BTCTYPE), true).getPrivKey();
        ECKey ecKey = ECKey.fromPrivate(privKeyBTC);

        String publicKey = Numeric.toHexStringNoPrefixZeroPadded(new BigInteger(ecKey.getPubKey()), 66);
        String privateKey = ecKey.getPrivateKeyEncoded(MainNetParams.get()).toString();
        String address = ecKey.toAddress(MainNetParams.get()).toString();
         log.info(privateKey);
         log.info(address);
        BtcWalletAccount btcWallet = BtcWalletAccount.builder().address(address).publicKey(publicKey).privateKey(privateKey).mnemonic(mnemonics).build();

        return btcWallet;


    }




}
