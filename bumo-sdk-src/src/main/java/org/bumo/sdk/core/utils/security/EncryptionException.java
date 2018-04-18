package org.bumo.sdk.core.utils.security;

/**
 * Encryption exception
 *
 * @author bumo
 */
public class EncryptionException extends RuntimeException{

    private static final long serialVersionUID = 2188866951704920121L;

    public EncryptionException(String message){
        super(message);
    }

    public EncryptionException(String message, Throwable cause){
        super(message, cause);
    }

}
