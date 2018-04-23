package io.bumo.sdk.core.utils.io;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

import io.bumo.sdk.core.utils.codec.HexUtils;
import io.bumo.sdk.core.utils.spring.Base64Utils;

/**
 * BytesBlob Binary byte block is the packaging of byte array, the purpose is to provide an immutable binary data structure
 *
 * @author bumo
 */
public class ByteBlob implements Serializable{

    private static final long serialVersionUID = -4268223365199561890L;

    private byte[] bytes;

    private ByteBlob(byte[] bytes){
        this.bytes = bytes;
    }


    public static ByteBlob wrap(byte[] bytes){
        byte[] replica = Arrays.copyOf(bytes, bytes.length);
        return new ByteBlob(replica);
    }


    public void writeTo(OutputStream out){
        try {
            out.write(bytes);
        } catch (IOException e) {
            throw new RuntimeIOException(e.getMessage(), e);
        }
    }

    public InputStream asInputStream(){
        return new ByteArrayInputStream(bytes);
    }

    public ByteBuffer asReadOnlyBuffer(){
        return ByteBuffer.wrap(bytes).asReadOnlyBuffer();
    }

    public byte[] toBytes(){
        return Arrays.copyOf(bytes, bytes.length);
    }

    public String toHexString(){
        return HexUtils.encode(bytes);
    }

    public String toBase64String(){
        return Base64Utils.encodeToString(bytes);
    }

    public String toBase64UrlString(){
        return Base64Utils.encodeToUrlSafeString(bytes);
    }

    public static ByteBlob parseHexString(String hexString){
        byte[] bytes = HexUtils.decode(hexString);
        return wrap(bytes);
    }

    public static ByteBlob parseBase64String(String base64String){
        byte[] bytes = Base64Utils.decodeFromString(base64String);
        return wrap(bytes);
    }

    public static ByteBlob parseBase64UrlString(String base64String){
        byte[] bytes = Base64Utils.decodeFromUrlSafeString(base64String);
        return wrap(bytes);
    }
}
