package org.bumo.sdk.core.adapter.bc.response.converter;


import java.io.InputStream;

import org.bumo.sdk.core.utils.http.HttpServiceContext;
import org.bumo.sdk.core.utils.http.ResponseConverter;
import org.bumo.sdk.core.utils.http.agent.ServiceRequest;
import org.bumo.sdk.core.utils.http.converters.JsonResponseConverter;

public class GetServiceResponse implements ResponseConverter{
    private JsonResponseConverter jsonResponseConverter = new JsonResponseConverter(ServiceResponse.class);

    @Override
    public Object getResponse(ServiceRequest request, InputStream responseStream, HttpServiceContext serviceContext) throws Exception{
        ServiceResponse serviceResponse = (ServiceResponse) jsonResponseConverter.getResponse(request, responseStream, null);
        return serviceResponse;
    }
}
