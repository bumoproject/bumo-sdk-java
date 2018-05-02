package io.bumo.sdk.core.event.source;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 * Simple event definition
 */
public interface EventSource{

    /**
     * Event code
     */
    String getCode();

    /**
     * Event name
     */
    String getName();

}
