package org.bumo.sdk.core.event.bottom;

import org.bumo.sdk.core.event.EventBusService;
import org.bumo.sdk.core.event.message.TransactionExecutedEventMessage;
import org.bumo.sdk.core.event.source.EventSourceEnum;
import org.bumo.sdk.core.pool.SponsorAccountPoolManager;
import org.bumo.sdk.core.transaction.sync.TransactionSyncManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 * For multiple processors that receive repeated notification and confirm the wrong results, multiple monitoring threads should share a post processor
 */
public class TxMqHandleProcess{

    private final Logger logger = LoggerFactory.getLogger(TxMqHandleProcess.class);

    private LimitQueue<String> successQueue = new LimitQueue<>(300);
    // Prefix failure queue, go back to error notification
    private LimitQueue<String> failQueue = new LimitQueue<>(300);
    private final Object lock = new Object();

    private TxFailManager txFailManager;
    private EventBusService eventBusService;
    private TransactionSyncManager transactionSyncManager;
    private SponsorAccountPoolManager sponsorAccountPoolManager;

    public TxMqHandleProcess(TxFailManager txFailManager, EventBusService eventBusService, TransactionSyncManager transactionSyncManager, SponsorAccountPoolManager sponsorAccountPoolManager){
        this.txFailManager = txFailManager;
        this.eventBusService = eventBusService;
        this.transactionSyncManager = transactionSyncManager;
        this.sponsorAccountPoolManager = sponsorAccountPoolManager;
    }

    void process(TransactionExecutedEventMessage executedEventMessage){

        String txHash = executedEventMessage.getHash();

        if (!transactionSyncManager.isLocalData(txHash)) {
            // For asynchronous invocation, direct release of address
            sponsorAccountPoolManager.notifyRecover(executedEventMessage.getSponsorAddress());
            return;
        }

        // Go heavy, filter
        synchronized (lock) {
            txFailManager.addFailEventMessage(executedEventMessage);

            // The queue exists and returns directly
            if (successQueue.exist(txHash)) {
                logger.debug("successQueue exist txHash : " + txHash + " , ignore.");
                return;
            }
            if (failQueue.exist(txHash)) {
                logger.debug("failQueue exist txHash : " + txHash + " , ignore.");
                return;
            }

            if (!executedEventMessage.getSuccess()) {
                if (notRepeatErrorCode(executedEventMessage.getErrorCode())) {
                    failQueue.offer(txHash);
                    txFailManager.notifyFailEvent(executedEventMessage);
                }
                return;
            }

            // Success, entering a successful queue, notifications
            successQueue.offer(txHash);
        }

        eventBusService.publishEvent(EventSourceEnum.TRANSACTION_NOTIFY.getEventSource(), executedEventMessage);
    }

    private boolean notRepeatErrorCode(String errorCode){
        return !(TxFailManager.REPEAT_RECEIVE == Long.valueOf(errorCode));
    }

}
