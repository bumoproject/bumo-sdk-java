package io.bumo.sdk.core.operation.impl;

import java.util.ArrayList;
import java.util.List;

import io.bumo.sdk.core.adapter.bc.OperationType;
import io.bumo.sdk.core.adapter.bc.common.Signer;
import io.bumo.sdk.core.adapter.bc.response.operation.SetSignerWeight;
import io.bumo.sdk.core.exception.SdkError;
import io.bumo.sdk.core.exception.SdkException;
import io.bumo.sdk.core.extend.protobuf.Chain;
import io.bumo.sdk.core.operation.AbstractBcOperation;
import io.bumo.sdk.core.operation.builder.BaseBuilder;
import io.bumo.sdk.core.utils.Assert;

/**
 * @author bumo
 * @since 18/03/19 4:23 p.m.
 * Add setting weight
 */
public class SetSignerWeightOperation extends AbstractBcOperation{

    private static final long UNMODIFIED = -1; // If you don't want to modify it, you need to set it to -1

    private long masterWeight = UNMODIFIED;
    private List<Signer> signers = new ArrayList<>();// 0 delete
    
    // Add setting weight 18/03/19 4:23 p.m.
    private SetSignerWeight setSignerWeight = new SetSignerWeight();

    private SetSignerWeightOperation(){
        super(OperationType.SET_SIGNER_WEIGHT.intValue());
    }

    @Override
    protected void buildOperationContinue(Chain.Operation.Builder operation){
        Chain.OperationSetSignerWeight.Builder operationSetSignerWeight = Chain.OperationSetSignerWeight.newBuilder();
        operationSetSignerWeight.setMasterWeight(masterWeight);
        signers.forEach(signer -> {
            Chain.Signer.Builder sign = Chain.Signer.newBuilder();
            sign.setAddress(signer.getAddress());
            sign.setWeight(signer.getWeight());
            operationSetSignerWeight.addSigners(sign);
        });

        operation.setSetSignerWeight(operationSetSignerWeight);
    }


    public static class Builder extends BaseBuilder<SetSignerWeightOperation, Builder>{
    	@SuppressWarnings("unused")
		private SetSignerWeight setSignerWeight ;
        @Override
        protected SetSignerWeightOperation newOperation(){
        	SetSignerWeightOperation setSignerWeightOperation = new SetSignerWeightOperation();
        	this.setSignerWeight = setSignerWeightOperation.setSignerWeight;
            return setSignerWeightOperation;
        }

        public Builder buildMasterWeight(long masterWeight) throws SdkException{
            return buildTemplate(() -> {
            		operation.masterWeight = masterWeight;
            		operation.setSignerWeight.setMasterWeight(masterWeight);
            	});
        }

        public Builder buildAddSigner(String address, long weight) throws SdkException{
            return buildTemplate(() -> {
            		operation.signers.add(new Signer(address, weight));
            		Signer[] signers = new Signer[operation.signers.size()];
            		operation.signers.toArray(signers);
            		
            		operation.setSignerWeight.setSigners(signers);
            		
            	});
        }

        @Override
        public void checkPass() throws SdkException{
            Assert.notTrue(operation.masterWeight == UNMODIFIED && operation.signers.isEmpty(), SdkError.OPERATION_ERROR_SET_SIGNER_WEIGHT);
            Assert.gteExpect(operation.masterWeight, UNMODIFIED, SdkError.OPERATION_ERROR_MASTER_WEIGHT_LT_ZERO);
            Assert.checkCollection(operation.signers, signer -> {
                Assert.notEmpty(signer.getAddress(), SdkError.OPERATION_ERROR_SET_SIGNER_ADDRESS_NOT_EMPTY);
                Assert.gteZero(signer.getWeight(), SdkError.OPERATION_ERROR_SINGER_WEIGHT_LT_ZERO);
            });
        }

    }


	public SetSignerWeight getSetSignerWeight() {
		return setSignerWeight;
	}
    
    
}
