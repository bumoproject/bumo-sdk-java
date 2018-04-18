package org.bumo.sdk.core.utils;

import java.util.AbstractList;

/**
 * ReadonlyArrayListWrapper Wrap the array as a read-only List
 *
 * @param <T>
 * @author bumo
 */
public class ReadonlyArrayListWrapper<T> extends AbstractList<T>{

    private T[] array;

    private int fromIndex;

    private int toIndex;

    public ReadonlyArrayListWrapper(T[] array){
        this(array, 0, array.length);
    }

    public ReadonlyArrayListWrapper(T[] array, int fromIndex){
        this(array, fromIndex, array.length);
    }

    public ReadonlyArrayListWrapper(T[] array, int fromIndex, int toIndex){
        if (toIndex < fromIndex) {
            throw new IllegalArgumentException("The toIndex less than fromIndex!");
        }
        if (fromIndex < 0) {
            throw new IllegalArgumentException("The fromIndex is negative!");
        }
        if (toIndex > array.length) {
            throw new IllegalArgumentException("The toIndex great than the length of array!");
        }
        this.array = array;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }

    @Override
    public T get(int index){
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return array[fromIndex + index];
    }

    @Override
    public int size(){
        return toIndex - fromIndex;
    }

}
