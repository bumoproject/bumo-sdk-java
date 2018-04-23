package io.bumo.sdk.core.operation.impl;

import io.bumo.sdk.core.adapter.bc.OperationType;
import io.bumo.sdk.core.adapter.bc.response.operation.IssueAsset;
import io.bumo.sdk.core.exception.SdkError;
import io.bumo.sdk.core.exception.SdkException;
import io.bumo.sdk.core.extend.protobuf.Chain;
import io.bumo.sdk.core.operation.AbstractBcOperation;
import io.bumo.sdk.core.operation.builder.BaseBuilder;
import io.bumo.sdk.core.utils.Assert;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public class IssueAssetOperation extends AbstractBcOperation{

    private IssueAsset issueAsset = new IssueAsset();

    private IssueAssetOperation(){
        super(OperationType.ISSUE_ASSET.intValue());
    }

    @Override
    protected void buildOperationContinue(Chain.Operation.Builder operation){

        Chain.OperationIssueAsset.Builder operationIssueAsset = Chain.OperationIssueAsset.newBuilder();

        Chain.Asset.Builder asset = Chain.Asset.newBuilder();
        Chain.AssetKey.Builder assetKey = Chain.AssetKey.newBuilder();
        assetKey.setCode(issueAsset.getCode());

        asset.setKey(assetKey);
        asset.setAmount(issueAsset.getAmount());
        operationIssueAsset.setCode(issueAsset.getCode());
        operationIssueAsset.setAmount(issueAsset.getAmount());

        operation.setIssueAsset(operationIssueAsset);
    }


    public static class Builder extends BaseBuilder<IssueAssetOperation, Builder>{
    	
    	private IssueAsset issueAsset;
        @Override
        protected IssueAssetOperation newOperation(){
        	IssueAssetOperation issueAssetOperation = new IssueAssetOperation();
        	this.issueAsset = issueAssetOperation.issueAsset;
            return issueAssetOperation;
        }

        public Builder buildAmount(long amount) throws SdkException{
            return buildTemplate(() -> {
            	this.issueAsset.setAmount(amount);
            });
        }

        public Builder buildAssetCode(String assetCode) throws SdkException{
            return buildTemplate(() ->{ 
            		this.issueAsset.setCode(assetCode);
            	});
        }

        @Override
        public void checkPass() throws SdkException{
            Assert.notEmpty(issueAsset.getCode(), SdkError.OPERATION_ERROR_ISSUE_CODE);
            Assert.gteZero(issueAsset.getAmount(), SdkError.OPERATION_ERROR_ISSUE_AMOUNT_ZERO);
        }

    }

	public IssueAsset getIssueAsset() {
		return issueAsset;
	}
    
}
