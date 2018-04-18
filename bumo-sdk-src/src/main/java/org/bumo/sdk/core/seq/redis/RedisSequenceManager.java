package org.bumo.sdk.core.seq.redis;

import org.bumo.sdk.core.adapter.bc.RpcService;
import org.bumo.sdk.core.adapter.bc.response.Account;
import org.bumo.sdk.core.adapter.exception.BlockchainError;
import org.bumo.sdk.core.event.message.TransactionExecutedEventMessage;
import org.bumo.sdk.core.event.source.EventSourceEnum;
import org.bumo.sdk.core.exception.SdkError;
import org.bumo.sdk.core.exception.SdkException;
import org.bumo.sdk.core.seq.AbstractSequenceManager;
import org.bumo.sdk.core.utils.spring.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 * Managing SEQ based on redis to realize cluster sharing
 */
public class RedisSequenceManager extends AbstractSequenceManager{

    @SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(RedisSequenceManager.class);

    private RpcService rpcService;
    private RedisClient redisClient;
    private DistributedLock distributedLock;

    public RedisSequenceManager(RpcService rpcService, RedisClient redisClient, DistributedLock distributedLock){
        super(EventSourceEnum.TRANSACTION_NOTIFY.getEventSource(), TransactionExecutedEventMessage.class);
        this.rpcService = rpcService;
        this.redisClient = redisClient;
        this.distributedLock = distributedLock;
    }

    @Override
    public long getSequenceNumber(String address) throws SdkException{
        String lockIdentifier = distributedLock.lockWithTimeout(address, 60 * 1000, 30 * 1000);
        if (StringUtils.isEmpty(lockIdentifier)) {
            throw new SdkException(SdkError.REDIS_ERROR_LOCK_TIMEOUT);
        }

        try {
            Long currentSeq = redisClient.getSeq(address);
            if (currentSeq == null) {
                currentSeq = getSeqByAddress(address);
            }

            long useSeq = currentSeq + 1;
            redisClient.setSeq(address, useSeq);

            return useSeq;
        } finally {
            distributedLock.releaseLock(address, lockIdentifier);
        }
    }

    private long getSeqByAddress(String address) throws SdkException{
        Account account = rpcService.getAccount(address);
        if (account == null) {
            throw new SdkException(BlockchainError.TARGET_NOT_EXIST);
        }
        return account.getNonce();
    }

    @Override
    public void reset(String address){
        redisClient.deleteSeq(address);
    }

    /**
     * In fact, this processing notice should query hash, then confirm that the address corresponding SEQ will be deleted again
     * But if we do this, network communication will cause great delay. If there are other threads in the interval, we will get the corresponding SEQ of address
     * Then there may be a SEQ error problem. Specifically, the hash generation is not confirmed
     * Now we need to clean up the cache directly, so as long as we ensure that the cleaning is fast enough and the time window is very small, we solve the above problems to a certain extent
     * There is no perfect solution to this problem at present
     * todo
     */
    @Override
    public void processMessage(TransactionExecutedEventMessage message){
        if (!message.getSuccess()) {
            reset(message.getSponsorAddress());
        }
    }

	@Override
	public void restoreBack(String address,long oldVal) {
		redisClient.setSeq(address, oldVal);
	}
}
