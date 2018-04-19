package org.bumo.sdk.core.exception;

import org.bumo.sdk.core.adapter.exception.BlockchainError;
import org.bumo.sdk.core.adapter.exception.BlockchainException;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 * SdkException predictable exception
 */
public class SdkException extends Exception{

    private static final long serialVersionUID = -1934407327912767401L;

    private int errorCode;    // Error code
    private String errorMessage;    // Error details

    public SdkException(){
        super();
    }

    public SdkException(int code, String message){
        super(message);
        this.errorCode = code;
        this.errorMessage = message;
    }

    public SdkException(BlockchainException be){
        super(be);
        this.errorCode = be.getErrorCode();
        this.errorMessage = be.getErrorMessage();
    }

    public SdkException(BlockchainError blockchainError){
        super(blockchainError.getDescription());
        this.errorCode = blockchainError.getCode();
        this.errorMessage = blockchainError.getDescription();
    }

    public SdkException(SdkError sdkError){
        super(sdkError.getDescription());
        this.errorCode = sdkError.getCode();
        this.errorMessage = sdkError.getDescription();
    }

    public SdkException(int code, String message, Throwable throwable){
        super(message, throwable);
        this.errorCode = code;
        this.errorMessage = message;
    }

    public int getErrorCode(){
        return errorCode;
    }

    public void setErrorCode(int errorCode){
        this.errorCode = errorCode;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString(){
        return "SdkException{" +
                "errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                "} " + super.toString();
    }
}
