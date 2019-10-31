package com.dlc.dlcconversiontool;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Administrator on 2018\5\10 0010.
 */

public class Aes128Util {
    private static Aes128Util instance;

    public static Aes128Util getInstance() {
        if (instance==null){
            instance = new Aes128Util();
        }
        return instance;
    }

    public byte[] encrypt(byte[] key,byte[] sSrc) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            return cipher.doFinal(sSrc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] decrypt(byte[] key,byte[] sSrc) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            return cipher.doFinal(sSrc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
