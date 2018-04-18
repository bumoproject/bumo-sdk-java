package org.bumo.sdk.core.spi;

import org.bumo.sdk.core.adapter.bc.RpcService;
import org.bumo.sdk.core.adapter.bc.request.test.EvalTXReq;
import org.bumo.sdk.core.adapter.bc.response.Account;
import org.bumo.sdk.core.adapter.bc.response.TransactionHistory;
import org.bumo.sdk.core.adapter.bc.response.operation.SetMetadata;
import org.bumo.sdk.core.adapter.bc.response.test.EvalTxResult;
import org.bumo.sdk.core.utils.spring.StringUtils;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public class BcQueryServiceImpl implements BcQueryService{

    private RpcService rpcService;

    public BcQueryServiceImpl(RpcService rpcService){
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
		
		//TODO check args
		return rpcService.testTransaction(request);
	}

}
