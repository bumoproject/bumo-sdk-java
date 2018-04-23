package io.bumo.sdk.core.transaction.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.bumo.sdk.core.event.handle.AbstractEventHandler;
import io.bumo.sdk.core.event.message.TransactionExecutedEventMessage;
import io.bumo.sdk.core.event.source.EventSourceEnum;
import io.bumo.sdk.core.exception.SdkError;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Processing synchronous Manager
 */
public class TransactionSyncManager extends AbstractEventHandler<TransactionExecutedEventMessage>{

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionSyncManager.class);

    // Maximum asynchronous super time length
    private static final long ASYNCFUTURE_EXPIRED_TIME = 24 * 3600 * 1000;
    private volatile boolean running = true;
    private Map<String, AsyncFutureTx> txFutures = new ConcurrentHashMap<>();

    public TransactionSyncManager(){
        super(EventSourceEnum.TRANSACTION_NOTIFY.getEventSource(), TransactionExecutedEventMessage.class);
    }

    public void init(){
        Thread thread = new Thread(() -> {
            while (running) {
                evictExpiredListeners();
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                }
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    @SuppressWarnings("unused")
	private void destroy(){
        running = false;
    }

    public void addAsyncFutureTx(AsyncFutureTx asyncFutureTx){
        asyncFutureTx.setTimestamp(System.currentTimeMillis());
        txFutures.put(asyncFutureTx.getSource(), asyncFutureTx);
    }

    public void remove(AsyncFutureTx result){
        txFutures.remove(result.getSource());
    }

    /**
     * Non local call or asynchronous call
     */
    public boolean isLocalData(String hash){
        return txFutures.containsKey(hash);
    }

    public void notifyTransactionAsyncFutures(TransactionExecutedEventMessage executedEventMessage){
        try {
            AsyncFutureTx asyncFutureTx = txFutures.remove(executedEventMessage.getHash());
            if (asyncFutureTx == null) {
                return;
            }
            if (executedEventMessage.getSuccess()) {
                asyncFutureTx.setSuccessFlag();
            } else {
                asyncFutureTx.setErrorFlag(executedEventMessage.getErrorCode(), executedEventMessage.getErrorMessage());
            }
        } catch (Exception e) {
            LOGGER.error(String.format("Error occurred on notifying TRANSACTION_EXECUTED event! --[hash=%s] --[%s] %s", executedEventMessage.getHash(), e.getClass().getName(), e.getMessage()), e);
        }
    }

    private void evictExpiredListeners(){
        try {
            AsyncFutureTx[] asyncFutureTxs = txFutures.values().toArray(new AsyncFutureTx[0]);
            for (AsyncFutureTx asyncFuture : asyncFutureTxs) {
                long now = System.currentTimeMillis();
                long value = now - asyncFuture.getTimestamp();
                if (value > ASYNCFUTURE_EXPIRED_TIME) {
                    txFutures.remove(asyncFuture.getSource());
                    // Timeout anomaly
                    asyncFuture.setErrorFlag(String.valueOf(SdkError.TRANSACTION_SYNC_TIMEOUT.getCode()), SdkError.TRANSACTION_SYNC_TIMEOUT.getDescription());
                }
            }
        } catch (Exception e) {
            LOGGER.error(String.format("Error occurred on evicting expired the transaction state monitor futures! --[%s] %s", e.getClass().getName(), e.getMessage()), e);
        }
    }


    @Override
    public void processMessage(TransactionExecutedEventMessage message){
        notifyTransactionAsyncFutures(message);
    }

}
