package org.bumo.sdk.core.utils.http.agent;

import java.lang.reflect.Method;
import java.nio.charset.Charset;

import org.bumo.sdk.core.utils.http.HttpMethod;
import org.bumo.sdk.core.utils.http.RequestParamFilter;
import org.bumo.sdk.core.utils.http.ResponseConverter;

/**
 * Service operation context
 * <p>
 * A parameter definition of a specific service operation is maintained
 *
 * @author bumo
 */
class ServiceActionContext{

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private Method serviceMethod;

    private HttpMethod requestMethod;

    private RequestPathTemplate pathTemplate;

    private PathParamResolver pathParamResolver;

    private RequestParamFilter requestParamFilter;

    private RequestParamResolver requestParamResolver;

    private RequestBodyResolver requestBodyResolver;

    private ResponseConverter responseConverter;

    private Class<?>[] thrownExceptionTypes;

    private boolean resolveContentOnHttpError;

    /**
     * Create a service operation context
     *
     * @param serviceMethod             The method of service interface
     * @param requestMethod             The HTTP method used by the service operation invocation
     * @param pathTemplate              The HTTP path template for service operations
     * @param pathParamResolver         The HTTP URL path parameter parser for the service operation
     * @param requestParamResolver      The HTTP URL query parameter parser for the service operation
     * @param requestBodyResolver       The HTTP request body parser for the service operation
     * @param responseConverter         The HTTP of service operation successfully replies the result converter
     * @param thrownExceptionTypes      The way of service interface is to list the type of exception by throws keyword
     * @param resolveContentOnHttpError Do you include HTTP content in reply in HTTP error?
     */
    public ServiceActionContext(Method serviceMethod, HttpMethod requestMethod, RequestPathTemplate pathTemplate,
                                PathParamResolver pathParamResolver, RequestParamFilter requestParamFilter,
                                RequestParamResolver requestParamResolver, RequestBodyResolver requestBodyResolver,
                                ResponseConverter responseConverter, Class<?>[] thrownExceptionTypes,
                                boolean resolveContentOnHttpError){
        this.serviceMethod = serviceMethod;
        this.requestMethod = requestMethod;
        this.pathTemplate = pathTemplate;
        this.pathParamResolver = pathParamResolver;
        this.requestParamFilter = requestParamFilter;
        this.requestParamResolver = requestParamResolver;
        this.requestBodyResolver = requestBodyResolver;
        this.responseConverter = responseConverter;
        this.thrownExceptionTypes = thrownExceptionTypes;
        this.resolveContentOnHttpError = resolveContentOnHttpError;
    }

    /**
     * Request path template
     *
     * @return
     */
    public RequestPathTemplate getPathTemplate(){
        return pathTemplate;
    }

    /**
     * Path parameter parser
     *
     * @return
     */
    public PathParamResolver getPathParamResolver(){
        return pathParamResolver;
    }

    /**
     * Request parameter parser
     *
     * @return
     */
    public RequestParamResolver getRequestParamResolver(){
        return requestParamResolver;
    }

    /**
     * Reply to the result converter
     *
     * @return
     */
    public ResponseConverter getResponseConverter(){
        return responseConverter;
    }

    public Method getServiceMethod(){
        return serviceMethod;
    }

    public HttpMethod getRequestMethod(){
        return requestMethod;
    }

    public RequestBodyResolver getRequestBodyResolver(){
        return requestBodyResolver;
    }

    public boolean isResolveContentOnHttpError(){
        return resolveContentOnHttpError;
    }

    public Class<?>[] getThrownExceptionTypes(){
        return thrownExceptionTypes;
    }

    public RequestParamFilter getRequestParamFilter(){
        return requestParamFilter;
    }

}
