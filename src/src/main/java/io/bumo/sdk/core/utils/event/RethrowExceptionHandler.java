package io.bumo.sdk.core.utils.event;

import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

public class RethrowExceptionHandler<TListener> extends ExceptionLoggingHandle<TListener>{

    public RethrowExceptionHandler(Logger logger){
        super(logger);
    }

    @Override
    public void handle(Exception ex, TListener listener, Method method, Object[] args){
        super.handle(ex, listener, method, args);
        if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        }
        throw new UndeclaredThrowableException(ex);
    }

}
