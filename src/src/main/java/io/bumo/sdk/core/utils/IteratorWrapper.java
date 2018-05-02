package io.bumo.sdk.core.utils;

import java.util.Iterator;

/**
 * IteratorWrapper is an adapter for iterators
 *
 * @param <T1>
 * @param <T2>
 * @author bumo
 */
public abstract class IteratorWrapper<T1, T2> implements Iterator<T1>{

    protected Iterator<T2> wrappedIterator;

    public IteratorWrapper(Iterator<T2> itr){
        this.wrappedIterator = itr;
    }

    @Override
    public boolean hasNext(){
        return wrappedIterator.hasNext();
    }

    @Override
    public T1 next(){
        return wrap(wrappedIterator.next());
    }

    protected abstract T1 wrap(T2 t2);

}
