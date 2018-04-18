package org.bumo.sdk.core.event.source;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public enum EventSourceEnum{

    LEDGER_SEQ_INCREASE(new LedgerSeqIncreaseEventSource()),
    TRANSACTION_NOTIFY(new TransactionNotifyEventSource()),

    ;
    private EventSource eventSource;

    EventSourceEnum(EventSource eventSource){
        this.eventSource = eventSource;
    }

    public EventSource getEventSource(){
        return eventSource;
    }
}
