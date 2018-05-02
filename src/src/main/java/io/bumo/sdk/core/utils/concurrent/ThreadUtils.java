package io.bumo.sdk.core.utils.concurrent;

public abstract class ThreadUtils{

    /**
     * In a non interruptible way, the time specified for the current thread sleep is longer
     *
     * @param millis The length of time to wait; unit milliseconds
     */
    public static void sleepUninterrupted(long millis){
        long start = System.currentTimeMillis();
        long elapseTime = System.currentTimeMillis() - start;
        while (elapseTime < millis) {
            try {
                Thread.sleep(elapseTime);
            } catch (InterruptedException e) {
                // ignore ;
            }
            elapseTime = System.currentTimeMillis() - start;
        }
    }

}
