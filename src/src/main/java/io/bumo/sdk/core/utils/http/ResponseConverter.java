package io.bumo.sdk.core.utils.http;

import java.io.InputStream;

import io.bumo.sdk.core.utils.http.agent.ServiceRequest;

/**
 * Reply to the result converter
 * <p>
 * It is used to define how to translate text results from HTTP to a specific object
 * <p>
 * When the type of exception thrown by ResponseConvert exists in the exception list of the operation method declaration of the service interface, the exception will be directly returned to the caller
 *
 * @author bumo
 */
public interface ResponseConverter{

    // TODO Support is parsed in HTTP state
    public Object getResponse(ServiceRequest request, InputStream responseStream, HttpServiceContext serviceContext) throws Exception;

}
