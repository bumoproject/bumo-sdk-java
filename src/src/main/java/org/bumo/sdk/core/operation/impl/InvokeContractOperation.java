package org.bumo.sdk.core.operation.impl;

import org.bumo.sdk.core.adapter.bc.OperationType;
import org.bumo.sdk.core.exception.SdkError;
import org.bumo.sdk.core.exception.SdkException;
import org.bumo.sdk.core.extend.protobuf.Chain;
import org.bumo.sdk.core.operation.AbstractBcOperation;
import org.bumo.sdk.core.operation.builder.BaseBuilder;
import org.bumo.sdk.core.utils.Assert;
import org.bumo.sdk.core.utils.spring.StringUtils;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 * Calling contracts is not an independent operation
 */
public class InvokeContractOperation extends AbstractBcOperation{

    private String destAddress;  // Contract address
    private String inputData; // Contract execution parameters

    private InvokeContractOperation(){
        super(OperationType.PAYMENT.intValue());
    } // Contract call use transfer type


    @Override
    protected void buildOperationContinue(Chain.Operation.Builder operation){
        Chain.OperationPayment.Builder operationPayment = Chain.OperationPayment.newBuilder();
        operationPayment.setDestAddress(destAddress);
        if (!StringUtils.isEmpty(inputData)) operationPayment.setInput(inputData);
        operation.setPayment(operationPayment);
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
