package io.bumo.sdk.core.utils.concurrent;

/**
 * Unanticipated timeout exception
 *
 * @author bumo
 */
public class UnexpectedTimeoutException extends RuntimeException{

    private static final long serialVersionUID = -4404758941888371638L;

    public UnexpectedTimeoutException(){
    }

    public UnexpectedTimeoutException(String message){
        super(message);
    }

    public UnexpectedTimeoutException(String message, Throwable cause){
        super(message, cause);
    }

}
