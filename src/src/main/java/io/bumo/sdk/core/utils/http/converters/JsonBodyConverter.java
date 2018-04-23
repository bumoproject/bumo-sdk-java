package io.bumo.sdk.core.utils.http.converters;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import io.bumo.sdk.core.utils.http.HttpServiceConsts;
import io.bumo.sdk.core.utils.http.RequestBodyConverter;
import io.bumo.sdk.core.utils.http.util.SerializeUtils;

public class JsonBodyConverter implements RequestBodyConverter{

    private Class<?> dataType;

    public JsonBodyConverter(Class<?> dataType){
        this.dataType = dataType;
    }

    @Override
    public InputStream toInputStream(Object param){
        //		String jsonString = JSON.toJSONString(param);
        String jsonString = SerializeUtils.serializeToJSON(param, dataType);
        try {
            return new ByteArrayInputStream(jsonString.getBytes(HttpServiceConsts.CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
