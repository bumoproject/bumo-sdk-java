package io.bumo.exception;

/**
 * @author riven
 */
public class SDKException extends RuntimeException{
	private static final long serialVersionUID = 429654902433634386L;
    private Integer errorCode;
    private String errorDesc;
    
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

    public SDKException(SdkError errEnum) {
        this(errEnum.getCode(), errEnum.getDescription());
    }

    public SDKException(SdkError errEnum, String message) {
        this(errEnum.getCode(), message);
    }

    public SDKException(Integer errCode, String message) {
        super(message);
        this.errorCode = errCode;
        this.errorDesc = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
    
    public String getErrorDesc() {
        return errorDesc;
    }
}
