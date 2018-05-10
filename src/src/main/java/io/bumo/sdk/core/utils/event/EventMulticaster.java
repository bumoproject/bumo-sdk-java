package io.bumo.sdk.core.utils.event;

import org.slf4j.Logger;

import io.bumo.sdk.core.utils.Disposable;
import io.bumo.sdk.core.utils.spring.ReflectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A method to broadcast a specific listener method call event to multiple listener correspondence is presented
 * <p>
 * A non Object class method that only supports no return value
 *
 * @param <TListener>
 * @author bumo
 */
public class EventMulticaster<TListener> implements Disposable{

    private TListener listenerProxy;

    private Method[] supportedMethods;

    private List<TListener> listeners = new CopyOnWriteArrayList<>();

    private ExceptionHandle<TListener> exHandle;

    public EventMulticaster(Class<TListener> listenerClass){
        this(listenerClass, (ExceptionHandle<TListener>) null);
    }

    public EventMulticaster(Class<TListener> listenerClass, Logger errorLogger){
        this(listenerClass, new ExceptionLoggingHandle<>(errorLogger));
    }

    @SuppressWarnings("unchecked")
	public EventMulticaster(Class<TListener> listenerClass, ExceptionHandle<TListener> exHandle){
        // Initialization error processor
        this.exHandle = exHandle == null ? new DefaultExceptionHandle<>() : exHandle;

        // Parsing unsupported methods
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(listenerClass);
        List<Method> supMths = new LinkedList<>();
        for (Method method : methods) {
            if (method.getDeclaringClass() == Object.class) {
                // Object method does not support
                continue;
            }
            if (method.getReturnType() != void.class) {
                // A method that does not support the return value
                continue;
            }
            supMths.add(method);
        }
        supportedMethods = supMths.toArray(new Method[supMths.size()]);

        // Generation agent class
        listenerProxy = (TListener) Proxy.newProxyInstance(listenerClass.getClassLoader(),
                new Class<?>[] {listenerClass}, new InvocationHandler(){
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
                        multicast(proxy, method, args);
                        return null;
                    }
                });
    }

    private void multicast(Object proxy, Method method, Object[] args){
        boolean supported = false;
        for (Method mth : supportedMethods) {
            if (mth.equals(method)) {
                supported = true;
                break;
            }
        }
        if (supported) {
            doNotify(listeners, method, args);
        } else {
            // The unsupported method was invoked
            throw new UnsupportedOperationException("Unsupported method for event multicasting!");
        }
    }

    protected void doNotify(List<TListener> listeners, Method method, Object[] args){
        for (TListener listener : listeners) {
            doNotifySingle(listener, method, args);
        }
    }

    protected void doNotifySingle(TListener listener, Method method, Object[] args){
        try {
            ReflectionUtils.invokeMethod(method, listener, args);
        } catch (Exception e) {
            exHandle.handle(e, listener, method, args);
        }
    }

    public void addListener(TListener listener){
        listeners.add(listener);
    }

    public void removeListener(TListener listener){
        listeners.remove(listener);
    }

    public TListener broadcast(){
        return listenerProxy;
    }

    @Override
    public void dispose(){
        listeners.clear();
        listeners = null;
        listenerProxy = null;
        supportedMethods = null;
        exHandle = null;
    }

}
