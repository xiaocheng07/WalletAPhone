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

        System.out.println("iv=" + HEX.ByteArrayToHexString(iv));
        System.out.println("data=" + HEX.ByteArrayToHexString(data));


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
            System.out.println("3des output=" + HEX.ByteArrayToHexString(result).toUpperCase());
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

        String key = "12345678";
        System.out.println("key测试工具用编码=" + HEX.ByteArrayToHexString(key.getBytes()));//16进制输出，输入测试工具

        String data = "ABCDEFGH";
        System.out.println("data测试工具用编码=" + HEX.ByteArrayToHexString(data.getBytes()));//16进制输出，输入测试工具


        // 使用银联算法
        byte[] mac = MAC.calcMAC(key.getBytes(), data.getBytes());
        System.out.println("mac长度=" + mac.length);

        System.out.println("mac=" + HEX.ByteArrayToHexString(mac).toUpperCase());

        byte[] iv = new byte[8];
        /*
        for (int i=0; i<iv.length; i++)
        {
            iv[i] = (byte)0x00;
        }
        */

        byte[] mac1 = MAC.calcMAC1(key.getBytes(), iv, data.getBytes());
        System.out.println("mac1=" + HEX.ByteArrayToHexString(mac1).toUpperCase());

        String key2 = "1234567812345678";
        System.out.println("输入密钥=" + HEX.ByteArrayToHexString(key2.getBytes()));


        byte[] iv2 = {(byte)0xFC, (byte)0xF3, 0x04, 0x77, 0x00, 0x00, 0x00, 0x00};
        byte[] k ={0x78,(byte)0xBB,(byte)0xDF,(byte)0xD3,(byte)0xCE,(byte)0xCA,0x48,(byte)0xFD,0x7B,(byte)0xD5,(byte)0xF6,(byte)0xAE,0x69,(byte)0x88,0x2C,0x60};
        byte[] d = {0x04, (byte)0xDC, 0x07, (byte)0xBC, 0x1C, 0x11, 0x16, 0x01, 0x00, 0x20, 0x15, 0x08, 0x27, 0x17, 0x24, 0x23, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        byte[] mac2 = MAC.calcMAC1(k, iv2, d);
        System.out.println("3mac = " + HEX.ByteArrayToHexString(mac2).toUpperCase());

    }
}
