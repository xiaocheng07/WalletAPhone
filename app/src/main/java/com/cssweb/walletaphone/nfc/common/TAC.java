package com.cssweb.walletaphone.nfc.common;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Created by chenh on 2015/11/14.
 */
public class TAC {
    //http://wenku.baidu.com/link?url=WCEjLeh7jUzdYAuZIh74UcnIU5dtfjWq3faHZjvYIwTSw2n1jj-_IhVgnyJEmoqYD92NA8d8p0G427JqEiB-L3o4iUNTFmCdbEqxELJyNLO
    //http://blog.csdn.net/yxstars/article/details/38456657
//http://www.cnblogs.com/xianlg/p/4683221.html

    public static byte[] calcTAC(byte[] key, byte[] iv, byte[] src)
    {
        if (key.length != 16)
            return null;

        byte[] left = new byte[8];
        byte[] right = new byte[8];
        System.arraycopy(key, 0, left, 0, 8);
        System.arraycopy(key, 8, right, 0, 8);
        byte[] sessionKey = XOR.bytesXOR(left, right);

        return MAC.calcMAC1(sessionKey, iv, src);
    }

    public static void main(String[] args) throws NoSuchProviderException, NoSuchAlgorithmException {

        String key = "1234567812345678";
        System.out.println("key测试工具用编码=" + HEX.ByteArrayToHexString(key.getBytes()));//16进制输出，输入测试工具

        String data = "ABCDEFGH";
        System.out.println("data测试工具用编码=" + HEX.ByteArrayToHexString(data.getBytes()));//16进制输出，输入测试工具


        byte[] iv = new byte[8];

        byte[] tac = TAC.calcTAC(key.getBytes(), iv, data.getBytes());
        System.out.println("tac=" + HEX.ByteArrayToHexString(tac).toUpperCase());



    }
}
