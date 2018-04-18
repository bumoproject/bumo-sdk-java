package org.bumo.sdk.core.event.bottom;

import org.bumo.sdk.core.adapter.bc.RpcService;
import org.bumo.sdk.core.adapter.bc.response.Transaction;
import org.bumo.sdk.core.adapter.bc.response.TransactionHistory;
import org.bumo.sdk.core.adapter.exception.BlockchainError;
import org.bumo.sdk.core.event.EventBusService;
import org.bumo.sdk.core.event.handle.AbstractEventHandler;
import org.bumo.sdk.core.event.message.LedgerSeqEventMessage;
import org.bumo.sdk.core.event.message.TransactionExecutedEventMessage;
import org.bumo.sdk.core.event.source.EventSourceEnum;
import org.bumo.sdk.core.exception.SdkError;
import org.bumo.sdk.core.exec.ExecutorsFactory;
import org.bumo.sdk.core.utils.spring.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 * Transaction failure controller
 */
public class TxFailManager extends AbstractEventHandler<LedgerSeqEventMessage>{

    public static final long SUCCESS = 0;
    public static final long NOT_FOUND = -1;
    public static final long REPEAT_RECEIVE = 3;

    private Map<Long, Set<String>> seqHashMapping = new ConcurrentHashMap<>();// seq-hash mapping
    private Map<String, Set<TransactionExecutedEventMessage>> hashMessageMapping = new ConcurrentHashMap<>();// hash-message mapping

    private final RpcService rpcService;
    private final EventBusService eventBusService;

    public TxFailManager(RpcService rpcService, EventBusService eventBusService){
        super(EventSourceEnum.LEDGER_SEQ_INCREASE.getEventSource(), LedgerSeqEventMessage.class);
        this.rpcService = rpcService;
        this.eventBusService = eventBusService;
    }

    @Override
    public void processMessage(LedgerSeqEventMessage message){
        notifySeqUpdate(message.getSeq());
    }


    /**
     * Add all the failure events
     */
    void addFailEventMessage(TransactionExecutedEventMessage message){
        Set<TransactionExecutedEventMessage> messageList = hashMessageMapping.computeIfAbsent(message.getHash(), hash -> new HashSet<>());
        messageList.add(message);
    }

    /**
     * Failure notification
     */
    void notifyFailEvent(TransactionExecutedEventMessage executedEventMessage){
    	ExecutorsFactory.getExecutorService().execute(new FailProcessor(rpcService, executedEventMessage));
    }

    /**
     * Specify SEQ to add failed hash, store hash-message and seq-hash, release the thread by the block growth notification
     */
    public void finalNotifyFailEvent(long seq, String hash, SdkError sdkError){
        addFailEventMessage(hash, sdkError);
        Set<String> hashSet = seqHashMapping.computeIfAbsent(seq, key -> new HashSet<>());
        hashSet.add(hash);
    }

    private void addFailEventMessage(String hash, SdkError sdkError){
        TransactionExecutedEventMessage message = new TransactionExecutedEventMessage();
        message.setHash(hash);
        message.setSuccess(false);
        message.setErrorCode(String.valueOf(sdkError.getCode()));
        message.setErrorMessage(sdkError.getDescription());
        addFailEventMessage(message);
    }

    /**
     * Block increase, release waiting threads
     */
    private void notifySeqUpdate(long newSeq){
        releaseWaitSeqThread(newSeq);
    }

    private void releaseWaitSeqThread(long waitNotifySeq){
        Set<String> waitExecutor = seqHashMapping.remove(waitNotifySeq);

        if (waitExecutor != null && !waitExecutor.isEmpty()) {
            waitExecutor.forEach(hash -> ExecutorsFactory.getExecutorService().execute(new FailProcessor(rpcService, hashMessageMapping.remove(hash))));
        }
    }


    /**
     * Failure processor
     */
    private class FailProcessor implements Runnable{

        private final RpcService rpcService;
        private final Set<TransactionExecutedEventMessage> executedEventMessages;
        private Logger log = LoggerFactory.getLogger(FailProcessor.class);

        private FailProcessor(RpcService rpcService, Set<TransactionExecutedEventMessage> executedEventMessages){
            this.rpcService = rpcService;
            this.executedEventMessages = executedEventMessages == null ? new HashSet<>() : executedEventMessages;
        }

        private FailProcessor(RpcService rpcService, TransactionExecutedEventMessage executedEventMessage){
            this.rpcService = rpcService;
            this.executedEventMessages = new HashSet<>();
            this.executedEventMessages.add(executedEventMessage);
        }

        @Override
        public void run(){
            if (!executedEventMessages.iterator().hasNext()) {
                return;
            }
            TransactionExecutedEventMessage message = executedEventMessages.iterator().next();
            String txHash = message.getHash();
            TransactionHistory transactionHistory = rpcService.getTransactionHistoryByHash(txHash);

            long errorCode = getErrorCode(txHash, transactionHistory);

            // No transaction record is generated, then the most appropriate error information is extracted from the error heap
            if (errorCode == NOT_FOUND) {
                TransactionExecutedEventMessage failMessage = filterBestMessage();
                eventBusService.publishEvent(EventSourceEnum.TRANSACTION_NOTIFY.getEventSource(), failMessage);
                return;
            }


            // The transaction records are generated, and the state of transaction records is processed directly
            if (errorCode != SUCCESS) {
                String errorDesc = getErrorDesc(txHash, transactionHistory);
                if (StringUtils.isEmpty(errorDesc)) {
                    errorDesc = BlockchainError.getDescription((int) errorCode);
                    if (StringUtils.isEmpty(errorDesc)) {
                        log.warn("errorCode mapping desc not found , errorCode=" + errorCode);
                    }
                }

                message.setErrorCode(String.valueOf(errorCode));
                message.setErrorMessage(errorDesc);
                eventBusService.publishEvent(EventSourceEnum.TRANSACTION_NOTIFY.getEventSource(), message);
            }

        }

        private TransactionExecutedEventMessage filterBestMessage(){
            // Pick out the most suitable error message. 1, because it will receive errorCode3, its priority is the lowest, and other errors will return
            for (TransactionExecutedEventMessage message : executedEventMessages) {
                if (Long.valueOf(message.getErrorCode()) != REPEAT_RECEIVE) {
                    return message;
                }
            }
            return executedEventMessages.iterator().next();
        }

        private long getErrorCode(String txHash, TransactionHistory transactionHistory){
            if (transactionHistory != null) {
                Transaction[] transactions = transactionHistory.getTransactions();
                if (transactions != null && transactions.length > 0) {
                    Transaction transaction = transactionHistory.getTransactions()[0];
                    log.debug("FailProcessor:check txHash," + txHash + ",result:" + transaction.getErrorCode());
                    if (txHash.equals(transaction.getHash())) {
                        return transaction.getErrorCode();
                    }
                }
            }
            return NOT_FOUND;
        }

        private String getErrorDesc(String txHash, TransactionHistory transactionHistory){
            if (transactionHistory != null) {
                Transaction[] transactions = transactionHistory.getTransactions();
                if (transactions != null && transactions.length > 0) {
                    Transaction transaction = transactionHistory.getTransactions()[0];
                    if (txHash.equals(transaction.getHash())) {
                        return transaction.getErrorDesc();
                    }
                }
            }
            return null;
        }
    }

}
