package org.bumo.sdk.core.utils.http.agent;

import java.io.InputStream;
import java.io.OutputStream;

import org.bumo.sdk.core.utils.http.RequestBodyConverter;
import org.bumo.sdk.core.utils.http.converters.ByteArrayBodyConverter;
import org.bumo.sdk.core.utils.http.converters.InputStreamBodyConverter;
import org.bumo.sdk.core.utils.http.converters.JsonBodyConverter;
import org.bumo.sdk.core.utils.http.converters.ObjectToStringBodyConverter;
import org.bumo.sdk.core.utils.io.EmptyInputStream;
import org.bumo.sdk.core.utils.spring.ClassUtils;

/**
 * Type RequestBody converter with automatic type matching
 *
 * @author haiq
 */
class TypeAutoAdapterBodyConverter implements RequestBodyConverter{

    private static final RequestBodyConverter OBJECT_TO_STRING_CONVERTER = new ObjectToStringBodyConverter();
    private static final RequestBodyConverter INPUT_STREAM_CONVERTER = new InputStreamBodyConverter();
    private static final RequestBodyConverter BYTES_CONVERTER = new ByteArrayBodyConverter();
    //	private static final RequestBodyConverter JSON_CONVERTER = new JsonBodyConverter();

    private RequestBodyConverter converter;

    public TypeAutoAdapterBodyConverter(Class<?> argType){
        converter = createConverter(argType);
    }

    private RequestBodyConverter createConverter(Class<?> argType){
        if (ClassUtils.isAssignable(InputStream.class, argType)) {
            return INPUT_STREAM_CONVERTER;
        }
        if (ClassUtils.isAssignable(String.class, argType)) {
            return OBJECT_TO_STRING_CONVERTER;
        }
        if (ClassUtils.isAssignable(byte[].class, argType)) {
            return BYTES_CONVERTER;
        }
        if (ClassUtils.isPrimitiveOrWrapper(argType)) {
            return OBJECT_TO_STRING_CONVERTER;
        }
        if (ClassUtils.isAssignable(OutputStream.class, argType)) {
            throw new IllegalHttpServiceDefinitionException("Unsupported type for the request body argument!");
        }
        // The default is returned by JSON
        return new JsonBodyConverter(argType);
        //		return JSON_CONVERTER;
    }

    @Override
    public InputStream toInputStream(Object param){
        if (param == null) {
            return EmptyInputStream.INSTANCE;
        }
        return converter.toInputStream(param);
    }

}
