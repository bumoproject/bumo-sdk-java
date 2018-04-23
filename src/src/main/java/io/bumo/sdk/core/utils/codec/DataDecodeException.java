package io.bumo.sdk.core.utils.codec;

/**
 * invalid data exception
 *
 * @author bumo
 */
public class DataDecodeException extends RuntimeException{

    private static final long serialVersionUID = 5834019788898871654L;

    public DataDecodeException(){
    }

    public DataDecodeException(String message){
        super(message);
    }

    public DataDecodeException(String message, Throwable cause){
        super(message, cause);
    }
}
