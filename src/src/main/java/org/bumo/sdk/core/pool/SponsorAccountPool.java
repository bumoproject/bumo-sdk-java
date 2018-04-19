package org.bumo.sdk.core.pool;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 * Account pool object
 */
public interface SponsorAccountPool{

    /**
     * Adding an account to an account pool
     *
     * @param sponsorAccounts The account array can be avoided by the account pool concurrency control
     */
    void addSponsorAccount(SponsorAccount... sponsorAccounts);


    /**
     * Obtaining an available initiation account
     */
    SponsorAccount getRichSponsorAccount();

    /**
     * Notification recovery
     *
     * @param address Recovery account address
     */
    void notifyRecover(String address);


}
