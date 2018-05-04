package io.bumo.sdk.core.operation.impl;

import io.bumo.sdk.core.adapter.bc.OperationType;
import io.bumo.sdk.core.adapter.bc.response.Asset;
import io.bumo.sdk.core.adapter.bc.response.Key;
import io.bumo.sdk.core.adapter.bc.response.operation.PayAsset;
import io.bumo.sdk.core.exception.SdkError;
import io.bumo.sdk.core.exception.SdkException;
import io.bumo.sdk.core.extend.protobuf.Chain;
import io.bumo.sdk.core.operation.AbstractBcOperation;
import io.bumo.sdk.core.operation.builder.BaseBuilder;
import io.bumo.sdk.core.utils.Assert;
import io.bumo.sdk.core.utils.spring.StringUtils;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 * Asset transfer
 */
public class PayAssetOperation extends AbstractBcOperation{
	
	private PayAsset payAsset = new PayAsset();

    private PayAssetOperation(){
        super(OperationType.PAYASSET.intValue());
    }

    @Override
    protected void buildOperationContinue(Chain.Operation.Builder operation){
        Chain.OperationPayAsset.Builder operationPayAsset = Chain.OperationPayAsset.newBuilder();
        operationPayAsset.setDestAddress(payAsset.getDestAddress());

        Chain.Asset.Builder asset = Chain.Asset.newBuilder();
        Chain.AssetKey.Builder assetKey = Chain.AssetKey.newBuilder();
        assetKey.setIssuer(payAsset.getAsset().getKey().getIssuer());
        assetKey.setCode(payAsset.getAsset().getKey().getCode());
        asset.setKey(assetKey);
        asset.setAmount(payAsset.getAsset().getAmount());
        operationPayAsset.setAsset(asset);
        if(!StringUtils.isEmpty(payAsset.getInput())){
            operationPayAsset.setInput(payAsset.getInput());
        }
        operation.setPayAsset(operationPayAsset);
    }

    public static class Builder extends BaseBuilder<PayAssetOperation, Builder>{
    	private PayAsset payAsset;
    	
    	
        @Override
        protected PayAssetOperation newOperation(){
        	PayAssetOperation payAssetOperation = new PayAssetOperation();
        	this.payAsset = payAssetOperation.payAsset;
        	Asset asset = new Asset();
        	Key key = new Key();
        	asset.setKey(key);
        	this.payAsset.setAsset(asset);
            return payAssetOperation;
        }

        public Builder buildTargetAddress(String targetAddress) throws SdkException{
            return buildTemplate(() -> {
            		operation.payAsset.setDestAddress(targetAddress);
            	});
        }

        public Builder buildAmount(long amount) throws SdkException{
            return buildTemplate(() -> {
            		this.payAsset.getAsset().setAmount(amount);
            	});
        }

        public Builder buildIssuerAddress(String issuerAddress) throws SdkException{
            return buildTemplate(() -> {
            	payAsset.getAsset().getKey().setIssuer(issuerAddress);
            });
        }

        public Builder buildAssetCode(String assetCode) throws SdkException{
            return buildTemplate(() ->{ 
            		payAsset.getAsset().getKey().setCode(assetCode);
            	});
        }

        public Builder buildInput(String input) throws SdkException{
            return buildTemplate(() -> {
                operation.payAsset.setInput(input);
            });
        }

        @Override
        public void checkPass() throws SdkException{
            Assert.notEmpty(payAsset.getDestAddress(), SdkError.OPERATION_ERROR_NOT_DESC_ADDRESS);
            Assert.notEmpty(payAsset.getAsset().getKey().getIssuer(), SdkError.OPERATION_ERROR_ISSUE_SOURCE_ADDRESS);
            Assert.notEmpty(payAsset.getAsset().getKey().getCode(), SdkError.OPERATION_ERROR_ISSUE_CODE);
            Assert.gtZero(payAsset.getAsset().getAmount(), SdkError.OPERATION_ERROR_PAYASSET_AMOUNT_ZERO);
        }
    }

	public PayAsset getPayAsset() {
		return payAsset;
	}
    
    

}
