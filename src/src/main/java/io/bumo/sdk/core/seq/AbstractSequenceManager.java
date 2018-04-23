package io.bumo.sdk.core.seq;

import io.bumo.sdk.core.event.handle.AbstractEventHandler;
import io.bumo.sdk.core.event.message.TransactionExecutedEventMessage;
import io.bumo.sdk.core.event.source.EventSource;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public abstract class AbstractSequenceManager extends AbstractEventHandler<TransactionExecutedEventMessage> implements SequenceManager{

    public AbstractSequenceManager(EventSource eventSource, Class<TransactionExecutedEventMessage> messageClass){
        super(eventSource, messageClass);
    }

    @Override
    public void init(){
    }

    @Override
    public void destroy(){
    }

	@Override
	public void restore(String address,long oldVal) {
		restoreBack(address,oldVal);
	}

	public abstract void restoreBack(String address,long oldVal);
}
