package org.bumo.sdk.core.utils.concurrent;

/**
 * AsyncFuture implementation for adaptive synchronization operation
 *
 * @param <TSource>
 * @author bumo
 */
public class SyncFutureAdaptor<TSource> implements AsyncFuture<TSource>{

    private TSource source;

    private boolean success = false;
    private Throwable exception;
    private String errorCode;
    private String errorMessage;

    /**
     * create SyncFutureAdaptor instance
     *
     * @param source    Operating object
     * @param exception The exception caused by the completion of the operation; if designated null, it indicates that the operation returned successfully without causing an exception
     * @param errorCode Error code
     */
    private SyncFutureAdaptor(TSource source, Throwable exception, String errorCode){
        this.source = source;
        this.success = exception == null;
        this.exception = exception;
        this.errorCode = errorCode;
    }

    /**
     * Create AsyncFuture instances that indicate successful completion of operations
     *
     * @param source Objects that perform operations
     * @return AsyncFuture instance
     */
    public static <T> AsyncFuture<T> createSuccessFuture(T source){
        return new SyncFutureAdaptor<>(source, null, null);
    }

    /**
     * Create an instance of AsyncFuture that indicates the exception returned by the operation
     *
     * @param source    Objects that perform operations
     * @param exception An exception that is triggered by an operation; not allowed to be null
     * @return AsyncFuture instance
     */
    public static <T> AsyncFuture<T> createErrorFuture(T source, Throwable exception){
        if (exception == null) {
            throw new IllegalArgumentException("Exception is null!");
        }
        return new SyncFutureAdaptor<>(source, exception, null);
    }

    /**
     * Create an instance of AsyncFuture that indicates the exception returned by the operation
     *
     * @param source    Objects that perform operations
     * @param errorCode Error code caused by operation
     * @return AsyncFuture instance
     */
    public static <T> AsyncFuture<T> createErrorFuture(T source, String errorCode){
        if (errorCode == null || errorCode.length() == 0) {
            throw new IllegalArgumentException("ErrorCode is empty!");
        }
        return new SyncFutureAdaptor<>(source, null, errorCode);
    }

    @Override
    public TSource getSource(){
        return source;
    }

    @Override
    public boolean isDone(){
        return true;
    }

    @Override
    public boolean isSuccess(){
        return success;
    }

    @Override
    public Throwable getException(){
        return exception;
    }

    @Override
    public void await() throws InterruptedException{
        return;
    }

    @Override
    public boolean await(long timeoutMillis) throws InterruptedException{
        return true;
    }

    @Override
    public void awaitUninterruptibly(){
        return;
    }

    @Override
    public boolean awaitUninterruptibly(long timeoutMillis){
        return true;
    }

    @Override
    public void addListener(AsyncFutureListener<TSource> listener){
        // Synchronization operation has been completed；
        listener.complete(this);
    }

    @Override
    public String getErrorCode(){
        return this.errorCode;
    }

    @Override
    public String getErrorMessage(){
        return this.errorMessage;
    }

}
