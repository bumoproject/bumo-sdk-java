package io.bumo.sdk.core.utils.blockchain;

import io.bumo.encryption.model.KeyType;

/**
 * v3 {private key, public key, address}
 */
public class SecureKeyGenerator{


    public static BlockchainKeyPair generateBumoKeyPair(){
        return BlockchainKeyPairFactory.generateBumoKeyPair(KeyType.ED25519);
    }

    public static String getPublicKey(String privateKey){
        return BlockchainKeyPairFactory.getPublicKeyV3(privateKey);
    }
}
