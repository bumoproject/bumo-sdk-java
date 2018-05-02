package io.bumo.sdk.core.transaction.support;

import io.bumo.sdk.core.transaction.Transaction;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public interface TransactionContentSupport{

    void put(String hash, Transaction transaction);

    Transaction get(String hash);

}
