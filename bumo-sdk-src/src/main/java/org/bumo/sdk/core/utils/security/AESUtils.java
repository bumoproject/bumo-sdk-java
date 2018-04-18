package org.bumo.sdk.core.utils.security;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import org.bumo.sdk.core.utils.codec.HexUtils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * AES encryption algorithm tool class
 *
 * @author bumo
 */
public class AESUtils{

    /**
     * To generate a 128 bit key with the specified seed <br>
     * <p>
     * If the specified seed is empty (null or length of 0), then generates a random secret key
     *
     * @param seed
     * @return
     */
    public static String generateKey128_Hex(byte[] seed){
        byte[] keyBytes = generateKey128_Bytes(seed);
        return HexUtils.encode(keyBytes);
    }

    /**
     * To generate a 128 bit key with the specified seed
     *
     * @param seed
     * @return
     */
    public static byte[] generateKey128_Bytes(byte[] seed){
        SecretKey key = generateKey128(seed);
        return key.getEncoded();
    }

    /**
     * To generate a 128 bit key with the specified seed
     *
     * @param seed not null;
     * @return
     */
    public static SecretKey generateKey128(byte[] seed){
        if (seed == null || seed.length == 0) {
            throw new IllegalArgumentException("Empty seed!");
        }
        // Note: the AES algorithm only supports 128 bit, do not support the 192, 256 bit secret key encryption
        byte[] hashBytes = ShaUtils.hash_128(seed);
        return new SecretKeySpec(hashBytes, "AES");

        // Note: because the same seed may generate different random number sequences, it cannot be generated based on random number mechanism. By huanghaiquan at 2017-08-25
        //		byte[] random = RandomUtils.generateRandomBytes(16, seed);
        //		return new SecretKeySpec(random, "AES");
    }

    /**
     * Random key generation 128 bit
     *
     * @return
     */
    public static SecretKey generateKey128(){
        byte[] randBytes = RandomUtils.generateRandomBytes(16);
        return new SecretKeySpec(randBytes, "AES");
    }

    /**
     * In order to generate 128 bit random key 16 hexadecimal encoding
     *
     * @return
     */
    public static String generateKey128_Hex(){
        byte[] keyBytes = generateKey128_Bytes();
        return HexUtils.encode(keyBytes);
    }

    public static byte[] generateKey128_Bytes(){
        SecretKey key = generateKey128();
        return key.getEncoded();
    }

    /**
     * With the specified 16 hexadecimal AES key encryption
     *
     * @param content
     * @param key     16 hexadecimal encoding the AES key
     * @return
     */
    public static byte[] encrypt(byte[] content, String key){
        return encrypt(content, HexUtils.decode(key));
    }

    public static byte[] encrypt(byte[] content, byte[] secretKey){
        SecretKey aesKey = new SecretKeySpec(secretKey, "AES");
        return encrypt(content, aesKey);
    }

    /**
     * @param plainBytes
     * @param key
     * @return
     */
    public static byte[] encrypt(byte[] plainBytes, SecretKey key){
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(plainBytes);
        } catch (InvalidKeyException e) {
            throw new EncryptionException(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptionException(e.getMessage(), e);
        } catch (NoSuchPaddingException e) {
            throw new EncryptionException(e.getMessage(), e);
        } catch (IllegalBlockSizeException e) {
            throw new EncryptionException(e.getMessage(), e);
        } catch (BadPaddingException e) {
            throw new EncryptionException(e.getMessage(), e);
        }
    }

    /**
     * decrypt
     *
     * @param encryptedBytes
     * @param key
     * @return
     */
    public static byte[] decrypt(byte[] encryptedBytes, String key){
        return decrypt(encryptedBytes, HexUtils.decode(key));
    }

    public static byte[] decrypt(byte[] encryptedBytes, byte[] key){
        SecretKey aesKey = new SecretKeySpec(key, "AES");
        return decrypt(encryptedBytes, aesKey);
    }

    public static byte[] decrypt(byte[] encryptedBytes, SecretKey secretKey){
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(encryptedBytes);
        } catch (InvalidKeyException e) {
            throw new DecryptionException(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            throw new DecryptionException(e.getMessage(), e);
        } catch (NoSuchPaddingException e) {
            throw new DecryptionException(e.getMessage(), e);
        } catch (IllegalBlockSizeException e) {
            throw new DecryptionException(e.getMessage(), e);
        } catch (BadPaddingException e) {
            throw new DecryptionException(e.getMessage(), e);
        }
    }

}
