package io.bumo.sdk.core.utils.http.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericType<T>{

    private final Type firstTypeArgument;

    protected GenericType(){
        Type superClass = getClass().getGenericSuperclass();

        firstTypeArgument = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    public Type getTypeArgument(){
        return firstTypeArgument;
    }

}
