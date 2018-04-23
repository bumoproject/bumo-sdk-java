package io.bumo.sdk.core.utils.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AsyncFutureBase<TSource> implements AsyncFuture<TSource>{

    private static Logger LOGGER = LoggerFactory.getLogger(AsyncFutureBase.class);

    private static final int UNDONE = 0;
    private static final int SUCCESS = 1;
    private static final int ERROR = 2;

    private TSource source;

    private AtomicInteger state = new AtomicInteger(UNDONE);
    private volatile Throwable exception;

    private volatile String errorCode;
    private volatile String errorMessage;

    private CountDownLatch completedLatch = new CountDownLatch(1);

    private CopyOnWriteArrayList<AsyncFutureListener<TSource>> listeners = new CopyOnWriteArrayList<>();

    public AsyncFutureBase(TSource source){
        this.source = source;
    }

    @Override
    public TSource getSource(){
        return source;
    }

    @Override
    public boolean isDone(){
        return state.get() != UNDONE;
    }

    @Override
    public boolean isSuccess(){
        return state.get() == SUCCESS;
    }

    @Override
    public Throwable getException(){
        return exception;
    }

    @Override
    public String getErrorCode(){
        return errorCode;
    }

    @Override
    public String getErrorMessage(){
        return errorMessage;
    }

    @Override
    public void await() throws InterruptedException{
        await(-1L, false);
    }

    @Override
    public boolean await(long timeoutMillis) throws InterruptedException{
        return await(timeoutMillis, false);
    }

    @Override
    public void awaitUninterruptibly(){
        try {
            await(-1L, false);
        } catch (InterruptedException e) {
        	e.printStackTrace();
        }
    }

    @Override
    public boolean awaitUninterruptibly(long timeoutMillis){
        try {
            return await(timeoutMillis, false);
        } catch (InterruptedException e) {
        	e.printStackTrace();
            return false;
        }
    }

    private boolean await(long timeoutMillis, boolean interruptibly) throws InterruptedException{
        if (timeoutMillis < 0) {
            timeoutMillis = Long.MAX_VALUE;
        }
        long startTs = System.currentTimeMillis();
        long endTs = startTs;
        long leftTs = timeoutMillis;
        boolean ready = false;
        do {
            try {
                ready = completedLatch.await(leftTs, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                if (interruptibly) {
                    throw e;
                }
            }
            if (ready) {
                return ready;
            }
            endTs = System.currentTimeMillis();
            leftTs = timeoutMillis - endTs + startTs;
            if (leftTs <= 0) {
                return isDone();
            }
        } while (true);
    }

    @Override
    public void addListener(AsyncFutureListener<TSource> listener){
        if (listener == null) {
            throw new IllegalArgumentException("Null listener!");
        }
        listeners.add(listener);
    }

    protected void setSuccess(){
        if (isDone()) {
            return;
        }
        boolean ok = state.compareAndSet(UNDONE, SUCCESS);
        if (ok) {
            complete();
        }
    }

    protected void setError(Throwable ex){
        if (isDone()) {
            return;
        }
        boolean ok = state.compareAndSet(UNDONE, ERROR);
        if (ok) {
            exception = ex;
            complete();
        }
    }

    protected void setError(String errorCode){
        setError(errorCode, null);
    }

    protected void setError(String errorCode, String errorMessage){
        if (isDone()) {
            return;
        }
        boolean ok = state.compareAndSet(UNDONE, ERROR);
        if (ok) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
            complete();
        }
    }

    private void complete(){
        completedLatch.countDown();
        // fire event;
        for (AsyncFutureListener<TSource> asyncFutureListener : listeners) {
            fireCompleted(asyncFutureListener);
        }
    }

    private void fireCompleted(AsyncFutureListener<TSource> asyncFutureListener){
        try {
            asyncFutureListener.complete(this);
        } catch (Exception e) {
            LOGGER.error("Error occurred on fire completed event to AsyncFutureListener["
                    + asyncFutureListener.getClass().getName() + "]!!!--" + e.getMessage(), e);
        }
    }
}
