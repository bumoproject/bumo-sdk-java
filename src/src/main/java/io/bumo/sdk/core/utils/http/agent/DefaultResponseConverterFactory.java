package io.bumo.sdk.core.utils.http.agent;

import java.lang.reflect.Method;

import io.bumo.sdk.core.utils.http.HttpAction;
import io.bumo.sdk.core.utils.http.ResponseConverter;
import io.bumo.sdk.core.utils.http.ResponseConverterFactory;
import io.bumo.sdk.core.utils.http.converters.ByteArrayResponseConverter;
import io.bumo.sdk.core.utils.http.converters.JsonResponseConverter;
import io.bumo.sdk.core.utils.http.converters.StringResponseConverter;

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
