package com.cssweb.walletaphone.nfc.common;




import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by chenhf on 2014/11/11.
 */
public class DESede {



    private static final  String KEY_ALGORITHM = "DESede";
    private static final  String CIPHER_ALGORITHM = "DESede/ECB/NoPadding";
    //private static final  String CIPHER_ALGORITHM = "DESede/CBC/NoPadding";


    /**
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static byte[] initKey(int keyBitsLen) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);

        kg.init(keyBitsLen);

        SecretKey secretKey = kg.generateKey();

        return secretKey.getEncoded();
    }

    /**
     * @param key
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static Key toKey(byte[] key) {
        try {

            // 参考http://blog.163.com/11_gying/blog/static/4067301220136176054973/

            if (key.length != 16)
                return null;

            byte[] newKey = new byte[24];

            System.arraycopy(key, 0, newKey, 0, 16);
            System.arraycopy(key, 0, newKey, 16, 8);

            DESedeKeySpec dks = new DESedeKeySpec(newKey);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);


            SecretKey securekey = keyFactory.generateSecret(dks);

            return securekey;

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return null;
    }

    /**
     * 加密
     *
     * @param key
     * @param src
     * @return
     */
    public static byte[] encrypt(byte[] key, byte[] src) {
        if (key.length != 16)
            return null;

        try {
            Key k = toKey(key);

            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

            cipher.init(Cipher.ENCRYPT_MODE, k);

            return cipher.doFinal(src);

        } catch (NoSuchPaddingException e1) {
            e1.printStackTrace();
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (IllegalBlockSizeException e1) {
            e1.printStackTrace();
        } catch (BadPaddingException e1) {
            e1.printStackTrace();
        } catch (InvalidKeyException e1) {
            e1.printStackTrace();
        }
        return null;
    }


    public static byte[] decrypt(byte[] key, byte[] src)
    {

    try {
        if (key.length != 16)
            return null;

        Key k = toKey(key);


    Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);


    cipher.init(Cipher.DECRYPT_MODE, k);

    return cipher.doFinal(src);

    } catch (NoSuchAlgorithmException e) {
    e.printStackTrace();
    } catch (InvalidKeyException e) {
    e.printStackTrace();
    }  catch (NoSuchPaddingException e) {
    e.printStackTrace();
    } catch (IllegalBlockSizeException e) {
    e.printStackTrace();
    } catch (BadPaddingException e) {
    e.printStackTrace();
    }

    return null;
    }


    public static void main(String[] args)
    {
//31323334353637383132333435363738
        byte[] key = {(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF};

        //System.out.println("测试工具用编码key=" + HEX.ByteArrayToHexString(key.getBytes())); // 输出结果输入测试工具
//41424344454647488000000000000000
        byte[] data = {0x41, 0x42, 0x43, 0x44, 0x00, 0x00, 0x00, 0x00};
        //System.out.println("测试工具用编码data=" + HEX.ByteArrayToHexString(data.getBytes()));// 输出结果输入测试工具



        byte[] encryptData = DESede.encrypt(key, data);
        System.out.println("加密结果长度= " + encryptData.length);

        System.out.println("加密结果= " + HEX.ByteArrayToHexString(encryptData).toUpperCase());

        byte[] decryptData = DESede.decrypt(key, encryptData);
        System.out.println("解密结果= " + HEX.ByteArrayToHexString(decryptData).toUpperCase());
    }
}
