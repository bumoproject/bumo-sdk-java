package io.bumo.exception;

public class SDKException extends RuntimeException{
	private static final long serialVersionUID = 429654902433634386L;
    private Integer errorCode;
    private String errorDesc;
    private SdkError sdkError;
    
    public SDKException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = SdkError.SYSTEM_ERROR.getCode();
        this.errorDesc = message;
    }

    public SDKException(Throwable cause) {
        super(cause);
        this.errorCode = SdkError.SYSTEM_ERROR.getCode();
        this.errorDesc = SdkError.SYSTEM_ERROR.getDescription();
    }

    public SDKException(Integer errCode, String message) {
        this(errCode, message, message);
    }

    public SDKException(SdkError errEnum) {
        this(errEnum.getCode(), errEnum.getDescription());
    }

    public SDKException(SdkError errEnum, String message) {
        this(errEnum.getCode(), message, message);
    }

    public SDKException(Integer errCode, String message, String chineseMsg) {
        super(message);
        this.errorCode = errCode;
        this.errorDesc = message;
        this.errorDesc = chineseMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
    
    public String getErrorDesc() {
        return errorDesc;
    }
}
