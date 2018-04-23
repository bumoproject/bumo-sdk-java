package io.bumo.sdk.core.utils;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author bumo
 */
public abstract class ArrayUtils{
    private ArrayUtils(){
    }

    @SuppressWarnings("unchecked")
	public static <T> T[] toArray(Iterator<T> itr, Class<T> clazz){
        List<T> lst = new LinkedList<>();
        while (itr.hasNext()) {
            T t = (T) itr.next();
            lst.add(t);
        }
        T[] array = (T[]) Array.newInstance(clazz, lst.size());
        lst.toArray(array);
        return array;
    }

    public static <T> List<T> asList(T[] array){
        return asList(array, 0, array.length);
    }

    public static <T> List<T> asList(T[] array, int fromIndex){
        return asList(array, fromIndex, array.length);
    }

    public static <T> List<T> asList(T[] array, int fromIndex, int toIndex){
        if (toIndex < fromIndex) {
            throw new IllegalArgumentException("The toIndex less than fromIndex!");
        }
        if (fromIndex < 0) {
            throw new IllegalArgumentException("The fromIndex is negative!");
        }
        if (toIndex > array.length) {
            throw new IllegalArgumentException("The toIndex great than the length of array!");
        }

        if (fromIndex == toIndex) {
            return Collections.emptyList();
        }
        return new ReadonlyArrayListWrapper<>(array, fromIndex, toIndex);
    }
}
