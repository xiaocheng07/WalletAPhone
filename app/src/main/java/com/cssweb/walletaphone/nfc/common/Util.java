package com.cssweb.walletaphone.nfc.common;

/**
 * Created by chenh on 2015/11/18.
 */
public class Util {

    public static byte[] balance = new byte[4];

    //4, 3, 2, 1
    public static byte[] toBytes(int a) {
        return new byte[] { (byte) (0x000000ff & (a >>> 24)),
                (byte) (0x000000ff & (a >>> 16)),
                (byte) (0x000000ff & (a >>> 8)), (byte) (0x000000ff & (a)) };
    }

    public static int toInt(byte[] b, int s, int n) {
        int ret = 0;

        final int e = s + n;
        for (int i = s; i < e; ++i) {
            ret <<= 8;
            ret |= b[i] & 0xFF;
        }
        return ret;
    }
    public static int toInt(byte... b) {
        int ret = 0;
        for (final byte a : b) {
            ret <<= 8;
            ret |= a & 0xFF;
        }
        return ret;
    }

    public static short substract(byte[] data, byte checkOverflow)
    {
        short  i,t1,t2,pos,ads;

        ads = 0;

        for(i=3; i>=0; i--)
        {
            t1 = (short)(balance[(short)(i)] & 0x0ff);
            t2 = (short)(data[i] & 0x0ff);

            if ( ads > (short)0 )
            {
                if ( t1 > (short)0 )
                {
                    t1--;
                    ads = (short)0;
                }
                else
                {
                    t1 = (short)255;
                    ads = (short)1;
                }
            }

            if ( t1 >= t2 )
            {
                t1 = (short)(t1-t2);
            }
            else
            {
                t1 = (short)(t1 + 256 - t2);
                ads = (short)1;
            }

            if (checkOverflow > (byte)0x0)
                balance[(short)(i)] = (byte)t1;
        }

        return ads;
    }

    public static short add(byte[] data, byte checkOverflow)
    {
        short  i,t1,t2,ads;

        ads = (short)0;

        for(i=3; i>=0; i--) {
            t1 = (short)(balance[(short)(i)] & 0x0ff);
            t2 = (short)(data[i] & 0x0ff);

            t1 = (short)(t1 + t2 + ads);

            if (checkOverflow > (byte)0 )
                balance[(short)(i)] = (byte)(t1 % 256); // 取余数

            ads = (short)(t1 / 256); // 取商
        }

        return ads;
    }

    public static void main(String[] args)
    {
        //int x = 500 % 256;
        //System.out.println("x=" + x);
        //int y = 500 / 256;
        //System.out.println("y=" + y);

        int r = 0;

        balance = toBytes(0);



        byte[] charge = toBytes(10);
        r = add(charge, (byte)1);
        System.out.println("r = " + r);
        System.out.println("充值后余额 = " + toInt(balance, 0, 4));



        byte[] purchase = toBytes(4);
        r = substract(purchase, (byte) 0);
        System.out.println("继续消费，检查是否溢出r=" + r);
        r = substract(purchase, (byte) 1);
        System.out.println("r=" + r);
        System.out.println("消费后余额 = " + toInt(balance, 0, 4));

        r = substract(purchase, (byte) 0);
        System.out.println("继续消费，检查是否溢出r=" + r);
        r = substract(purchase, (byte) 1);
        System.out.println("r=" + r);
        System.out.println("消费后余额 = " + toInt(balance, 0, 4));

        r = substract(purchase, (byte) 0);
        System.out.println("继续消费，检查是否溢出r=" + r);



    }
}
