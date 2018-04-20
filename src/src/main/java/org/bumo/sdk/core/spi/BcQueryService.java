package org.bumo.sdk.core.spi;

import org.bumo.sdk.core.adapter.bc.request.test.EvalTXReq;
import org.bumo.sdk.core.adapter.bc.response.Account;
import org.bumo.sdk.core.adapter.bc.response.TransactionHistory;
import org.bumo.sdk.core.adapter.bc.response.operation.SetMetadata;
import org.bumo.sdk.core.adapter.bc.response.test.EvalTxResult;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 * Block chain query service
 */
public interface BcQueryService{

    /**
     * Access to account information
     *
     * @param address Account address
     */
    Account getAccount(String address);

    /**
     * Get the value of the specified account metadata
     *
     * @param address Account address
     * @param key     metadata key
     */
    SetMetadata getAccount(String address, String key);

    /**
     * Get the history of the trade
     *
     * @param hash txHash
     */

    TransactionHistory getTransactionHistoryByHash(String hash);

    /**
     * Get the history of the trade
     *
     * @param hash txHash
     */

    TransactionHistory getTransactionHistoryByLedgerSeq(Long ledgerSeq);
    
    /**
     * TODO Cost of assessment
     * @author bumo
     * @since 18/03/16 3:44 p.m. 
     * @param request
     * @return
     */
    EvalTxResult testTransaction(EvalTXReq request);
    
    
}
