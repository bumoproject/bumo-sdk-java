package io.bumo.sdk.core.utils.http.converters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.bumo.sdk.core.utils.http.HttpServiceContext;
import io.bumo.sdk.core.utils.http.ResponseConverter;
import io.bumo.sdk.core.utils.http.agent.ServiceRequest;
import io.bumo.sdk.core.utils.spring.StreamUtils;

public class ByteArrayResponseConverter implements ResponseConverter{

    public static final ByteArrayResponseConverter INSTANCE = new ByteArrayResponseConverter();

    private ByteArrayResponseConverter(){
    }

    @Override
    public Object getResponse(ServiceRequest request, InputStream responseStream, HttpServiceContext serviceContext){
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            StreamUtils.copy(responseStream, out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
