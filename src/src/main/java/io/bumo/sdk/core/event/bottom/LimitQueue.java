package io.bumo.sdk.core.event.bottom;

import java.util.LinkedList;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public class LimitQueue<E>{

    private int limit; // queue length

    private LinkedList<E> queue = new LinkedList<>();

    public LimitQueue(int limit){
        this.limit = limit;
    }

    public void offer(E e){
        if (queue.size() >= limit) {
            queue.poll();
        }
        queue.offer(e);
    }

    public boolean exist(E e){
        return queue.contains(e);
    }

}
