package io.bumo.sdk.core.spi;

import io.bumo.sdk.core.adapter.bc.RpcService;
import io.bumo.sdk.core.adapter.bc.request.test.EvalTXReq;
import io.bumo.sdk.core.adapter.bc.response.Account;
import io.bumo.sdk.core.adapter.bc.response.TransactionHistory;
import io.bumo.sdk.core.adapter.bc.response.ledger.Ledger;
import io.bumo.sdk.core.adapter.bc.response.operation.SetMetadata;
import io.bumo.sdk.core.adapter.bc.response.test.EvalTxResult;
import io.bumo.sdk.core.utils.spring.StringUtils;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public class QueryServiceImpl implements QueryService {

    private RpcService rpcService;

    public QueryServiceImpl(RpcService rpcService){
        this.rpcService = rpcService;
    }


    @Override
    public Account getAccount(String address){
        if (StringUtils.isEmpty(address)) {
            throw new IllegalArgumentException("query account method address must not null!");
        }
        return rpcService.getAccount(address);
    }

    @Override
    public SetMetadata getAccount(String address, String key){
        if (StringUtils.isEmpty(address) || StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("query account method address and key must not null!");
        }
        return rpcService.getAccountMetadata(address, key);
    }

    @Override
    public TransactionHistory getTransactionHistoryByHash(String hash){
        if (StringUtils.isEmpty(hash)) {
            throw new IllegalArgumentException("query getTransactionHistoryByHash method hash must not null!");
        }
        return rpcService.getTransactionHistoryByHash(hash);
    }

    /**
     * TODO Cost of assessment
     */
	@Override
	public EvalTxResult testTransaction(EvalTXReq request) {
		return rpcService.testTransaction(request);
	}


	@Override
	public TransactionHistory getTransactionHistoryByLedgerSeq(Long ledgerSeq) {
		return rpcService.getTransactionHistoryByLedgerSeq(ledgerSeq);
	}
	
	@Override
	public Ledger getLatestLedger() {
		return rpcService.getLedger();
	}

}
