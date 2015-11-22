package com.cssweb.walletaphone.nfc.common;

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
}
