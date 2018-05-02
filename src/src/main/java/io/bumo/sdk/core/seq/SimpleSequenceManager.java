package io.bumo.sdk.core.seq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.bumo.sdk.core.adapter.bc.RpcService;
import io.bumo.sdk.core.adapter.bc.response.Account;
import io.bumo.sdk.core.adapter.exception.BlockchainError;
import io.bumo.sdk.core.event.message.TransactionExecutedEventMessage;
import io.bumo.sdk.core.event.source.EventSourceEnum;
import io.bumo.sdk.core.exception.SdkException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Simple memory based implementation of SEQ management
 */
public class SimpleSequenceManager extends AbstractSequenceManager{

    private static Logger LOGGER = LoggerFactory.getLogger(SimpleSequenceManager.class);

    private RpcService rpcService;

    private final Object mapMutex = new Object();
    private volatile boolean running = true;
    private long EXPIRED_MILLIS = 15 * 1000;
    private Map<String, SequenceNumber> snMap = new ConcurrentHashMap<>();

    public SimpleSequenceManager(RpcService rpcService){
        super(EventSourceEnum.TRANSACTION_NOTIFY.getEventSource(), TransactionExecutedEventMessage.class);
        this.rpcService = rpcService;
    }

    @Override
    public void init(){
        Thread thrd = new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(30000);

                    evictExpiredSequenceNumber();
                } catch (Exception e) {
                    // swallow error;
                    LOGGER.warn(String.format("Error occurred on evict expired sequence number! --[%s] %s", e.getClass().getName(), e.getMessage()), e);
                }
            }
        });
        thrd.setDaemon(true);
        thrd.start();
    }

    @Override
    public void destroy(){
        running = false;
    }

    private void evictExpiredSequenceNumber(){
        SequenceNumber[] snArray = snMap.values().toArray(new SequenceNumber[snMap.size()]);
        for (SequenceNumber sn : snArray) {
            if (sn.isExpired()) {
                snMap.remove(sn.getAddress());
            }
        }
    }

    @Override
    public long getSequenceNumber(String address) throws SdkException{
        SequenceNumber sn = snMap.get(address);
        if (sn == null) {
            synchronized (mapMutex) {
                sn = snMap.computeIfAbsent(address, SequenceNumber::new);
            }
        }

        return sn.next();
    }

    @Override
    public void reset(String address){
        if (address == null) {
            return;
        }
        LOGGER.debug("SEQUENCEBUNBER RESET! --[address=" + address + "]");
        snMap.remove(address);
    }

    @Override
    public void processMessage(TransactionExecutedEventMessage message){
        if (!message.getSuccess()) {
            reset(message.getSponsorAddress());
        }
    }

    /**
     * sequence
     */
    private class SequenceNumber{

        private final Object mutex = new Object();

        private String address;

        private volatile AtomicLong sn;

        private volatile long initTime;

        public SequenceNumber(String address){
            this.address = address;
            this.initTime = System.currentTimeMillis();
        }

        public boolean isExpired(){
            return (System.currentTimeMillis() - initTime) > EXPIRED_MILLIS;
        }

        public String getAddress(){
            return address;
        }

        public long next() throws SdkException{
            do {
                if (sn == null) {
                    synchronized (mutex) {
                        if (sn == null) {
                            Account account = rpcService.getAccount(address);
                            if (account == null) {
                                throw new SdkException(BlockchainError.TARGET_NOT_EXIST);
                            }
                            long nextSN = account.getNonce();
                            nextSN++;
                            initTime = System.currentTimeMillis();
                            sn = new AtomicLong(nextSN);

                            LOGGER.debug("SEQUENCEBUNBER next! --[next=" + nextSN + "]");
                            return nextSN;
                        }
                    }
                }
                if (isExpired()) {
                    sn = null;
                } else {
                    // Renewal
                    initTime = System.currentTimeMillis();
                    return sn.incrementAndGet();
                }
            } while (true);
        }

    }

	@Override
	public void restoreBack(String address,long oldVal) {
		reset(address);
	}
}
