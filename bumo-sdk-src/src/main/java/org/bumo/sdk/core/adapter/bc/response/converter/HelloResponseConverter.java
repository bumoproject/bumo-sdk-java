package org.bumo.sdk.core.adapter.bc.response.converter;

import java.io.InputStream;

import org.bumo.sdk.core.adapter.bc.response.Hello;
import org.bumo.sdk.core.utils.http.HttpServiceContext;
import org.bumo.sdk.core.utils.http.ResponseConverter;
import org.bumo.sdk.core.utils.http.agent.ServiceRequest;
import org.bumo.sdk.core.utils.http.converters.StringResponseConverter;
import org.bumo.sdk.core.utils.http.util.SerializeUtils;

/**
 * @author 布萌
 * @since 18/3/20 下午5:47.
 */
public class HelloResponseConverter implements ResponseConverter{

    @Override
    public Object getResponse(ServiceRequest serviceRequest, InputStream inputStream, HttpServiceContext httpServiceContext) throws Exception{
        String jsonResponse = (String) StringResponseConverter.INSTANCE.getResponse(serviceRequest, inputStream, null);
        if (jsonResponse == null) {
            return null;
        } else {
            jsonResponse = jsonResponse.trim();
            return SerializeUtils.deserializeAs(jsonResponse, Hello.class);
        }
    }

}
