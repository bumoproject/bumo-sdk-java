package org.bumo.sdk.core.utils.http.agent;

import org.bumo.sdk.core.utils.http.StringConverter;
import org.bumo.sdk.core.utils.http.converters.ObjectToStringConverter;
import org.bumo.sdk.core.utils.spring.BeanUtils;

abstract class StringConverterFactory{

    public static final StringConverter DEFAULT_PARAM_CONVERTER = new ObjectToStringConverter();

    public static StringConverter instantiateStringConverter(Class<?> converterClazz){
        StringConverter converter = null;
        if (converterClazz != null) {
            if (!StringConverter.class.isAssignableFrom(converterClazz)) {
                throw new IllegalHttpServiceDefinitionException(
                        "The specified converter of path param doesn't implement the interface "
                                + StringConverter.class.getName() + "!");
            }

            converter = (StringConverter) BeanUtils.instantiate(converterClazz);
        } else {
            // create default converter
            converter = DEFAULT_PARAM_CONVERTER;
        }
        return converter;
    }
}
