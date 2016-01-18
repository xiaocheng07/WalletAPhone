package com.cssweb.walletaphone.nfc.test;

import com.cssweb.walletaphone.nfc.common.DES;
import com.cssweb.walletaphone.nfc.common.DESede;
import com.cssweb.walletaphone.nfc.common.HEX;
import com.cssweb.walletaphone.nfc.common.INT;
import com.cssweb.walletaphone.nfc.common.MAC;
import com.cssweb.walletaphone.nfc.common.Padding;
import com.cssweb.walletaphone.nfc.common.Util;

/**
 * Created by chenh on 2015/11/24.
 */
public class TestData {
    String card_no = "4100000099999432";

    String transKey = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";

    String cardControl = "31323334353637383132333435363738";
    String cardManage = "58B860693E4F13DD11EE471349E2F1DC";

    String appControl = "EBEC8FCAFF6F91ACD3938011A1536854";
    String appManage = "8F9370265BE85FB3FB5A31AF8C9F0B5D";
    String FileManage1 = "AE4E2A2D035E159D57E02E5483DC3F68";
    String FileManage2 = "687F19B93825C00731A66205A9660CC4";

    String cappManage = "37C17EFA48C5C513489E5EB6C7AF939A";

    String pinUnblock = "20C30BCEB1B078DCB8259D4C71692D72";
    String pinReload = "B0717DDF79E7FA67C4B73A2BD6FB1E89";

    String purchase1 = "37C17EFA48C5C513489E5EB6C7AF939A";
    String load1 = "206A7AEADA34D7D396ED5166BE858608";
    String tac = "B52A9EFBE06CC2CA4ED531205BDF5AC9";

    String applockkey = "394EE8DB60A2D1359029E4EFE956FB41";
    String appprelockkey = "C3F4335E76520F8BBB018F6B1ACE48A6";

    public static int purchaseId = 1;
    public static byte keyIndex = 0x00;
    public static final byte[] test_data_random = {0x41, 0x42, 0x43, 0x44};
    public static byte[] test_terminalId = {0x00, 0x00, 0x00, 0x00, 0x00, 0x01};
    public static byte[] test_datetime = {0x20, 0x15, 0x11, 0x11, 0x08, 0x45, 0x00};


    public byte[] chargeSessionKey;
    public byte[] purchaseSessionKey;

    public void chargeInit()
    {
        byte[] temp = new byte[512];
        byte[] chargeTradeId = {0x00, 0x01};

        System.arraycopy(test_data_random, (short) 0, temp, (short) 0, (short) 4);
        System.arraycopy(chargeTradeId, (short) 0, temp, (short) 4, (short) 2);

        byte[] data = Padding.padding(temp, (short)6);

        byte[] CHARGE_KEY =   HEX.HexStringToByteArray(load1);
        chargeSessionKey = DESede.encrypt(CHARGE_KEY, data);

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

       // byte[] data = Padding.padding(temp, (short)18);

        byte[] iv = new byte[8];
        byte[] k = new byte[8];
        System.arraycopy(chargeSessionKey, 0, k, 0, 8);
        byte[] mac2 = MAC.calcMAC1(k, iv, temp);
        System.out.println("mac2=" + HEX.ByteArrayToHexString(mac2));
    }

    public void purchase()
    {
        byte[] temp = new byte[8];
        byte[] purchaseTradeId = {0x00, 0x01};
        byte[] ternimalTradeId = {0x00, 0x00, 0x00, 0x01};
        byte[] PURCHASE_KEY =   HEX.HexStringToByteArray(purchase1);

        System.arraycopy(test_data_random, (short) 0, temp, (short) 0, (short) 4);
        System.arraycopy(purchaseTradeId, (short) 0, temp, (short) 4, (short) 2);

        System.arraycopy(ternimalTradeId, 2, temp, 6, 2);


        byte[] data = Padding.padding(temp, (short)8);
        purchaseSessionKey = DESede.encrypt(PURCHASE_KEY, data);
        System.out.println("消费过程密钥" + HEX.ByteArrayToHexString(purchaseSessionKey));


        byte[] temp2 = new byte[27];
        //普通消费交易
        byte[] money = {0x00, 0x00, 0x00, 0x04};
        //初始化复合消费交易
        //byte[] money = {0x00, 0x00, 0x00, 0x00};

        System.arraycopy(money, 0, temp2, 0, 4);
        final byte TRADE_TYPE_PURCHASE = (byte)0x06;
        temp2[4] = TRADE_TYPE_PURCHASE;
        System.arraycopy(test_terminalId, 0, temp2, 5, 6);
        System.arraycopy(test_datetime, (short) 0, temp2, (short) 11, (short) 7);

        byte[] uid = {(byte)0x85, (byte)0x88, (byte)0xD3, (byte)0x2B, (byte)0x76, (byte)0x88, (byte)0xD3, (byte)0x2B, (byte)0x76};

        System.arraycopy(uid, (short) 0, temp2, (short) 18, (short) uid.length);

        byte[] iv = new byte[8];
        byte[] mac1 = MAC.calcMAC1(purchaseSessionKey, iv, temp2);
        System.out.println("mac1=" + HEX.ByteArrayToHexString(mac1));
    }

    public void cappPurchase()
    {
        byte[] temp = new byte[8];
        byte[] purchaseTradeId = {0x00, 0x01};
        byte[] ternimalTradeId = {0x00, 0x00, 0x00, 0x01};
        byte[] PURCHASE_KEY =   HEX.HexStringToByteArray(purchase1);

        System.arraycopy(test_data_random, (short) 0, temp, (short) 0, (short) 4);
        System.arraycopy(purchaseTradeId, (short) 0, temp, (short) 4, (short) 2);

        System.arraycopy(ternimalTradeId, 2, temp, 6, 2);


        byte[] data = Padding.padding(temp, (short)8);
        purchaseSessionKey = DESede.encrypt(PURCHASE_KEY, data);
        System.out.println("复合消费过程密钥" + HEX.ByteArrayToHexString(purchaseSessionKey));


        byte[] temp2 = new byte[27];
        //begin
        //byte[] money = {0x00, 0x00, 0x00, 0x00};

        //end
        byte[] money = {0x00, 0x00, 0x00, 0x04};


        System.arraycopy(money, 0, temp2, 0, 4);
        final byte TRADE_TYPE_CAPP_PURCHASE = (byte)0x09;
        temp2[4] = TRADE_TYPE_CAPP_PURCHASE;
        System.arraycopy(test_terminalId, 0, temp2, 5, 6);
        System.arraycopy(test_datetime, (short) 0, temp2, (short) 11, (short) 7);

        byte[] uid = {(byte)0x85, (byte)0x88, (byte)0xD3, (byte)0x2B, (byte)0x76, (byte)0x88, (byte)0xD3, (byte)0x2B, (byte)0x76};

        System.arraycopy(uid, (short) 0, temp2, (short) 18, (short) uid.length);

        byte[] iv = new byte[8];
        byte[] mac1 = MAC.calcMAC1(purchaseSessionKey, iv, temp2);
        System.out.println("复合消费交易mac1=" + HEX.ByteArrayToHexString(mac1));
    }

    public void genAPDU()
    {


        byte[] iv = {(byte)0x41, (byte)0x42, 0x43, 0x44, 0x00, 0x00, 0x00, 0x00};
        byte[] mac = null;
        byte[] macData = null;
        byte[] key = null;
        String keyData;
        byte[] keyCipher = null;
        String data;

        keyData = "1401003300"+ cardControl + "800000";
        key = HEX.HexStringToByteArray(transKey);
        keyCipher = DESede.encrypt(key, HEX.HexStringToByteArray(keyData));
        data = "84D4" + "3900" + "1C" + HEX.ByteArrayToHexString(keyCipher);
        System.out.println("data = " + data);
        macData = HEX.HexStringToByteArray(data);
        key = HEX.HexStringToByteArray(transKey);
        mac = MAC.calcMAC1(key, iv, macData);
        System.out.println("cardControl mac = " + HEX.ByteArrayToHexString(mac).toUpperCase() + "\n");

        keyData = "1401003300"+ cardManage + "800000";
        key = HEX.HexStringToByteArray(cardControl);
        keyCipher = DESede.encrypt(key, HEX.HexStringToByteArray(keyData));
        data = "84D4" + "3600" + "1C" + HEX.ByteArrayToHexString(keyCipher);
        System.out.println("data = " + data);
        macData = HEX.HexStringToByteArray(data);
        key = HEX.HexStringToByteArray(cardControl);
        mac = MAC.calcMAC1(key, iv, macData);
        System.out.println("cardManage mac = " + HEX.ByteArrayToHexString(mac).toUpperCase() + "\n");

        keyData = "1401003300"+ appControl + "800000";
        key = HEX.HexStringToByteArray(cardControl);
        keyCipher = DESede.encrypt(key, HEX.HexStringToByteArray(keyData));
        data = "84D4" + "3900" + "1C" + HEX.ByteArrayToHexString(keyCipher);
        System.out.println("data = " + data);
        macData = HEX.HexStringToByteArray(data);
        key = HEX.HexStringToByteArray(cardControl);
        mac = MAC.calcMAC1(key, iv, macData);
        System.out.println("appControl mac = " + HEX.ByteArrayToHexString(mac).toUpperCase() + "\n");


        keyData = "1401003300"+ appManage + "800000";
        key = HEX.HexStringToByteArray(appControl);
        keyCipher = DESede.encrypt(key, HEX.HexStringToByteArray(keyData));
        data = "84D4" + "5000" + "1C" + HEX.ByteArrayToHexString(keyCipher);
        System.out.println("data = " + data);
        macData = HEX.HexStringToByteArray(data);
        key = HEX.HexStringToByteArray(appControl);
        mac = MAC.calcMAC1(key, iv, macData);
        System.out.println("appManage mac = " + HEX.ByteArrayToHexString(mac).toUpperCase() + "\n");

        keyData = "1401003300"+ FileManage1 + "800000";
        key = HEX.HexStringToByteArray(appControl);
        keyCipher = DESede.encrypt(key, HEX.HexStringToByteArray(keyData));
        data = "84D4" + "3600" + "1C" + HEX.ByteArrayToHexString(keyCipher);
        System.out.println("data = " + data);
        macData = HEX.HexStringToByteArray(data);
        key = HEX.HexStringToByteArray(appControl);
        mac = MAC.calcMAC1(key, iv, macData);
        System.out.println("FileManage1 mac = " + HEX.ByteArrayToHexString(mac).toUpperCase() + "\n");

        keyData = "1401003300"+ FileManage2 + "800000";
        key = HEX.HexStringToByteArray(appControl);
        keyCipher = DESede.encrypt(key, HEX.HexStringToByteArray(keyData));
        data = "84D4" + "3601" + "1C" + HEX.ByteArrayToHexString(keyCipher);
        System.out.println("data = " + data);
        macData = HEX.HexStringToByteArray(data);
        key = HEX.HexStringToByteArray(appControl);
        mac = MAC.calcMAC1(key, iv, macData);
        System.out.println("FileManage2 mac = " + HEX.ByteArrayToHexString(mac).toUpperCase() + "\n");


        keyData = "1401003300"+ cappManage + "800000";
        key = HEX.HexStringToByteArray(appControl);
        keyCipher = DESede.encrypt(key, HEX.HexStringToByteArray(keyData));
        data = "84D4" + "0301" + "1C" + HEX.ByteArrayToHexString(keyCipher);
        System.out.println("data = " + data);
        macData = HEX.HexStringToByteArray(data);
        key = HEX.HexStringToByteArray(appControl);
        mac = MAC.calcMAC1(key, iv, macData);
        System.out.println("cappManage mac = " + HEX.ByteArrayToHexString(mac).toUpperCase() + "\n");


        keyData = "1401003300"+ pinUnblock + "800000";
        key = HEX.HexStringToByteArray(appControl);
        keyCipher = DESede.encrypt(key, HEX.HexStringToByteArray(keyData));
        data = "84D4" + "0400" + "1C" + HEX.ByteArrayToHexString(keyCipher);
        System.out.println("data = " + data);
        macData = HEX.HexStringToByteArray(data);
        key = HEX.HexStringToByteArray(appControl);
        mac = MAC.calcMAC1(key, iv, macData);
        System.out.println("pinUnblock mac = " + HEX.ByteArrayToHexString(mac).toUpperCase() + "\n");


        keyData = "1401003300"+ pinReload + "800000";
        key = HEX.HexStringToByteArray(appControl);
        keyCipher = DESede.encrypt(key, HEX.HexStringToByteArray(keyData));
        data = "84D4" + "0500" + "1C" + HEX.ByteArrayToHexString(keyCipher);
        System.out.println("data = " + data);
        macData = HEX.HexStringToByteArray(data);
        key = HEX.HexStringToByteArray(appControl);
        mac = MAC.calcMAC1(key, iv, macData);
        System.out.println("pinReload mac = " + HEX.ByteArrayToHexString(mac).toUpperCase() + "\n");

        keyData = "1401003300"+ purchase1 + "800000";
        key = HEX.HexStringToByteArray(appControl);
        keyCipher = DESede.encrypt(key, HEX.HexStringToByteArray(keyData));
        data = "84D4" + "0601" + "1C" + HEX.ByteArrayToHexString(keyCipher);
        System.out.println("data = " + data);
        macData = HEX.HexStringToByteArray(data);
        key = HEX.HexStringToByteArray(appControl);
        mac = MAC.calcMAC1(key, iv, macData);
        System.out.println("purchase1 mac = " + HEX.ByteArrayToHexString(mac).toUpperCase() + "\n");

        keyData = "1401003300"+ load1 + "800000";
        key = HEX.HexStringToByteArray(appControl);
        keyCipher = DESede.encrypt(key, HEX.HexStringToByteArray(keyData));
        data = "84D4" + "0701" + "1C" + HEX.ByteArrayToHexString(keyCipher);
        System.out.println("data = " + data);
        macData = HEX.HexStringToByteArray(data);
        key = HEX.HexStringToByteArray(appControl);
        mac = MAC.calcMAC1(key, iv, macData);
        System.out.println("load1 mac = " + HEX.ByteArrayToHexString(mac).toUpperCase() + "\n");

        keyData = "1401003300"+ tac + "800000";
        key = HEX.HexStringToByteArray(appControl);
        keyCipher = DESede.encrypt(key, HEX.HexStringToByteArray(keyData));
        data = "84D4" + "0800" + "1C" + HEX.ByteArrayToHexString(keyCipher);
        System.out.println("data = " + data);
        macData = HEX.HexStringToByteArray(data);
        key = HEX.HexStringToByteArray(appControl);
        mac = MAC.calcMAC1(key, iv, macData);
        System.out.println("tac mac = " + HEX.ByteArrayToHexString(mac).toUpperCase() + "\n");

        keyData = "1401003300"+ applockkey + "800000";
        key = HEX.HexStringToByteArray(appControl);
        keyCipher = DESede.encrypt(key, HEX.HexStringToByteArray(keyData));
        data = "84D4" + "3300" + "1C" + HEX.ByteArrayToHexString(keyCipher);
        System.out.println("data = " + data);
        macData = HEX.HexStringToByteArray(data);
        key = HEX.HexStringToByteArray(appControl);
        mac = MAC.calcMAC1(key, iv, macData);
        System.out.println("applock mac = " + HEX.ByteArrayToHexString(mac).toUpperCase() + "\n");


        keyData = "1401003300"+ appprelockkey + "800000";
        key = HEX.HexStringToByteArray(appControl);
        keyCipher = DESede.encrypt(key, HEX.HexStringToByteArray(keyData));
        data = "84D4" + "3400" + "1C" + HEX.ByteArrayToHexString(keyCipher);
        System.out.println("data = " + data);
        macData = HEX.HexStringToByteArray(data);
        key = HEX.HexStringToByteArray(appControl);
        mac = MAC.calcMAC1(key, iv, macData);
        System.out.println("appprelock mac = " + HEX.ByteArrayToHexString(mac).toUpperCase() + "\n");



        String sfi05 = "04D68500 2C 0731  4100  0000  00  00" + card_no + "0200  20150605  000F00000001  0001  20150605  20380808  0000";
        sfi05 = sfi05.replace(" ", "");
        System.out.println("sfi05 = " + sfi05);
        macData = HEX.HexStringToByteArray(sfi05);
        key = HEX.HexStringToByteArray(cardManage);
        mac = MAC.calcMAC1(key, iv, macData);
        System.out.println("sfi05 mac = " + HEX.ByteArrayToHexString(mac).toUpperCase() + "\n");


        String sfi15 = "04D69500 22 0731  4100  0000  0000  01  01  0000" + card_no + "20150605  20380808  0000";
        sfi15 = sfi15.replace(" ", "");
        System.out.println("sfi15 = " + sfi15);
        macData = HEX.HexStringToByteArray(sfi15);
        key = HEX.HexStringToByteArray(FileManage1);
        mac = MAC.calcMAC1(key, iv, macData);
        System.out.println("sfi15 mac = " + HEX.ByteArrayToHexString(mac).toUpperCase() + "\n");

        String sfi11 = "04D6910024 01 002819A0 11 000F00000001 01F4 00 00 00 00 00 00000000000000000000000000";
        sfi11 = sfi11.replace(" ", "");
        System.out.println("sfi11 = " + sfi11);
        macData = HEX.HexStringToByteArray(sfi11);
        key = HEX.HexStringToByteArray(FileManage2);
        mac = MAC.calcMAC1(key, iv, macData);
        System.out.println("sfi11 mac = " + HEX.ByteArrayToHexString(mac).toUpperCase() + "\n");

        //4F95是CRC编码
        String gdjt = "04DC02BC 34 02 2E 00000900000000000000000000000000900000000000000000000000000000000000000000000000000000004F95";
        gdjt = gdjt.replace(" ", "");
        System.out.println("gdjt = " + gdjt);
        macData = HEX.HexStringToByteArray(gdjt);
        key = HEX.HexStringToByteArray(FileManage2);
        mac = MAC.calcMAC1(key, iv, macData);
        System.out.println("gdjt mac = " + HEX.ByteArrayToHexString(mac).toUpperCase() + "\n");

        String sfi17 = "04DC07BC 1C 1116 01 00  20150827172423  00000000000000000000000000";
        sfi17 = sfi17.replace(" ", "");
        System.out.println("sfi17 = " + sfi17);
        macData = HEX.HexStringToByteArray(sfi17);
        key = HEX.HexStringToByteArray(FileManage2);
        mac = MAC.calcMAC1(key, iv, macData);
        System.out.println("sfi17 mac = " + HEX.ByteArrayToHexString(mac).toUpperCase() + "\n");

        String appblock = "841E000004";
        appblock = appblock.replace(" ", "");
        System.out.println("appblock = " + appblock);
        macData = HEX.HexStringToByteArray(appblock);
        key = HEX.HexStringToByteArray(applockkey);
        mac = MAC.calcMAC1(key, iv, macData);
        System.out.println("appblock mac = " + HEX.ByteArrayToHexString(mac).toUpperCase() + "\n");

        String appunblock = "8418 0000 04";
        appunblock = appunblock.replace(" ", "");
        System.out.println("appunblock = " + appunblock);
        macData = HEX.HexStringToByteArray(appunblock);
        key = HEX.HexStringToByteArray(appprelockkey);
        mac = MAC.calcMAC1(key, iv, macData);
        System.out.println("appunblock mac = " + HEX.ByteArrayToHexString(mac).toUpperCase() + "\n");
    }

    public static void main(String[] args)
    {
        TestData test = new TestData();

       test.genAPDU();

/*
        test.chargeInit();
        test.charge();

        test.purchase();

        test.cappPurchase();
        */

    }

}
