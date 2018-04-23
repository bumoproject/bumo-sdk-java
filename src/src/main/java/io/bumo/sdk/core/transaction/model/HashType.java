package io.bumo.sdk.core.transaction.model;


import java.util.HashMap;
import java.util.Map;

import io.bumo.encryption.utils.hash.HashUtil;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public enum HashType{
    SHA256(0),
    SM3(1),;

    private int value;

    HashType(int value){
        this.value = value;
    }

    private static final Map<Integer, HashType> TYPE_MAPPING = new HashMap<>();

    static{
        for (HashType hashType : HashType.values()) {
            TYPE_MAPPING.put(hashType.getValue(), hashType);
        }
    }

    public static HashType getHashType(int value){
        HashType hashType = TYPE_MAPPING.get(value);
        if (hashType == null) throw new RuntimeException("not support hash type :" + value);
        return hashType;
    }

    public String hash2Hex(byte[] origin){
        try {
            return HashUtil.GenerateHashHex(origin);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getValue(){
        return value;
    }
}
