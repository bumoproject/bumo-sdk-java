package org.bumo.sdk.core.utils.http.converters;

import java.io.InputStream;

import org.bumo.sdk.core.utils.http.HttpServiceContext;
import org.bumo.sdk.core.utils.http.ResponseConverter;
import org.bumo.sdk.core.utils.http.agent.ServiceRequest;
import org.bumo.sdk.core.utils.http.util.SerializeUtils;

public class JsonResponseConverter implements ResponseConverter{

    private Class<?> clazz;

    public JsonResponseConverter(Class<?> clazz){
        this.clazz = clazz;
    }

    @Override
    public Object getResponse(ServiceRequest request, InputStream responseStream, HttpServiceContext serviceContext) throws Exception{
        String jsonResponse = (String) StringResponseConverter.INSTANCE.getResponse(request, responseStream, null);
        if (jsonResponse == null) {
            return null;
        }
        jsonResponse = jsonResponse.trim();
        // TODO: A policy that does not specify the "date and time" format
        return SerializeUtils.deserializeAs(jsonResponse, clazz);
    }

}
