package com.cssweb.walletaphone.nfc.common;




import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by chenhf on 2014/11/11.
 */
public class DES {

    private static final String KEY_ALGORITHM = "DES";
    private static final String CIPHER_ALGORITHM = "DES/ECB/NoPadding";



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


            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);



            //DESedeKeySpec dks = new DESedeKeySpec(Key);
            //SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);

            DESKeySpec dks = new DESKeySpec(key);
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

    /**
     *
     * @param key
     * @param src
     * */
    public static byte[] decrypt(byte[] key, byte[] src)
    {

        try
        {

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
       // String key = "12345678";
      //  System.out.println("key测试工具用编码=" + HEX.ByteArrayToHexString(key.getBytes()));//16进制输出，输入测试工具

       // String data = "ABCDEFGH";
      //  System.out.println("data测试工具用编码=" + HEX.ByteArrayToHexString(data.getBytes()));//16进制输出，输入测试工具



        byte[] key = {0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38};
        byte[] data = {0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48};
        byte[] encryptData = DES.encrypt(key, data);
        System.out.println("加密结果长度= " + encryptData.length);

        System.out.println("加密结果= " + HEX.ByteArrayToHexString(encryptData).toUpperCase());

        //byte[] decryptData = DES.decrypt(key.getBytes(), encryptData);
      //  System.out.println("解密结果= " + HEX.ByteArrayToHexString(decryptData).toUpperCase());
    }
}
