package io.bumo.sdk.core.transaction.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.bumo.sdk.core.transaction.Transaction;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public class MemoryTransactionContentSupport implements TransactionContentSupport{

    private static final BufferMap<String, Transaction> CACHE = new BufferMap<>();

    @Override
    public void put(String hash, Transaction transaction){
        CACHE.put(hash, transaction);
    }

    @Override
    public Transaction get(String hash){
        return CACHE.get(hash);
    }


    private static class BufferMap<K, V>{

        public volatile int QUEUE_LEN = 4;
        private volatile List<HashMap<K, V>> queue = new ArrayList<>();
        private final int bufferSize = 1000;

        public BufferMap(){
            queue.add(new HashMap<>());
        }

        public void put(K key, V value){

            if (queue.get(queue.size() - 1).size() >= bufferSize) {
                if (queue.size() < QUEUE_LEN) {
                    queue.add(new HashMap<>());
                } else {
                    HashMap<K, V> remove = queue.remove(0);
                    remove.clear();
                    queue.add(remove);
                }
            }

            queue.get(queue.size() - 1).put(key, value);
        }

        public V get(K key){
            for (HashMap<K, V> hm : queue) {
                if (hm.containsKey(key)) {
                    return hm.get(key);
                }

            }
            return null;
        }
    }
}
