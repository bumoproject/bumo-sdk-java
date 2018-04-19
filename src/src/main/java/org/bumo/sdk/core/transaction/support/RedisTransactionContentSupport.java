package org.bumo.sdk.core.transaction.support;

import java.util.concurrent.TimeUnit;

import org.bumo.sdk.core.seq.redis.SimpleRedisClient;
import org.bumo.sdk.core.spi.BcOperationService;
import org.bumo.sdk.core.transaction.Transaction;
import org.bumo.sdk.core.transaction.model.TransactionSerializable;
import org.bumo.sdk.core.utils.SerializeUtil;
import org.bumo.sdk.core.utils.spring.Assert;
import org.bumo.sdk.core.utils.spring.StringUtils;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public class RedisTransactionContentSupport implements TransactionContentSupport{

    private SimpleRedisClient simpleRedisClient;
    private BcOperationService bcOperationService;

    private final int expect = (int) TimeUnit.DAYS.toSeconds(1);

    public RedisTransactionContentSupport(SimpleRedisClient simpleRedisClient, BcOperationService bcOperationService){
        this.simpleRedisClient = simpleRedisClient;
        this.bcOperationService = bcOperationService;
    }

    @Override
    public void put(String hash, Transaction transaction){
        simpleRedisClient.setEx(hash.getBytes(), SerializeUtil.serialize(transaction.forSerializable()), expect);
    }

    @Override
    public Transaction get(String hash){
        Assert.hasText(hash, "");
        if (StringUtils.isEmpty(hash)) {
            throw new IllegalArgumentException("get transaction hash must not be null");
        }

        byte[] redisObject = simpleRedisClient.get(hash.getBytes());
        if (redisObject == null) {
            return null;
        }

        TransactionSerializable transactionSerializable = SerializeUtil.deserialize(redisObject);
        return bcOperationService.continueTransaction(transactionSerializable);
    }

}
