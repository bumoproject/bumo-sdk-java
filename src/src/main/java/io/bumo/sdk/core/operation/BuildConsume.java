package io.bumo.sdk.core.operation;

import io.bumo.sdk.core.exception.SdkException;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
@FunctionalInterface
public interface BuildConsume{

    void build() throws SdkException;

}
