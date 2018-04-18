package org.bumo.sdk.core.utils.http.agent;

import java.lang.reflect.Method;

import org.bumo.sdk.core.utils.http.HttpAction;
import org.bumo.sdk.core.utils.http.ResponseConverter;
import org.bumo.sdk.core.utils.http.ResponseConverterFactory;
import org.bumo.sdk.core.utils.http.converters.ByteArrayResponseConverter;
import org.bumo.sdk.core.utils.http.converters.JsonResponseConverter;
import org.bumo.sdk.core.utils.http.converters.StringResponseConverter;

public class DefaultResponseConverterFactory implements ResponseConverterFactory{

    public static final DefaultResponseConverterFactory INSTANCE = new DefaultResponseConverterFactory();

    private DefaultResponseConverterFactory(){
    }

    @Override
    public ResponseConverter createResponseConverter(HttpAction actionDef, Method mth){
        Class<?> retnClazz = mth.getReturnType();
        // create default response converter;
        if (byte[].class == retnClazz) {
            return ByteArrayResponseConverter.INSTANCE;
        }
        if (String.class == retnClazz) {
            return StringResponseConverter.INSTANCE;
        }

        // TODO:Unprocessed basic types, input and output streams
        return new JsonResponseConverter(retnClazz);
    }

}
