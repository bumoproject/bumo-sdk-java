package io.bumo.sdk.core.utils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import io.bumo.sdk.core.utils.spring.TypeUtils;

public abstract class DataTypeUtils{

    private static Map<Class<?>, Class<?>> wrapperTypes = new HashMap<>();

    static{
        wrapperTypes.put(long.class, Long.class);
        wrapperTypes.put(int.class, Integer.class);
        wrapperTypes.put(char.class, Character.class);
        wrapperTypes.put(byte.class, Byte.class);
        wrapperTypes.put(boolean.class, Boolean.class);
    }

    @SuppressWarnings("unused")
	public static boolean isAssignable(Type lhsType, Type rhsType){
        boolean assignable = TypeUtils.isAssignable(lhsType, rhsType);
        if (assignable) {
            return true;
        }
        if (lhsType instanceof Class) {
            Class<?> lhsClass = (Class<?>) lhsType;

        }

        return false;
    }

}
