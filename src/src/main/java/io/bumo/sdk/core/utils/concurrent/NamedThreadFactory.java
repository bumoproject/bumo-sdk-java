package io.bumo.sdk.core.utils.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Thread factory that can be named for threads
 *
 * @author bumo
 */
public class NamedThreadFactory implements ThreadFactory{

    private String name;

    private boolean indexThread = false;

    private AtomicLong index;

    private boolean deamon;

    /**
     * create NamedThreadFactory instance；
     *
     * @param name
     */
    public NamedThreadFactory(String name){
        this(name, false, false);
    }

    /**
     * create NamedThreadFactory instance；
     *
     * @param name
     * @param deamon  Whether or not the guard thread
     */
    public NamedThreadFactory(String name, boolean deamon){
        this(name, false, deamon);
    }

    /**
     * create NamedThreadFactory instance
     *
     * @param name        
     * @param indexThread   Whether the number of threads created is recorded; if so, the final thread name is formed by adding index values before the specified thread name, such as "threadName-0, threadName-1"
     * @param deamon        Whether or not the guard thread
     */
    public NamedThreadFactory(String name, boolean indexThread, boolean deamon){
        this.name = name;
        this.indexThread = indexThread;
        if (indexThread) {
            index = new AtomicLong(0);
        }
        this.deamon = deamon;
    }

    @Override
    public Thread newThread(Runnable r){
        String thrdName = name;
        if (indexThread) {
            long i = index.incrementAndGet();
            thrdName = name + "-" + i;
        }
        Thread thrd = new Thread(r, thrdName);
        thrd.setDaemon(deamon);
        return thrd;
    }

}
