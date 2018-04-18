package org.bumo.sdk.core.event.source;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public class TransactionNotifyEventSource implements EventSource{

    public static final String CODE = "TRANSACTION_NOTIFY_EVENT_SOURCE";

    @Override
    public String getCode(){
        return CODE;
    }

    @Override
    public String getName(){
        return "Transaction notification event source";
    }

}
