package org.bumo.sdk.core.utils.blockchain;

import cfca.sadk.algorithm.common.PKIException;
import cfca.sadk.util.Base64;
import cfca.sadk.x509.certificate.X509Cert;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bumo.sdk.core.utils.codec.Base58Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

public class CFCAKeyPair{

    private String address;

    private String pubKey;

    private CFCAKeyPair(String address, String pubKey){
        this.address = address;
        this.pubKey = pubKey;
    }

    public String getAddress(){
        return address;
    }

    public String getPubKey(){
        return pubKey;
    }

    public static CFCAKeyPair generate(byte[] p7SignedData) throws PKIException{
        final cfca.sadk.util.Signature signature = new cfca.sadk.util.Signature();
        X509Cert x509Cert = signature.getSignerX509CertFromP7SignData(p7SignedData);
        byte[] raw_pkey = Base64.encode(x509Cert.getEncoded());

        byte[] pubKeyBytes = generatePublicKey(raw_pkey);

        String address = generateAddress(pubKeyBytes);
        String base58PubKey = Base58Utils.encode(pubKeyBytes);

        return new CFCAKeyPair(address, base58PubKey);
    }

    /**
     * @return encode public key
     * @throws Exception
     */
    private static byte[] generatePublicKey(byte[] raw_pkey){
        byte[] buff = new byte[raw_pkey.length + 4];
        buff[0] = 4;
        System.arraycopy(raw_pkey, 0, buff, 4, raw_pkey.length);
        return buff;
    }

    /**
     * encode address
     *
     * @param pkeyWithPrefix
     * @return
     */
    private static String generateAddress(byte[] pkeyWithPrefix){
        byte[] buff = new byte[29];
        buff[0] = (byte) 0xE6;
        buff[1] = (byte) 0x9A;
        buff[2] = (byte) 0x73;
        buff[3] = (byte) 0xFF;

        if (pkeyWithPrefix.length == 32)
            buff[4] = 1;
        else {
            buff[4] = pkeyWithPrefix[0];
        }

        // init MessageDigest
        MessageDigest md160 = null;
        try {
            Security.addProvider(new BouncyCastleProvider());
            md160 = MessageDigest.getInstance("RIPEMD160");
            byte[] pkey160 = md160.digest(pkeyWithPrefix);
            System.arraycopy(pkey160, 0, buff, 5, 20);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

        MessageDigest sha256 = null;
        try {
            sha256 = MessageDigest.getInstance("SHA-256");

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        sha256.update(buff, 0, 25);
        byte[] hash1 = sha256.digest();
        sha256.reset();
        sha256.update(hash1);
        byte[] hash2 = sha256.digest();
        System.arraycopy(hash2, 0, buff, 25, 4);
        return Base58Utils.encode(buff);
    }

}
