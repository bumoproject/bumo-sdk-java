package io.bumo.sdk.core.utils.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import io.bumo.encryption.key.PrivateKey;
import io.bumo.encryption.key.PublicKey;

/**
 * Summary tool class
 *
 * @author bumo
 */
public class ShaUtils{

    /**
     * SHA128 hash for the specified byte array
     *
     * @param data
     * @return Return a byte array of 16
     */
    public static byte[] hash_128(byte[] bytes){
        byte[] hash256Bytes = hash_256(bytes);
        return Arrays.copyOf(hash256Bytes, 16);
    }

    /**
     * SHA256 hash for the specified byte array
     *
     * @param data
     * @return Return a byte array of 32
     */
    public static byte[] hash_256(byte[] bytes){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(bytes);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static byte[] hash_256(InputStream input){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            byte[] buff = new byte[64];
            int len = 0;
            while ((len = input.read(buff)) > 0) {
                md.update(buff, 0, len);
            }
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * Assemble the data to be signed
     */
    @Deprecated
    public static byte[] getData(byte[] userAgent, byte[] url, byte[] requestBody){

        byte[] bytes = new byte[userAgent.length + url.length + (requestBody == null ? 0 : requestBody.length)];
        System.arraycopy(userAgent, 0, bytes, 0, userAgent.length);
        System.arraycopy(url, 0, bytes, userAgent.length, url.length);
        if (requestBody != null) {
            System.arraycopy(requestBody, 0, bytes, userAgent.length + url.length, requestBody.length);
        }
        return bytes;
    }


    /**
     * sign 3.0
     *
     * @param msg   A message to be signed
     * @param bSkey private key
     * @return signed data
     */
    public static byte[] signV3(byte[] msg, String bSkey, String pbKey){
        try {
            PrivateKey bumoKey = new PrivateKey(bSkey);
            return bumoKey.sign(msg);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Signature failed !!", e);
        }
    }

    /**
     * check signature 3.0
     *
     * @param msg  Message content
     * @param pkey public key
     * @param sig  signature
     */
    public static boolean verifyV3(byte[] msg, byte[] signMsg, String pkey){
        try {
            return PublicKey.verify(msg, signMsg, pkey);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Signature verify failed !!", e);
        }
    }

}
