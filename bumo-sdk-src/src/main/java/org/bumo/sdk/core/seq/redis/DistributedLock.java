package org.bumo.sdk.core.seq.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

import java.util.List;
import java.util.UUID;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public class DistributedLock{

    private String lockPrefix = "seqManagerLock:";
    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedLock.class);
    private final ShardedJedisPool jedisPool;

    public DistributedLock(ShardedJedisPool jedisPool){
        this.jedisPool = jedisPool;
    }

    /**
     * Unfair spin acquisition lock
     *
     * @param lockName       the key of the lock
     * @param acquireTimeout Get timeout time
     * @param timeout        The timeout time of the lockThe timeout time of the lock
     * @return Lock identification
     */
    @SuppressWarnings("deprecation")
	public String lockWithTimeout(String lockName, long acquireTimeout, long timeout){
        Jedis conn = null;
        String retIdentifier = null;
        ShardedJedis shardedJedis = jedisPool.getResource();
        try {
            // Random generation of a value
            String identifier = UUID.randomUUID().toString();
            // Lock name, that is, key value
            String lockKey = lockPrefix + lockName;
            // Overtime time. After locking up, the lock is released automatically
            int lockExpire = (int) (timeout / 1000);
            // Access to connections
            conn = shardedJedis.getShard(lockKey);

            // Gets the timeout time of the lock. If it exceeds this time, it will give up the access lock
            long end = System.currentTimeMillis() + acquireTimeout;
            while (System.currentTimeMillis() < end) {
                if (conn.setnx(lockKey, identifier) == 1) {
                    conn.expire(lockKey, lockExpire);
                    // Return the value value for release lock time confirmation
                    retIdentifier = identifier;
                    return retIdentifier;
                }
                // Return to -1, which means key does not set timeout time, set a timeout for key
                if (conn.ttl(lockKey) == -1) {
                    conn.expire(lockKey, lockExpire);
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (JedisException e) {
            LOGGER.error("lockWithTimeout found exception", e);
        } finally {
            if (conn != null) {
                jedisPool.returnResource(shardedJedis);
            }
        }
        return retIdentifier;
    }

    /**
     * Release lock
     *
     * @param lockName   The key of the lock
     * @param identifier The identification of the release lock
     */
    @SuppressWarnings("deprecation")
	public boolean releaseLock(String lockName, String identifier){
        Jedis conn = null;
        String lockKey = lockPrefix + lockName;
        boolean retFlag = false;
        ShardedJedis shardedJedis = jedisPool.getResource();
        try {
            conn = shardedJedis.getShard(lockKey);
            while (true) {
                // Monitor lock, prepare to start the transaction
                conn.watch(lockKey);
                // The value value is returned to determine whether the lock is locked. If the lock is removed, the release lock is released
                if (identifier.equals(conn.get(lockKey))) {
                    Transaction transaction = conn.multi();
                    transaction.del(lockKey);
                    List<Object> results = transaction.exec();
                    if (results == null) {
                        continue;
                    }
                    retFlag = true;
                }
                conn.unwatch();
                break;
            }
        } catch (JedisException e) {
            LOGGER.error("releaseLock found exception", e);
        } finally {
            if (conn != null) {
                jedisPool.returnResource(shardedJedis);
            }
        }
        return retFlag;
    }
}
