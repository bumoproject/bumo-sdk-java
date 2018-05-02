package io.bumo.sdk.core.utils.http.converters;

import io.bumo.sdk.core.utils.http.StringConverter;

public class ObjectToStringConverter implements StringConverter{

    @Override
    public String toString(Object param){
        return param == null ? null : param.toString();
    }

}
