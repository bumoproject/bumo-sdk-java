package io.bumo.sdk.core.adapter.bc.response.converter;


import java.io.InputStream;

import io.bumo.sdk.core.utils.http.HttpServiceContext;
import io.bumo.sdk.core.utils.http.ResponseConverter;
import io.bumo.sdk.core.utils.http.agent.ServiceRequest;
import io.bumo.sdk.core.utils.http.converters.JsonResponseConverter;

public abstract class AbstractResponseConverter implements ResponseConverter{
    private JsonResponseConverter jsonResponseConverter = new JsonResponseConverter(ServiceResponse.class);

    @Override
    public Object getResponse(ServiceRequest request, InputStream responseStream, HttpServiceContext serviceContext) throws Exception{
        ServiceResponse serviceResponse = (ServiceResponse) jsonResponseConverter.getResponse(request, responseStream, null);
        //		if (serviceResponse == null ||! "0".equals(serviceResponse.getErrorCode())) {
        //			throw new RuntimeException("errorCode:"+serviceResponse.getErrorCode());
        //		}
        return dealResult(serviceResponse);
    }

    public abstract Object dealResult(ServiceResponse serviceResponse);

}
