package io.bumo.sdk.core.utils.http.converters;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import io.bumo.sdk.core.utils.http.HttpServiceConsts;
import io.bumo.sdk.core.utils.http.RequestBodyConverter;

public class ObjectToStringBodyConverter implements RequestBodyConverter{

    @Override
    public InputStream toInputStream(Object param){
        try {
            String text = param.toString();
            byte[] bytes = text.getBytes(HttpServiceConsts.CHARSET);
            return new ByteArrayInputStream(bytes);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
