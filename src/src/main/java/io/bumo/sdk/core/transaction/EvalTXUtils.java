package io.bumo.sdk.core.transaction;

import io.bumo.sdk.core.adapter.bc.response.test.EvalTxResult;
import io.bumo.sdk.core.config.SDKEngine;
import io.bumo.sdk.core.exception.SdkException;
import io.bumo.sdk.core.operation.BcOperation;

/***
 * 
 * @author bumo
 *
 */
public final class EvalTXUtils {
	public static final long GAS_PRICE = 100;
	

	public static final long getFeeByArgs(String address, long gasPrice,BcOperation bcOperation) throws SdkException{
		EvalTransaction evalTX = SDKEngine.getInstance().getOperationService().newEvalTransaction(address);
		EvalTxResult result = evalTX
      		  .buildAddGasPrice(gasPrice)
      		  .buildAddOperation(bcOperation).commit();
		return result.getActualFee();
	}
	
	
	public static final long getFeeByArgs(String address, long gasPrice,BcOperation[] bcOperations) throws SdkException{
		EvalTransaction evalTX = SDKEngine.getInstance().getOperationService().newEvalTransaction(address);
		EvalTxResult result = evalTX
      		  .buildAddGasPrice(gasPrice)
      		  .buildAddOperations(bcOperations).commit();
		return result.getActualFee();
	}

}
