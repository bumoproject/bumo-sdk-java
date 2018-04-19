package org.bumo.sdk.core.utils.http.converters;

import org.bumo.sdk.core.utils.http.StringConverter;
import org.bumo.sdk.core.utils.http.util.SerializeUtils;

/**
 * JSON format parameter converter
 *
 * @author bumo
 */
public class JsonConverter implements StringConverter{

    private Class<?> dataType;

    public JsonConverter(Class<?> dataType){
        this.dataType = dataType;
    }

    @Override
    public String toString(Object obj){
        // TODO:The output format of the "date and time" is not defined
        return SerializeUtils.serializeToJSON(obj, dataType);
    }

}
