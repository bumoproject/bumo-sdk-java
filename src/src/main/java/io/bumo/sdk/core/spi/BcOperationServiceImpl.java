package io.bumo.sdk.core.spi;

import io.bumo.sdk.core.adapter.bc.RpcService;
import io.bumo.sdk.core.balance.NodeManager;
import io.bumo.sdk.core.event.bottom.TxFailManager;
import io.bumo.sdk.core.pool.SponsorAccountPoolManager;
import io.bumo.sdk.core.seq.SequenceManager;
import io.bumo.sdk.core.transaction.EvalTransaction;
import io.bumo.sdk.core.transaction.Transaction;
import io.bumo.sdk.core.transaction.model.TransactionSerializable;
import io.bumo.sdk.core.transaction.sync.TransactionSyncManager;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public class BcOperationServiceImpl implements BcOperationService{

    private SequenceManager sequenceManager;
    private RpcService rpcService;
    private TransactionSyncManager transactionSyncManager;
    private NodeManager nodeManager;
    private TxFailManager txFailManager;
    @SuppressWarnings("unused")
	private SponsorAccountPoolManager sponsorAccountPoolManager;

    public BcOperationServiceImpl(SequenceManager sequenceManager, RpcService rpcService, TransactionSyncManager transactionSyncManager, NodeManager nodeManager, TxFailManager txFailManager, SponsorAccountPoolManager sponsorAccountPoolManager){
        this.sequenceManager = sequenceManager;
        this.rpcService = rpcService;
        this.transactionSyncManager = transactionSyncManager;
        this.nodeManager = nodeManager;
        this.txFailManager = txFailManager;
        this.sponsorAccountPoolManager = sponsorAccountPoolManager;
    }

    @Override
    public Transaction newTransaction(String sponsorAddress){
        return new Transaction(sponsorAddress, sequenceManager, rpcService, transactionSyncManager, nodeManager, txFailManager);
    }

    @Override
    public Transaction continueTransaction(TransactionSerializable transactionSerializable){
        return new Transaction(transactionSerializable, sequenceManager, rpcService, transactionSyncManager, nodeManager, txFailManager);
    }

	@Override
	public EvalTransaction newEvalTransaction(String sponsorAddress) {
		return new EvalTransaction(sequenceManager,rpcService,sponsorAddress);
	}
    
    

}
