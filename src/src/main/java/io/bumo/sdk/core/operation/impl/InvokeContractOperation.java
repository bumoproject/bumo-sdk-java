package io.bumo.sdk.core.operation.impl;

import io.bumo.sdk.core.adapter.bc.OperationType;
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
 * Calling contracts is not an independent operation
 */
public class InvokeContractOperation extends AbstractBcOperation{

    private String destAddress;  // Contract address
    private String inputData; // Contract execution parameters

    private InvokeContractOperation(){
        super(OperationType.PAYASSET.intValue());
    } // Contract call use transfer type


    @Override
    protected void buildOperationContinue(Chain.Operation.Builder operation){
        Chain.OperationPayAsset.Builder operationPayAsset = Chain.OperationPayAsset.newBuilder();
        operationPayAsset.setDestAddress(destAddress);
        if (!StringUtils.isEmpty(inputData)) operationPayAsset.setInput(inputData);
        operation.setPayAsset(operationPayAsset);
    }


    public static class Builder extends BaseBuilder<InvokeContractOperation, Builder>{

        @Override
        protected InvokeContractOperation newOperation(){
            return new InvokeContractOperation();
        }

        public Builder buildDestAddress(String destAddress) throws SdkException{
            return buildTemplate(() -> operation.destAddress = destAddress);
        }

        public Builder buildInputData(String inputData) throws SdkException{
            return buildTemplate(() -> operation.inputData = inputData);
        }

        @Override
        public void checkPass() throws SdkException{
            Assert.notEmpty(operation.destAddress, SdkError.OPERATION_ERROR_NOT_CONTRACT_ADDRESS);
        }
    }

}
