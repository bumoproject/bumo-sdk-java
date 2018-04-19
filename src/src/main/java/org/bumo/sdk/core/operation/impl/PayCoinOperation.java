package org.bumo.sdk.core.operation.impl;

import org.bumo.sdk.core.adapter.bc.OperationType;
import org.bumo.sdk.core.adapter.bc.response.operation.PayCoin;
import org.bumo.sdk.core.exception.SdkError;
import org.bumo.sdk.core.exception.SdkException;
import org.bumo.sdk.core.extend.protobuf.Chain;
import org.bumo.sdk.core.operation.AbstractBcOperation;
import org.bumo.sdk.core.operation.builder.BaseBuilder;
import org.bumo.sdk.core.utils.Assert;
import org.bumo.sdk.core.utils.spring.StringUtils;

/**
 * @author bumo
 * @since 18/03/16
 * pay BU coin
 */
public class PayCoinOperation extends AbstractBcOperation{
    private PayCoin payCoin = new PayCoin();

    private PayCoinOperation(){
        super(OperationType.PAY_COIN.intValue());
    }

    @Override
    protected void buildOperationContinue(Chain.Operation.Builder operation){
        Chain.OperationPayCoin.Builder operationPayCoin = Chain.OperationPayCoin.newBuilder();
        operationPayCoin.setDestAddress(payCoin.getDestAddress());
        operationPayCoin.setAmount(payCoin.getAmount());
		if(!StringUtils.isEmpty(payCoin.getInput())){
        	operationPayCoin.setInput(payCoin.getInput());
    	}
        operation.setPayCoin(operationPayCoin);
        
    }

    public static class Builder extends BaseBuilder<PayCoinOperation, Builder>{
    	@SuppressWarnings("unused")
		private PayCoin payCoin;
        @Override
        protected PayCoinOperation newOperation(){
        	PayCoinOperation payCoinOperation = new PayCoinOperation();
        	this.payCoin = payCoinOperation.payCoin;
            return payCoinOperation;
        }

        public Builder buildTargetAddress(String targetAddress) throws SdkException{
            return buildTemplate(() -> {
		            	operation.payCoin.setDestAddress(targetAddress);
            		});
        }

        public Builder buildAmount(long amount) throws SdkException{
            return buildTemplate(() -> {
            			operation.payCoin.setAmount(amount);
            		});
        }
        
        public Builder buildInput(String input) throws SdkException{
            return buildTemplate(() -> {
            			operation.payCoin.setInput(input);
            		});
        }


        @Override
        public void checkPass() throws SdkException{
            Assert.notEmpty(operation.payCoin.getDestAddress(), SdkError.OPERATION_ERROR_NOT_DESC_ADDRESS);
            Assert.gtZero(operation.payCoin.getAmount(), SdkError.OPERATION_ERROR_PAYMENT_COIN_ZERO);
        }
        
    }
    public PayCoin getPayCoin() {
		return payCoin;
	}
}
