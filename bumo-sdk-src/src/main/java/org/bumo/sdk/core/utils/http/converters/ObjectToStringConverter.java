package org.bumo.sdk.core.utils.http.converters;

import org.bumo.sdk.core.utils.http.StringConverter;

public class ObjectToStringConverter implements StringConverter{

    @Override
    public String toString(Object param){
        return param == null ? null : param.toString();
    }

}
