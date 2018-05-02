package io.bumo.sdk.core.event.source;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public class LedgerSeqIncreaseEventSource implements EventSource{

    public static final String CODE = "LEDGER_SEQ_INCREASE_EVENT_SOURCE";

    @Override
    public String getCode(){
        return CODE;
    }

    @Override
    public String getName(){
        return "Block SEQ increases events";
    }

}
