package com.cssweb.walletaphone.nfc.common;

/**
 * Created by chenh on 2015/11/30.
 */
public class Util {

    public static final void arrayCopy(byte[] src, short srcOffset, byte[] dst, short dstOffset, short len)
    {
        System.arraycopy(src, srcOffset, dst, dstOffset, len);
    }
}
