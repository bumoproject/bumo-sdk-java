package org.bumo.sdk.core.utils.concurrent;

public interface AsyncFutureListener<TSource>{

    public void complete(AsyncFuture<TSource> future);

}
