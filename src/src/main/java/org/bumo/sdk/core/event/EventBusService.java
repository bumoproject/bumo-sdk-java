package org.bumo.sdk.core.event;

import org.bumo.sdk.core.event.handle.EventHandler;
import org.bumo.sdk.core.event.message.EventMessage;
import org.bumo.sdk.core.event.source.EventSource;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public interface EventBusService{

    void clear();

    void addEventHandler(EventHandler eventHandle);

    void publishEvent(EventSource eventSource, EventMessage eventMessage);

}
