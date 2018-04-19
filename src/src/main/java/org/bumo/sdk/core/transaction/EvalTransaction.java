package org.bumo.sdk.core.transaction;

import org.bumo.sdk.core.adapter.bc.OperationType;
import org.bumo.sdk.core.adapter.bc.RpcService;
import org.bumo.sdk.core.adapter.bc.request.test.EvalTXReq;
import org.bumo.sdk.core.adapter.bc.request.test.ReqOperation;
import org.bumo.sdk.core.adapter.bc.request.test.ReqSubTransaction;
import org.bumo.sdk.core.adapter.bc.request.test.ReqTransactionJson;
import org.bumo.sdk.core.adapter.bc.response.test.EvalTxResult;
import org.bumo.sdk.core.adapter.exception.BlockchainException;
import org.bumo.sdk.core.exception.SdkError;
import org.bumo.sdk.core.exception.SdkException;
import org.bumo.sdk.core.operation.BcOperation;
import org.bumo.sdk.core.operation.BuildConsume;
import org.bumo.sdk.core.operation.impl.CreateAccountOperation;
import org.bumo.sdk.core.operation.impl.IssueAssetOperation;
import org.bumo.sdk.core.operation.impl.PayCoinOperation;
import org.bumo.sdk.core.operation.impl.PaymentOperation;
import org.bumo.sdk.core.operation.impl.SetMetadataOperation;
import org.bumo.sdk.core.operation.impl.SetSignerWeightOperation;
import org.bumo.sdk.core.operation.impl.SetThresholdOperation;
import org.bumo.sdk.core.seq.SequenceManager;
import org.bumo.sdk.core.utils.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Eval the fee of transaction
 * @author bumo
 * @since 18/03/16 4:42 p.m.
 */
public class EvalTransaction{

    private static Logger logger = LoggerFactory.getLogger(EvalTransaction.class);
    
    private final RpcService rpcService;
    private final SequenceManager sequenceManager;
    private ReqSubTransaction subTransaction;
    private ReqTransactionJson reqTransactionJson = new ReqTransactionJson();
    private final ReqTransactionJson[] items = new ReqTransactionJson[1];
    private ReqOperation[] opers = null;
    private final EvalTXReq request  = new EvalTXReq();
    private String sponsorAddress;

	/**
     * Normal sponsor
     */
    public EvalTransaction(SequenceManager sequenceManager,RpcService rpcService,String sponsorAddress){
    	this.sequenceManager = sequenceManager;
    	this.rpcService = rpcService;
        this.sponsorAddress = sponsorAddress;
        try {
			buildInit();
		} catch (SdkException e) {
			logger.info("Evaluation of cost transaction construction suboperation failure！");
		}
    }
    
    private final EvalTransaction buildInit() throws SdkException{
        return buildTemplate(() -> {
        	subTransaction= new ReqSubTransaction();
        	subTransaction.setFeeLimit(0);
        	subTransaction.setSourceAddress(sponsorAddress);
        	long now = sequenceManager.getSequenceNumber(sponsorAddress);
        	sequenceManager.restore(sponsorAddress, now-1);
        	subTransaction.setNonce(now);
        });
    }
    
    
    public EvalTransaction buildAddOperations(BcOperation[] operations) throws SdkException{
        return buildTemplate(() -> {
        	for(BcOperation oper : operations ) {
        		buildAddOperation(oper);
        	}
        });
    }

    public EvalTransaction buildAddOperation(BcOperation operation) throws SdkException{
        return buildTemplate(() -> {
            subTransaction.setOperations(createOperArray(operation));
        });
    }

	public EvalTransaction buildAddGasPrice(long gasPrice) throws SdkException{
		return buildTemplate(() -> {
			subTransaction.setGasPrice(gasPrice);
		});
	}

    private ReqOperation[] createOperArray(BcOperation operation) {
    	if (operation != null) {
    		ReqOperation op = new ReqOperation();
    		int now = 0;
    		int future = now + 1;
    		ReqOperation[] temp = new ReqOperation[future];
        	if(opers != null) {
        		now = opers.length;
        		future = now + 1;
        		temp = new ReqOperation[future];
        		System.arraycopy(opers, 0, temp, 0, now);
        	}
        	
        	if(operation instanceof CreateAccountOperation) {
        		CreateAccountOperation createAccountOperation = (CreateAccountOperation)operation;
        		op.setCreateAccount(createAccountOperation.getCreateAccount());
        		op.setType(OperationType.CREATE_ACCOUNT.intValue());
        	}
        	
        	if(operation instanceof IssueAssetOperation) {
        		IssueAssetOperation issueAssetOperation = (IssueAssetOperation)operation;
        		op.setIssueAsset(issueAssetOperation.getIssueAsset());
        		op.setType(OperationType.ISSUE_ASSET.intValue());
        	}
        	
        	if(operation instanceof PaymentOperation) {
        		PaymentOperation paymentOperation = (PaymentOperation)operation;
        		op.setPayment(paymentOperation.getPayment());
        		op.setType(OperationType.PAYMENT.intValue());
        	}
        	
        	if(operation instanceof SetMetadataOperation) {
        		SetMetadataOperation setMetadataOperation = (SetMetadataOperation)operation;
        		op.setSetMetadata(setMetadataOperation.getSetMetadata());
        		op.setType(OperationType.SET_METADATA.intValue());
        	}
        	
        	if(operation instanceof SetSignerWeightOperation) {
        		SetSignerWeightOperation setThresholdOperation = (SetSignerWeightOperation)operation;
        		op.setSetSignerWeight(setThresholdOperation.getSetSignerWeight());
        		op.setType(OperationType.SET_SIGNER_WEIGHT.intValue());
        	}
        	
        	if(operation instanceof SetThresholdOperation) {
        		SetThresholdOperation setThresholdOperation = (SetThresholdOperation)operation;
        		op.setSetThreshold(setThresholdOperation.getSetThreshold());
        		op.setType(OperationType.SET_THRESHOLD.intValue());
        	}
        	
    		if(operation instanceof PayCoinOperation) {
    			PayCoinOperation payCoinOperation = (PayCoinOperation)operation;
    			op.setPayCoin(payCoinOperation.getPayCoin());
    			op.setType(OperationType.PAY_COIN.intValue());
    		}
    		
    		temp[now] = op;
    		opers = temp;
        	op = null;
        }
    	return opers;
    }
    
    private final EvalTransaction  build() throws SdkException {
    	checkBeforeCommit();
    	reqTransactionJson.setReqSubTransaction(subTransaction);
    	items[0] = reqTransactionJson;
    	request.setItems(items);
    	return this;
    }
    
    private void checkBeforeCommit() throws SdkException{
        Assert.notEmpty(sponsorAddress, SdkError.TRANSACTION_ERROR_SPONSOR);
        Assert.notNull(subTransaction.getOperations(), SdkError.OPERATION_ERROR_TEST_OPER);
    }

    private EvalTransaction buildTemplate(BuildConsume buildConsume) throws SdkException{
        buildConsume.build();
        return this;
    }


    public EvalTxResult commit() throws SdkException{
    	build();
		EvalTxResult result = null;
    	try {
			 result = rpcService.testTransaction(request);
		}catch (Exception e){
    		if(e.getCause() instanceof  BlockchainException){
				throw new SdkException(((BlockchainException) e.getCause()).getErrorCode(),((BlockchainException) e.getCause()).getErrorMessage());
			}
		}
    	return result;
            
      }
    
    
}
