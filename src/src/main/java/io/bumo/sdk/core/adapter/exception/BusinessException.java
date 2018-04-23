package io.bumo.sdk.core.adapter.exception;

public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = -1934407327912767401L;


    private int errorCode;            //错误代码

    private String errorMessage;    //错误详细信息

    public BusinessException(){
        super();
    }

    public BusinessException(int code, String message){
        super(message);
        this.errorCode = code;
        this.errorMessage = message;
    }

    public BusinessException(int code, String message, Throwable throwable){
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

}
