package org.bumo.sdk.core.utils.transfer;


import org.bumo.sdk.core.utils.concurrent.AsyncFuture;

/**
 * AsyncMessageSendable Is an abstraction of an asynchronous send operation；
 *
 * @param <TData>
 * @author bumo
 */
public interface AsyncSendable<TSender, TData>{

    /**
     * Asynchronous send message；
     *
     * @param message
     * @return
     */
    public AsyncFuture<TSender> asyncSend(TData message);

}
