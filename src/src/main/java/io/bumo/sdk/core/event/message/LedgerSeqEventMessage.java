package io.bumo.sdk.core.event.message;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public class LedgerSeqEventMessage implements EventMessage{

    private String host;// node host

    private long seq;// current seq


    public String getHost(){
        return host;
    }

    public void setHost(String host){
        this.host = host;
    }

    public long getSeq(){
        return seq;
    }

    public void setSeq(long seq){
        this.seq = seq;
    }
}
