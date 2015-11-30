package com.cssweb.walletaphone.nfc.common;

/**
 * Created by chenh on 2015/11/30.
 */
public class Padding {

    public static byte[] padding(byte[] src, short srcLen)
    {
        short x = (short)(srcLen % 8);

        short addLen = 8;

        if (x != 0) {
            addLen = (short)(8 - x);
        }


        byte[] add = new byte[addLen];
        for (short i=0; i<addLen; i++)
        {
            if (i==0)
                add[i] = (byte)0x80;
            else
                add[i] = (byte)0x00;
        }

        short len = (short)(srcLen + addLen);
        byte[] data = new byte[len];

        Util.arrayCopy(src, (short)0, data, (short)0, srcLen);
        Util.arrayCopy(add, (short)0, data, srcLen, addLen);

        return data;
    }
}
