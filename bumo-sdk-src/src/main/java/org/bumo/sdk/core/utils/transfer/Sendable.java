package org.bumo.sdk.core.utils.transfer;

/**
 * Sendable It is the abstraction of the sending operation
 *
 * @author bumo
 */
public interface Sendable<TData>{

    /**
     * send message
     *
     * @param message
     */
    public void send(TData message);

}
