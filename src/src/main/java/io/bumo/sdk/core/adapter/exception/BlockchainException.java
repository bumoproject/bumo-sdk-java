package io.bumo.sdk.core.adapter.exception;

public class BlockchainException extends BusinessException{

    private static final long serialVersionUID = -4848138173487569791L;

    private static final int UN_KNOW_ERROR_CODE = 99999;

    public BlockchainException(int code, String message){
        super(code, message);
    }

    public BlockchainException(String message){
        super(UN_KNOW_ERROR_CODE, message);
    }

    public BlockchainException(String message, Throwable cause){
        super(0, message, cause);
    }

}
