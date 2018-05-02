package io.bumo.sdk.core.exception;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.ExecutionException;

/**
 * Exception tool
 */
public class ExceptionUtil{

    private ExceptionUtil(){
        // Prevent Instantiation
    }

    public static Throwable unwrapThrowable(Throwable wrapped){
        Throwable unwrapped = wrapped;
        while (true) {
            if (unwrapped instanceof InvocationTargetException) {
                unwrapped = ((InvocationTargetException) unwrapped).getTargetException();
            } else if (unwrapped instanceof UndeclaredThrowableException) {
                unwrapped = ((UndeclaredThrowableException) unwrapped).getUndeclaredThrowable();
            } else if (unwrapped instanceof ExecutionException) {
                unwrapped = unwrapped.getCause();
            } else {
                return unwrapped;
            }
        }
    }

}
