package io.bumo.sdk.core.event.handle;

import io.bumo.sdk.core.event.message.EventMessage;
import io.bumo.sdk.core.event.source.EventSource;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 * Event processor
 */
public interface EventHandler{

    /**
     * Event source
     */
    EventSource eventSource();

    /**
     * Event processor
     */
    void onEvent(EventMessage message);

}
