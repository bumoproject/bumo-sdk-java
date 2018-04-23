package io.bumo.sdk.core.utils.http;

/**
 * It is used to describe more than 400 errors in the HTTP status code of the calling request
 *
 * @author bumo
 */
public class HttpStatusException extends HttpServiceException{

    private static final long serialVersionUID = 9123750807777784421L;

    private int httpCode;

    public HttpStatusException(int httpCode, String message){
        super(message);
        this.httpCode = httpCode;
        //		this.content = content;
    }

    /**
     * http state code
     *
     * @return
     */
    public int getHttpCode(){
        return httpCode;
    }
}
