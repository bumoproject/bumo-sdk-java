package io.bumo.sdk.core.operation.impl;

import io.bumo.sdk.core.adapter.bc.OperationType;
import io.bumo.sdk.core.adapter.bc.common.Signer;
import io.bumo.sdk.core.adapter.bc.response.TypeThreshold;
import io.bumo.sdk.core.adapter.bc.response.operation.SetPrivilege;
import io.bumo.sdk.core.adapter.bc.response.operation.SetThreshold;
import io.bumo.sdk.core.exception.SdkError;
import io.bumo.sdk.core.exception.SdkException;
import io.bumo.sdk.core.extend.protobuf.Chain;
import io.bumo.sdk.core.operation.AbstractBcOperation;
import io.bumo.sdk.core.operation.builder.BaseBuilder;
import io.bumo.sdk.core.utils.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bumo
 * @since 18/03/19 3:44 p.m. Add an object that sets the threshold
 * 
 */
public class SetPrivilegeOperation extends AbstractBcOperation{

    private static final long UNMODIFIED = -1;// If you don't want to modify it, you need to set it to -1

    private String masterWeight;
    private List<Signer> signers = new ArrayList<>();// 0 delete
    private String txThreshold;
    private List<TypeThreshold> typeThresholds = new ArrayList<>();// 0 delete
    private SetPrivilege setPrivilege = new SetPrivilege();

    private SetPrivilegeOperation(){
        super(OperationType.SET_THRESHOLD.intValue());
    }

    @Override
    protected void buildOperationContinue(Chain.Operation.Builder operation){
        Chain.OperationSetPrivilege.Builder operationSetPrivilege = Chain.OperationSetPrivilege.newBuilder();
        operationSetPrivilege.setMasterWeight(setPrivilege.getMasterWeight());

        typeThresholds.forEach(typeThreshold -> {
            Chain.OperationTypeThreshold.Builder typeThresholdBuilder = Chain.OperationTypeThreshold.newBuilder();
            typeThresholdBuilder
                    .setType(Chain.Operation.Type.forNumber(Integer.valueOf("" + typeThreshold.getType())));
            typeThresholdBuilder.setThreshold(typeThreshold.getThreshold());
            operationSetPrivilege.addTypeThresholds(typeThresholdBuilder);
        });

        operationSetPrivilege.setTxThreshold(txThreshold);
        signers.forEach(signer -> {
            Chain.Signer.Builder sign = Chain.Signer.newBuilder();
            sign.setAddress(signer.getAddress());
            sign.setWeight(signer.getWeight());
            operationSetPrivilege.addSigners(sign);
        });

        operation.setSetPrivilege(operationSetPrivilege);
    }


    public static class Builder extends BaseBuilder<SetPrivilegeOperation, Builder>{
    	@SuppressWarnings("unused")
		private SetPrivilege setPrivilege;
        @Override
        protected SetPrivilegeOperation newOperation(){
        	SetPrivilegeOperation setPrivilegeOperation = new SetPrivilegeOperation();
        	this.setPrivilege = setPrivilegeOperation.setPrivilege;
            return setPrivilegeOperation;
        }


        public Builder buildMasterWeight(String masterWeight) throws SdkException{
            return buildTemplate(() -> {
                operation.masterWeight = masterWeight;
                operation.setPrivilege.setMasterWeight(masterWeight);
            });
        }

        public Builder buildSigners(List<Signer> signerList) throws SdkException{
            return buildTemplate(() -> {
                operation.signers.addAll(signerList);
                Signer[] signers = new Signer[operation.signers.size()];
                operation.signers.toArray(signers);

                operation.setPrivilege.setSigners(signers);

            });
        }

        public Builder buildTxThreshold(String txThreshold) throws SdkException{
            return buildTemplate(() ->
            {
                operation.txThreshold = txThreshold;
                operation.setPrivilege.setTxThreshold(txThreshold);
            });
        }

        public Builder buildTypeThreshold(List<TypeThreshold> typeThresholds) throws SdkException{
            return buildTemplate(() -> {
                operation.typeThresholds.addAll(typeThresholds);

                //添加到对象中
                TypeThreshold[] a = new TypeThreshold[operation.typeThresholds.size()];
                operation.typeThresholds.toArray(a);
                operation.setPrivilege.setTypeThresholds(a);
            });
        }

        public Builder buildAddSigners(String address, long weight) throws SdkException{
            return buildTemplate(() -> {
                operation.signers.add(new Signer(address, weight));
                Signer[] signers = new Signer[operation.signers.size()];
                operation.signers.toArray(signers);

                operation.setPrivilege.setSigners(signers);

            });
        }

        public Builder buildAddTypeThreshold(OperationType type, long threshold) throws SdkException{
            return buildTemplate(() -> {
                Assert.notNull(type, SdkError.OPERATION_ERROR_TX_THRESHOLD_TYPE_NOT_NULL);
                operation.typeThresholds.add(new TypeThreshold(type.intValue(), threshold));

                //添加到对象中
                TypeThreshold[] a = new TypeThreshold[operation.typeThresholds.size()];
                operation.typeThresholds.toArray(a);
                operation.setPrivilege.setTypeThresholds(a);
            });
        }

        @Override
        public void checkPass() throws SdkException{
            Assert.notTrue(operation.signers.isEmpty(), SdkError.OPERATION_ERROR_SET_SIGNER_WEIGHT);
            Assert.checkCollection(operation.signers, signer -> {
                Assert.notEmpty(signer.getAddress(), SdkError.OPERATION_ERROR_SET_SIGNER_ADDRESS_NOT_EMPTY);
                Assert.gteZero(signer.getWeight(), SdkError.OPERATION_ERROR_SINGER_WEIGHT_LT_ZERO);
            });
        }

    }


	public SetPrivilege getSetPrivilege() {
		return setPrivilege;
	}
    
}
