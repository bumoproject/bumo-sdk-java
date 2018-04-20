package org.bumo.sdk.core.utils.blockchain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bumo.encryption.key.PrivateKey;
import org.bumo.encryption.model.KeyType;
import org.bumo.sdk.core.utils.codec.Base58Utils;

import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.KeyPairGenerator;
import net.i2p.crypto.eddsa.Utils;

public abstract class BlockchainKeyPairFactory{

    @SuppressWarnings("unused")
	private static KeyPairGenerator keyPairGenerator = new KeyPairGenerator();

    static{
        Security.addProvider(new BouncyCastleProvider());
    }

    private BlockchainKeyPairFactory(){
    }

    /**
     * random bumo account {private key, public key, address},  default Type = KeyType.ED25519
     */
    public static BlockchainKeyPair generateBumoKeyPair(){
        return generateBumoKeyPair(KeyType.ED25519);
    }

    public static String getPublicKeyV3(String privateKey){
        try {
            return new PrivateKey(privateKey).getEncPublicKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * random bumo account {private key, public key, address} for 3.0
     *
     * @param type
     * @return
     */
    public static BlockchainKeyPair generateBumoKeyPair(KeyType type){
        try {

            PrivateKey bumoKey = new PrivateKey(type);
            String publicKey = bumoKey.getEncPublicKey();
            String privateKey = bumoKey.getEncPrivateKey();
            String address = bumoKey.getEncAddress();
            return new BlockchainKeyPair(privateKey, publicKey, address);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param priKey
     * @return
     */
    @SuppressWarnings("unused")
	private static String generateBumoPriKey(EdDSAPrivateKey priKey){
        try {
            byte[] priKeyheadArr = Utils.hexToBytes("DA379F01");
            byte[] M = ArrayUtils.addAll(priKeyheadArr, priKey.getSeed());
            byte[] priKeyendArr = Utils.hexToBytes("00");
            M = ArrayUtils.addAll(M, priKeyendArr);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(M);
            byte[] m_256_1 = md.digest();
            md.update(m_256_1);
            byte[] m_256_2 = md.digest();
            byte[] M_check = new byte[M.length + 4];
            System.arraycopy(M, 0, M_check, 0, M.length);
            System.arraycopy(m_256_2, 0, M_check, M.length, 4);
            return Base58Utils.encode(M_check);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error occured on generating BumoAddress!--" + e.getMessage(), e);
        }
    }

    /**
     * get encode public key
     *
     * @param pubKey
     * @return
     */
    @SuppressWarnings("unused")
	private static String generateBumoPubKey(EdDSAPublicKey pubKey){
        return Base58Utils.encode(pubKey.getAbyte());
    }

    /**
     * get encode address
     *
     * @param pubKey
     * @return encode address
     */
    @SuppressWarnings("unused")
	private static String generateBumoAddress(EdDSAPublicKey pubKey){
        try {
            MessageDigest md = MessageDigest.getInstance("RIPEMD160");
            md.update(pubKey.getAbyte());
            byte[] N = md.digest();

            byte[] pubKeyheadArr = Utils.hexToBytes("E69A73FF01");
            byte[] M = ArrayUtils.addAll(pubKeyheadArr, N);
            md = MessageDigest.getInstance("SHA-256");
            md.update(M);
            byte[] M_256_1 = md.digest();
            md.update(M_256_1);
            byte[] M_256_2 = md.digest();
            byte[] S = new byte[M.length + 4];
            System.arraycopy(M, 0, S, 0, M.length);
            System.arraycopy(M_256_2, 0, S, M.length, 4);
            return Base58Utils.encode(S);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error occured on generating BumoAddress!--" + e.getMessage(), e);
        }
    }

    /**
     * change base58 private key to bytes
     *
     * @param base58PrivKeyStr
     * @return
     */
    public static byte[] getPriKeyBytes(String base58PrivKeyStr){
        byte[] base58PrivKey = Base58Utils.decode(base58PrivKeyStr);
        if (base58PrivKey == null || base58PrivKey.length != 41) {
            throw new RuntimeException("私钥[" + base58PrivKey + "]长度不正确！");
        }
        byte[] privArr = new byte[32];
        System.arraycopy(base58PrivKey, 4, privArr, 0, privArr.length);
        return privArr;
    }
}
