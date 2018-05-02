package io.bumo.sdk.core.utils.concurrent;

import java.util.concurrent.TimeoutException;

/**
 * Provides a description of the result of an asynchronous transfer operation
 *
 * @param <TSource>
 * @author bumo
 */
public interface AsyncFuture<TSource>{

    /**
     * Returns the object that executes the asynchronous operation
     *
     * @return
     */
    public TSource getSource();

    /**
     * Whether the operation has been completed
     * <p>
     * When the operation returns successfully or the exception returns, it is indicated that it has been completed
     *
     * @return
     */
    public boolean isDone();

    /**
     * Whether the operation has been successful or not
     *
     * @return
     */
    public boolean isSuccess();

    public String getErrorCode();

    String getErrorMessage();

    /**
     * Return operation exception
     * <p>
     * When it is not completed (isDone method returns to false) or when the operation is normal, it will return to null
     *
     * @return
     */
    public Throwable getException();

    /**
     * Wait for the asynchronous operation to be completed
     *
     * @throws InterruptedException
     */
    public void await() throws InterruptedException, TimeoutException;

    /**
     * Wait for the asynchronous operation to be completed
     *
     * @param timeoutMillis Timeout milliseconds
     * @return true indicates that the operation is completed; false means timeout
     * @throws InterruptedException
     */
    public boolean await(long timeoutMillis) throws InterruptedException, TimeoutException;

    /**
     * Wait for the asynchronous operation to be completed
     * <p>
     * Wait process does not trigger interrupts
     */
    public void awaitUninterruptibly();

    /**
     * Wait for the asynchronous operation to be completed
     * <p>
     * Wait process does not trigger interrupts
     *
     * @param timeoutMillis Timeout milliseconds
     * @return true indicates that the operation is completed; false means timeout
     */
    public boolean awaitUninterruptibly(long timeoutMillis);

    /**
     * Registered listener
     *
     * @param listener
     */
    public void addListener(AsyncFutureListener<TSource> listener);

}
