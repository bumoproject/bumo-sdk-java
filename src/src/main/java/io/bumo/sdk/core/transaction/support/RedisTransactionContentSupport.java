package io.bumo.sdk.core.transaction.support;

import java.util.concurrent.TimeUnit;

import io.bumo.sdk.core.seq.redis.SimpleRedisClient;
import io.bumo.sdk.core.spi.OperationService;
import io.bumo.sdk.core.transaction.Transaction;
import io.bumo.sdk.core.transaction.model.TransactionSerializable;
import io.bumo.sdk.core.utils.SerializeUtil;
import io.bumo.sdk.core.utils.spring.Assert;
import io.bumo.sdk.core.utils.spring.StringUtils;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public class RedisTransactionContentSupport implements TransactionContentSupport{

    private SimpleRedisClient simpleRedisClient;
    private OperationService operationService;

    private final int expect = (int) TimeUnit.DAYS.toSeconds(1);

    public RedisTransactionContentSupport(SimpleRedisClient simpleRedisClient, OperationService operationService){
        this.simpleRedisClient = simpleRedisClient;
        this.operationService = operationService;
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
        return operationService.continueTransaction(transactionSerializable);
    }

}
