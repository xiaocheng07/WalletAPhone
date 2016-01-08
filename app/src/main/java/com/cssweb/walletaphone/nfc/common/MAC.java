package com.cssweb.walletaphone.nfc.common;



import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Created by chenhf on 2014/11/10.
 */
public class MAC {




    /**
     * 银联算法
     * @param MAK
     * @param src
     * @return
     */
    public static byte[] calcMAC(byte[] MAK, byte[] src) {
        // 补足数据，8的倍数
        int length = src.length;

        int x = length % 8;
        int addLen = 0;
        if (x != 0) {
            addLen = 8 - length % 8;
        }

        byte[] data = new byte[length + addLen];
        System.arraycopy(src, 0, data, 0, length);


        // 获取第一块MAB
        int pos = 0;
        byte[] oper1 = new byte[8];
        System.arraycopy(data, pos, oper1, 0, 8);
        pos += 8;

        for (int i = 1; i < data.length / 8; i++) {

            // 获取后面MAB Block
            byte[] oper2 = new byte[8];
            System.arraycopy(data, pos, oper2, 0, 8);

            // 存放异或结果
            byte[] t = XOR.bytesXOR(oper1, oper2);
            oper1 = t;

            pos += 8;
        }

        // 将异或运算后的最后8个字节（RESULT BLOCK）转换成16个HEXDECIMAL：
        byte[] resultBlock = bytesToHexString(oper1).getBytes();
       // byte[] resultBlock = Hex.encodeHexString(oper1).getBytes();

        // 取前8个字节用mkey1，DES加密
        byte[] front8 = new byte[8];
        System.arraycopy(resultBlock, 0, front8, 0, 8);
        byte[] desfront8 = DES.encrypt(MAK, front8);

        // 取后面8个字节
        byte[] behind8 = new byte[8];
        System.arraycopy(resultBlock, 8, behind8, 0, 8);

        // 将加密后的结果与后8 个字节异或：
        byte[] resultXOR = XOR.bytesXOR(desfront8, behind8);

        // 用异或的结果TEMP BLOCK 再进行一次单倍长密钥算法运算
        //System.out.println("mak =  " + MAK.length);
        //System.out.println("xor = " + resultXOR.length);

        byte[] buff = DES.encrypt(MAK, resultXOR);

        // 将运算后的结果（ENC BLOCK2）转换成16 个HEXDECIMAL asc
        // 取8个长度字节


        String finalResult = bytesToHexString(buff);
       // String finalResult = Hex.encodeHexString(buff);

        byte[] retBuf = new byte[8];
        System.arraycopy(finalResult.getBytes(), 0, retBuf, 0, 8);

        return retBuf;
    }



    /**
     * @param data
     * @return
     */
    private static String bytesToHexString(byte[] data) {
        StringBuffer sb = new StringBuffer();

        String sTemp;
        for (int i = 0; i < data.length; i++) {
            sTemp = Integer.toHexString(0xFF & data[i]);

            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }


    /**
     * PBOC-DES-MAC算法
     * @param key
     * @param iv
     * @param src
     * @return
     */
    public static byte[] calcMAC1(byte[] key, byte[] iv, byte[] src)
    {

        //  if (iv.length != 16)
        //     return null;

        byte[] left = new byte[8];
        byte[] right = new byte[8];
        System.arraycopy(key, 0, left, 0, 8);

        if (key.length == 16)
            System.arraycopy(key, 8, right, 0, 8);

        int x = src.length % 8;

        int addLen = 8;

        if (x != 0) {
            addLen = 8 - x;
        }


        byte[] add = new byte[addLen];
        for (int i=0; i<addLen; i++)
        {
            if (i==0)
                add[i] = (byte)0x80;
            else
                add[i] = (byte)0x00;
        }

        byte[] data = new byte[src.length + addLen];
        System.arraycopy(src, 0, data, 0, src.length);
        System.arraycopy(add, 0, data, src.length, addLen);

        //System.out.println("iv=" + HEX.ByteArrayToHexString(iv));
        System.out.println("calcMAC1 data=" + HEX.ByteArrayToHexString(data));


        int pos = 0;
        byte[] block1 = new byte[8];
        System.arraycopy(data, pos, block1, 0, 8);
        pos += 8;

        byte[] input = XOR.bytesXOR(iv, block1);

        byte[] output = new byte[8];
        output = DES.encrypt(left, input);


        int count = data.length/8;
        for (int i=1; i<count; i++)
        {
            byte[] block = new byte[8];
            System.arraycopy(data, pos, block, 0, 8);
            pos += 8;

            // 存放异或结果
            input = XOR.bytesXOR(output, block);

            output = DES.encrypt(left, input);
        }

        if (key.length == 16) {
            byte[] tmp = DES.decrypt(right, output);
            byte[] result = DES.encrypt(left, tmp);


            // left 4 bytes
            byte[] mac = new byte[4];
            System.out.println("calcMAC1 3des output=" + HEX.ByteArrayToHexString(result).toUpperCase());
            System.arraycopy(result, 0, mac, 0, 4);
            return mac;
        }
        else
        {
            byte[] mac = new byte[4];
            System.arraycopy(output, 0, mac, 0, 4);
            return mac;
        }
    }


    public static void main(String[] args) throws NoSuchProviderException, NoSuchAlgorithmException {
/*
        String key = "12345678";
        System.out.println("key测试工具用编码=" + HEX.ByteArrayToHexString(key.getBytes()));//16进制输出，输入测试工具

        String data = "ABCDEFGH";
        System.out.println("data测试工具用编码=" + HEX.ByteArrayToHexString(data.getBytes()));//16进制输出，输入测试工具


        // 使用银联算法
        byte[] mac = MAC.calcMAC(key.getBytes(), data.getBytes());
        System.out.println("mac长度=" + mac.length);

        System.out.println("mac=" + HEX.ByteArrayToHexString(mac).toUpperCase());




        byte[] mac1 = MAC.calcMAC1(key.getBytes(), iv, data.getBytes());
        System.out.println("mac1=" + HEX.ByteArrayToHexString(mac1).toUpperCase());

        String key2 = "1234567812345678";
        System.out.println("输入密钥=" + HEX.ByteArrayToHexString(key2.getBytes()));
*/
        //byte[] transKey ={(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff};
        byte[] transKey ={0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38};
        byte[] cardControl = {0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38};
        byte[] cardManager = {0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38};

        byte[] purchase = {0x37,(byte)0xC1,0x7E,(byte)0xFA,0x48,(byte)0xC5,(byte)0xC5,0x13,0x48,(byte)0x9E,0x5E,(byte)0xB6,(byte)0xC7,(byte)0xAF,(byte)0x93,(byte)0x9A};
        byte[] charge = {0x20,0x6A,0x7A,(byte)0xEA,(byte)0xDA,0x34,(byte)0xD7,(byte)0xD3,(byte)0x96,(byte)0xED,0x51,0x66,(byte)0xBE,(byte)0x85,(byte)0x86,0x08};
        byte[] tac = {(byte)0xB5,0x2A,(byte)0x9E,(byte)0xFB,(byte)0xE0,0x6C,(byte)0xC2,(byte)0xCA,0x4E,(byte)0xD5,0x31,0x20,0x5B,(byte)0xDF,0x5A,(byte)0xC9};
        /*
        byte[] appControl = {EBEC8FCAFF6F91ACD3938011A1536854};
        byte[] appManager = {EBEC8FCAFF6F91ACD3938011A1536854};
        byte[] fileManager1 = {AE4E2A2D035E159D57E02E5483DC3F68};
        byte[] fileManager2 = {687F19B93825C00731A66205A9660CC4};
        byte[] cappManager = {37C17EFA48C5C513489E5EB6C7AF939A};
        byte[] pinUnLock = {20C30BCEB1B078DCB8259D4C71692D72};
        byte[] pinReload = {B0717DDF79E7FA67C4B73A2BD6FB1E89};

        byte[] appLock = {394EE8DB60A2D1359029E4EFE956FB41};
        byte[] appUnLock = {(byte)0xC3,(byte)0xF4,0x33,0x5E,0x76,0x52,0x0F,(byte)0x8B,(byte)0xBB,0x01,(byte)0x8F,0x6B,0x1A,(byte)0xCE,0x48,(byte)0xA6};
*/
        byte[] keyData = new byte[24];
        keyData[0] = 0x14;
        keyData[1] = 0x01;
        keyData[2] = 0x00;
        keyData[3] = 0x33;
        keyData[4] = 0x00;
        System.arraycopy(cardControl, 0, keyData, 5, 16);
        keyData[21] = (byte)0x80;
        keyData[22] = 0x00;
        keyData[23] = 0x00;


        byte[] keyCipher = DESede.encrypt(transKey, keyData);
        System.out.println("加密结果= " + HEX.ByteArrayToHexString(keyCipher).toUpperCase());

        byte[] iv = {(byte)0x41, (byte)0x42, 0x43, 0x44, 0x00, 0x00, 0x00, 0x00};


        byte[] macData = new byte[29];
        macData[0] = (byte)0x84;
        macData[1] = (byte)0xD4;
        //macData[2] = (byte)0x39; //密钥类型
        macData[2] = (byte)0x36; //密钥类型
        macData[3] = (byte)0x00; //密钥索引
        macData[4] = (byte)0x1C; //
        System.arraycopy(keyCipher, 0, macData, 5, keyCipher.length);


        String file05 = "04D685002C07314100000000004100000099999432020020150605000F00000001000120150605203808080000";
        byte[] data05 = HEX.HexStringToByteArray(file05);

        byte[] mac2 = MAC.calcMAC1(transKey, iv, data05);
        System.out.println("3mac = " + HEX.ByteArrayToHexString(mac2).toUpperCase());

    }
}
