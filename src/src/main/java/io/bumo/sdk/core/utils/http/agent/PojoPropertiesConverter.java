package io.bumo.sdk.core.utils.http.agent;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import io.bumo.sdk.core.utils.EmptyProperties;
import io.bumo.sdk.core.utils.http.PropertiesConverter;
import io.bumo.sdk.core.utils.http.RequestParam;

/**
 * A converter for the POJO property
 *
 * @author bumo
 */
public class PojoPropertiesConverter implements PropertiesConverter{

    private List<String> propNames = new LinkedList<>();

    private RequestParamResolver paramResolver;

    private Class<?> argType;

    public PojoPropertiesConverter(Class<?> argType){
        this.argType = argType;
        try {
            resolveParamProperties();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
    }

    private void resolveParamProperties() throws IntrospectionException{
        List<ArgDefEntry<RequestParamDefinition>> reqParamDefs = new LinkedList<>();

        BeanInfo beanInfo = Introspector.getBeanInfo(argType);
        PropertyDescriptor[] propDescs = beanInfo.getPropertyDescriptors();

        for (PropertyDescriptor propDesc : propDescs) {
            //            TypeDescriptor propTypeDesc;
            //            propTypeDesc = beanWrapper.getPropertyTypeDescriptor(propDesc.getName());


            //            RequestParam reqParamAnno = propTypeDesc.getAnnotation(RequestParam.class);
            RequestParam reqParamAnno = propDesc.getPropertyType().getAnnotation(RequestParam.class);
            if (reqParamAnno == null) {
                // Ignore the attributes of untagged RequestParam
                continue;
            }
            RequestParamDefinition reqParamDef = RequestParamDefinition.resolveDefinition(reqParamAnno);
            ArgDefEntry<RequestParamDefinition> defEntry = new ArgDefEntry<>(reqParamDefs.size(), propDesc.getPropertyType(),
                    reqParamDef);
            reqParamDefs.add(defEntry);
            propNames.add(propDesc.getName());
        }
        paramResolver = RequestParamResolvers.createParamResolver(reqParamDefs);
    }

    @Override
    public Properties toProperties(Object arg){
        if (propNames.size() == 0) {
            return EmptyProperties.INSTANCE;
        }
        Object[] propValues = new Object[propNames.size()];
        int i = 0;
        try {
            for (String propName : propNames) {
                Field field = arg.getClass().getField(propName);
                field.setAccessible(true);
                propValues[i] = field.get(arg);
                i++;
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        Properties params = paramResolver.resolve(propValues);
        return params;
    }

}
