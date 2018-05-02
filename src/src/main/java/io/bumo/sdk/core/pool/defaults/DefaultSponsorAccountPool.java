package io.bumo.sdk.core.pool.defaults;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.bumo.sdk.core.exec.ExecutorsFactory;
import io.bumo.sdk.core.pool.SponsorAccount;
import io.bumo.sdk.core.pool.SponsorAccountPool;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 * Default account pool implementation
 */
public class DefaultSponsorAccountPool implements SponsorAccountPool{

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSponsorAccountPool.class);

    private ConcurrentLinkedQueue<SponsorAccount> availableQueue = new ConcurrentLinkedQueue<>();// Available queues
    private ConcurrentHashMap<String, SponsorAccount> unAvailableSponsorAccountMap = new ConcurrentHashMap<>();// Unavailable set

    public DefaultSponsorAccountPool(){
    	ExecutorsFactory.getScheduledThreadPoolExecutor().scheduleAtFixedRate(new UnAvailableSponsorAccountCheck(), 1, 30, TimeUnit.MINUTES);
    }

    @Override
    public void addSponsorAccount(SponsorAccount... sponsorAccounts){
        availableQueue.addAll(Arrays.asList(sponsorAccounts));
    }

    @Override
    public SponsorAccount getRichSponsorAccount(){
        SponsorAccount sponsorAccount = availableQueue.poll();
        if (sponsorAccount == null) {
            throw new RuntimeException("getRichSponsorAccount result is null! please check config");
        }
        sponsorAccount.initExpire();
        unAvailableSponsorAccountMap.put(sponsorAccount.getAddress(), sponsorAccount);
        return sponsorAccount;
    }

    @Override
    public void notifyRecover(String address){
        SponsorAccount sponsorAccount = unAvailableSponsorAccountMap.remove(address);
        if (sponsorAccount != null) {
            availableQueue.add(sponsorAccount);
        }
    }

    private class UnAvailableSponsorAccountCheck implements Runnable{

        @Override
        public void run(){
            try {
                Collection<SponsorAccount> sponsorAccounts = unAvailableSponsorAccountMap.values();
                Iterator<SponsorAccount> sponsorAccountIterator = sponsorAccounts.iterator();
                while (sponsorAccountIterator.hasNext()) {
                    SponsorAccount sponsorAccount = sponsorAccountIterator.next();
                    if (sponsorAccount.expire()) {
                        sponsorAccountIterator.remove();
                        availableQueue.add(sponsorAccount);
                    }
                }
            } catch (Exception e) {
                LOGGER.warn("UnAvailableSponsorAccountCheck throw exception!", e);
            }
        }
    }

}
