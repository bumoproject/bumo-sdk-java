package org.bumo.sdk.core.seq.redis;

import org.bumo.sdk.core.utils.spring.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public class RedisClient implements SimpleRedisClient{

    private String seqAddressPrefix = "seqManagerAddress:";
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisClient.class);
    private ShardedJedisPool pool = null;
    private List<RedisConfig> redisConfigs;

    public RedisClient(List<RedisConfig> redisConfigs){
        this.redisConfigs = redisConfigs;
    }

    public void init(){
        if (pool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(50);
            config.setMaxIdle(20);
            config.setMaxWaitMillis(1000 * 100);
            config.setTestOnBorrow(true);
            config.setTestOnReturn(true);

            // Scheme://username:password@IP:PORT/dbindex
            // redis://user:bumo8881@192.168.10.732:103793/04
            // redis://user:bumo888@192.168.10.73:10379/5
            List<JedisShardInfo> shards = redisConfigs.stream().map(redisConfig -> {
                String urlHost = String.format("redis://user:%s@%s:%s/%s", redisConfig.getPassword(), redisConfig.getHost(), redisConfig.getPort(), redisConfig.getDatabase());
                return new JedisShardInfo(urlHost);
            }).collect(Collectors.toList());

            pool = new ShardedJedisPool(config, shards);
        }
    }

    @Override
    public void setEx(byte[] key, byte[] value, int seconds){
        consumeJedis(jedis -> jedis.setex(key, seconds, value));
    }

    @Override
    public byte[] get(byte[] key){
        return (byte[]) callJedis(jedis -> jedis.get(key));
    }


    public void setEx(String key, String value, int seconds){
        if (key == null || value == null) {
            return;
        }

        consumeJedis(jedis -> jedis.setex(key, seconds, value));
    }

    public String get(String key){
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        return (String) callJedis(jedis -> jedis.get(key));
    }

    public void setSeq(String address, Long value){
        setEx(seqAddressPrefix + address, value.toString(), 60);
    }

    public Long getSeq(String address){
        if (StringUtils.isEmpty(address)) {
            return null;
        }

        String value = get(seqAddressPrefix + address);

        return value == null ? null : Long.valueOf(value);

    }

    public void deleteSeq(String address){
        if (StringUtils.isEmpty(address)) {
            return;
        }

        consumeJedis(jedis -> jedis.del(seqAddressPrefix + address));
    }


    public void consumeJedis(Consumer<ShardedJedis> consumer){
        callJedis(jedis -> {
            consumer.accept(jedis);
            return null;
        });
    }

    @SuppressWarnings("deprecation")
	public Object callJedis(Function<ShardedJedis, Object> function){
        ShardedJedis jedis = null;
        Object result = null;
        try {
            jedis = pool.getResource();
            result = function.apply(jedis);
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            pool.returnResource(jedis);
        }
        return result;
    }

    public ShardedJedisPool getPool(){
        return pool;
    }

}
