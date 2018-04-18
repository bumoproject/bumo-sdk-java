package org.bumo.sdk.core.transaction.model;

import java.io.Serializable;

import org.bumo.sdk.core.utils.io.ByteBlob;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public class TransactionBlob implements Serializable{

    private static final long serialVersionUID = -2993044303975412391L;
    private String hash;
    private ByteBlob bytesBlob;

    public TransactionBlob(byte[] bytes, HashType hashType){
        this.bytesBlob = ByteBlob.wrap(bytes);
        this.hash = hashType.hash2Hex(bytes);
    }

    public String getHash(){
        return hash;
    }

    public ByteBlob getBytes(){
        return bytesBlob;
    }

    public String getHex(){
        return bytesBlob.toHexString();
    }

}
