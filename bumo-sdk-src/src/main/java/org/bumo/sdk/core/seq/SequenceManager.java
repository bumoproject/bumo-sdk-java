package org.bumo.sdk.core.seq;

import org.bumo.sdk.core.exception.SdkException;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public interface SequenceManager{

    /**
     * Initialization
     */
    void init();

    /**
     * Destroy
     */
    void destroy();

    /**
     * Gets the next submitting transaction sequence number of the specified block chain address
     */
    long getSequenceNumber(String address) throws SdkException;

    /**
     * Reset, call when submission fails
     */
    void reset(String address);
    /**
     * Reply data
     * @param address
     */
    void restore(String address,long oldVal);

}
