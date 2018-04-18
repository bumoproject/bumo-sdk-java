package org.bumo.sdk.core.operation.impl;

import java.util.ArrayList;
import java.util.List;

import org.bumo.sdk.core.adapter.bc.OperationType;
import org.bumo.sdk.core.adapter.bc.response.TypeThreshold;
import org.bumo.sdk.core.adapter.bc.response.operation.SetThreshold;
import org.bumo.sdk.core.exception.SdkError;
import org.bumo.sdk.core.exception.SdkException;
import org.bumo.sdk.core.extend.protobuf.Chain;
import org.bumo.sdk.core.operation.AbstractBcOperation;
import org.bumo.sdk.core.operation.builder.BaseBuilder;
import org.bumo.sdk.core.utils.Assert;

/**
 * @author bumo
 * @since 18/03/19 3:44 p.m. Add an object that sets the threshold
 * 
 */
public class SetThresholdOperation extends AbstractBcOperation{

    private static final long UNMODIFIED = -1;// If you don't want to modify it, you need to set it to -1

    private long txThreshold = UNMODIFIED;
    private List<TypeThreshold> typeThresholds = new ArrayList<>();// 0 delete
    // new Add @18/03/19 3:44 p.m.
    private SetThreshold setThreshold = new SetThreshold(); 

    private SetThresholdOperation(){
        super(OperationType.SET_THRESHOLD.intValue());
    }

    @Override
    protected void buildOperationContinue(Chain.Operation.Builder operation){
        Chain.OperationSetThreshold.Builder operationSetThreshold = Chain.OperationSetThreshold.newBuilder();
        operationSetThreshold.setTxThreshold(txThreshold);

        typeThresholds.forEach(typeThreshold -> {
            Chain.OperationTypeThreshold.Builder typeThresholdBuilder = Chain.OperationTypeThreshold.newBuilder();
            typeThresholdBuilder
                    .setType(Chain.Operation.Type.forNumber(Integer.valueOf("" + typeThreshold.getType())));
            typeThresholdBuilder.setThreshold(typeThreshold.getThreshold());
            operationSetThreshold.addTypeThresholds(typeThresholdBuilder);
        });

        operation.setSetThreshold(operationSetThreshold);
    }


    public static class Builder extends BaseBuilder<SetThresholdOperation, Builder>{
    	@SuppressWarnings("unused")
		private SetThreshold setThreshold;
        @Override
        protected SetThresholdOperation newOperation(){
        	SetThresholdOperation setThresholdOperation = new SetThresholdOperation();
        	this.setThreshold = setThresholdOperation.setThreshold;
            return setThresholdOperation;
        }


        public Builder buildTxThreshold(long txThreshold) throws SdkException{
            return buildTemplate(() -> 
            	{
            		operation.txThreshold = txThreshold;
            		operation.setThreshold.setTxThreshold(txThreshold);
            	});
        }

        public Builder buildAddTypeThreshold(OperationType type, long threshold) throws SdkException{
            return buildTemplate(() -> {
                Assert.notNull(type, SdkError.OPERATION_ERROR_TX_THRESHOLD_TYPE_NOT_NULL);
                operation.typeThresholds.add(new TypeThreshold(type.intValue(), threshold));
                
                //添加到对象中
                TypeThreshold[] a = new TypeThreshold[operation.typeThresholds.size()];
                operation.typeThresholds.toArray(a);
                operation.setThreshold.setTypeThresholds(a);
            });
        }

        @Override
        public void checkPass() throws SdkException{
            Assert.notTrue(operation.txThreshold == UNMODIFIED && operation.typeThresholds.isEmpty(), SdkError.OPERATION_ERROR_SET_THRESHOLD);
            Assert.gteExpect(operation.txThreshold, UNMODIFIED, SdkError.OPERATION_ERROR_TX_THRESHOLD_LT_ZERO);
            Assert.checkCollection(operation.typeThresholds, typeThreshold -> Assert.gteZero(typeThreshold.getThreshold(), SdkError.OPERATION_ERROR_TX_THRESHOLD_TYPE_LT_ZERO));
        }

    }


	public SetThreshold getSetThreshold() {
		return setThreshold;
	}
    
}
