package org.bumo.sdk.core.transaction;

import org.bumo.sdk.core.transaction.support.MemoryTransactionContentSupport;
import org.bumo.sdk.core.transaction.support.TransactionContentSupport;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 * A cache transaction container with buffer is provided, the default is 4000
 * Since transformation has the ability to access redis, static access is not a good way to access
 * But considering compatibility problem, there is no transformation here, and there is no intention of further maintenance
 * It is not recommended that the caller use this object, which does not ensure that subsequent maintenance will rebuild this part
 */
public class TransactionContent{

    private static TransactionContentSupport transactionContentSupport = new MemoryTransactionContentSupport();

    public static void put(String hash, Transaction transaction){
        transactionContentSupport.put(hash, transaction);
    }

    public static Transaction get(String hash){
        return transactionContentSupport.get(hash);
    }

    @Deprecated
    public static void changeQueueLen(int queueLen){}


    /**
     * Switch to support custom through this method
     */
    public static void switchSupport(TransactionContentSupport transactionContentSupport){
        TransactionContent.transactionContentSupport = transactionContentSupport;
    }

}
