package io.bumo.sdk.core.seq.redis;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public interface SimpleRedisClient{


    void setEx(byte[] key, byte[] value, int seconds);

    byte[] get(byte[] key);

}
