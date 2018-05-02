package io.bumo.sdk.core.utils.http.agent;

import io.bumo.sdk.core.utils.http.PropertiesConverter;
import io.bumo.sdk.core.utils.http.StringConverter;
import io.bumo.sdk.core.utils.spring.BeanUtils;

public class PropertiesConverterFactory{

    public static PropertiesConverter instantiatePropertiesConverter(Class<?> converterClazz, Class<?> argType){
        if (converterClazz == null || PropertiesConverter.class == converterClazz || PojoPropertiesConverter.class == converterClazz) {
            return new PojoPropertiesConverter(argType);
        }
        if (!PropertiesConverter.class.isAssignableFrom(converterClazz)) {
            throw new IllegalHttpServiceDefinitionException(
                    "The specified converter of path param doesn't implement the interface "
                            + StringConverter.class.getName() + "!");
        }

        PropertiesConverter converter = (PropertiesConverter) BeanUtils.instantiate(converterClazz);
        return converter;
    }

}
