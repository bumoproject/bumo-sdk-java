package org.bumo.sdk.core.utils.event;

import java.lang.reflect.Method;

/**
 * ExceptionHandle Defines the processing interface of exception appearing in event notification of event listener
 *
 * @param <TListener>
 * @author bumo
 */
public interface ExceptionHandle<TListener>{

    /**
     * Processing listener exception
     *
     * @param ex       exception
     * @param listener An exception listener instance
     * @param method   A method of abnormal occurrence
     * @param args     Method parameters
     */
    public void handle(Exception ex, TListener listener, Method method, Object[] args);
}
