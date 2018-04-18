package org.bumo.sdk.core.utils.http.agent;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Properties;

import org.bumo.sdk.core.utils.http.HttpMethod;

public interface ServiceRequest{

    HttpMethod getHttpMethod();

    URI getUri();

    ByteBuffer getBody();

    Properties getRequestParams();

    /**
     * Return the list of parameter values of the service method
     *
     * @return
     */
    Object[] getArgs();

}