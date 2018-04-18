package org.bumo.sdk.core.utils.concurrent;

/**
 * Unanticipated interception exception
 *
 * @author bumo
 */
public class UnexpectedInterruptedException extends RuntimeException{

    private static final long serialVersionUID = -4404758941888371638L;

    public UnexpectedInterruptedException(){
    }

    public UnexpectedInterruptedException(String message){
        super(message);
    }

    public UnexpectedInterruptedException(String message, Throwable cause){
        super(message, cause);
    }

}
