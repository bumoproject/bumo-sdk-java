package org.bumo.sdk.core.spi;

import org.bumo.sdk.core.transaction.EvalTransaction;
import org.bumo.sdk.core.transaction.Transaction;
import org.bumo.sdk.core.transaction.model.TransactionSerializable;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 * Block chain operation service
 */
public interface BcOperationService{

    /**
     * Use an account pool to open a transaction
     *
     * @see cn.bumo.sdk.core.operation.OperationFactory Operating factory
     */
//    Transaction newTransactionByAccountPool();

    /**
     * new a Transaction
     *
     * @param sponsorAddress Initiator
     * @see org.bumo.sdk.core.operation.OperationFactory Operating factory
     */
    Transaction newTransaction(String sponsorAddress);

    /**
     * Continue a transaction
     *
     * @param transactionSerializable serialized objects
     */
    Transaction continueTransaction(TransactionSerializable transactionSerializable);
    /***
     * Create an assessment of transaction operations
     * @return
     */
    EvalTransaction newEvalTransaction(String sponsorAddress);
    

}
