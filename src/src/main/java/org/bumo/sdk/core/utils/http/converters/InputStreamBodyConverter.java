package org.bumo.sdk.core.utils.http.converters;

import java.io.InputStream;

import org.bumo.sdk.core.utils.http.RequestBodyConverter;

public class InputStreamBodyConverter implements RequestBodyConverter{

    @Override
    public InputStream toInputStream(Object param){
        return (InputStream) param;
    }

}
