package com.cssweb.walletaphone.nfc.test;

import com.cssweb.walletaphone.nfc.common.DES;
import com.cssweb.walletaphone.nfc.common.HEX;
import com.cssweb.walletaphone.nfc.common.INT;
import com.cssweb.walletaphone.nfc.common.MAC;
import com.cssweb.walletaphone.nfc.common.Padding;
import com.cssweb.walletaphone.nfc.common.Util;

/**
 * Created by chenh on 2015/11/24.
 */
public class TestData {

    public static int purchaseId = 1;
    public static byte keyIndex = 0x00;

    public static byte[] test_terminalId = {0x31, 0x32, 0x33, 0x34, 0x35, 0x36};
    public static byte[] test_datetime = {0x14, 0x0f, 0x0b, 0x18, 0x0a, 0x0a, 0x0a};
    public static final byte[] test_data_random = {0x41, 0x42, 0x43, 0x44};

    public byte[] chargeSessionKey;
    public byte[] purchaseSessionKey;

    public void chargeInit()
    {
        byte[] temp = new byte[512];
        byte[] chargeTradeId = {0x00, 0x00};
        System.arraycopy(test_data_random, (short) 0, temp, (short) 0, (short) 4);
        System.arraycopy(chargeTradeId, (short) 0, temp, (short) 4, (short) 2);

        byte[] data = Padding.padding(temp, (short)6);

        byte[] CHARGE_KEY =   {0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38};
        chargeSessionKey = DES.encrypt(CHARGE_KEY, data);

        System.out.println("充值过程密钥" + HEX.ByteArrayToHexString(chargeSessionKey));
    }

    public void charge()
    {
        byte TRADE_TYPE_CHARGE = (byte)0x02;
        byte[] temp = new byte[18];


        byte[] money = INT.toBytes(100);
        System.arraycopy(money, 0, temp, 0, 4);
        temp[4] = TRADE_TYPE_CHARGE;
        System.arraycopy(test_terminalId, 0, temp, 5, 6);
        System.arraycopy(test_datetime, 0, temp, 11, 7);

        byte[] data = Padding.padding(temp, (short)18);

        byte[] iv = new byte[8];
        byte[] mac2 = MAC.calcMAC1(chargeSessionKey, iv, data);
        System.out.println("mac2=" + HEX.ByteArrayToHexString(mac2));
    }

    public void purchase()
    {
        byte[] temp = new byte[8];

        byte[] purchaseTradeId = {0x00, 0x00};
        System.arraycopy(test_data_random, (short) 0, temp, (short) 0, (short) 4);
        System.arraycopy(purchaseTradeId, (short) 0, temp, (short) 4, (short) 2);
        byte[] ternimalTradeId = {0x00, 0x00, 0x00, 0x01};
        System.arraycopy(ternimalTradeId, 2, temp, 6, 2);

        byte[] PURCHASE_KEY =   {0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38};

        byte[] data = Padding.padding(temp, (short)8);
        purchaseSessionKey = DES.encrypt(PURCHASE_KEY, data);
        System.out.println("消费过程密钥" + HEX.ByteArrayToHexString(purchaseSessionKey));


        byte[] temp2 = new byte[18];
        byte[] money = {0x00, 0x00, 0x00, 0x04};
        System.arraycopy(money, 0, temp2, 0, 4);
        final byte TRADE_TYPE_PURCHASE = (byte)0x06;
        temp2[4] = TRADE_TYPE_PURCHASE;
        System.arraycopy(test_terminalId, 0, temp2, 5, 6);
        System.arraycopy(test_datetime, (short) 0, temp2, (short) 11, (short) 7);

        byte[] data2 = Padding.padding(temp2, (short)18);

        byte[] iv = new byte[8];
        byte[] mac1 = MAC.calcMAC1(purchaseSessionKey, iv, data2);
        System.out.println("mac1=" + HEX.ByteArrayToHexString(mac1));
    }

    public static void main(String[] args)
    {
        TestData test = new TestData();
        test.chargeInit();
        test.charge();
        test.purchase();
    }

}
