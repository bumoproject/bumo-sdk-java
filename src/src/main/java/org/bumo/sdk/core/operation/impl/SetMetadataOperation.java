package org.bumo.sdk.core.operation.impl;

import org.bumo.sdk.core.adapter.bc.OperationType;
import org.bumo.sdk.core.adapter.bc.response.operation.SetMetadata;
import org.bumo.sdk.core.exception.SdkError;
import org.bumo.sdk.core.exception.SdkException;
import org.bumo.sdk.core.extend.protobuf.Chain;
import org.bumo.sdk.core.operation.AbstractBcOperation;
import org.bumo.sdk.core.operation.builder.BaseBuilder;
import org.bumo.sdk.core.utils.Assert;

/**
 * @author bumo
 * @since 18/03/19 4:53 p.m.
 * One metadata update can only update one
 */
public class SetMetadataOperation extends AbstractBcOperation{

    private SetMetadata setMetadata;

    private SetMetadataOperation(){
        super(OperationType.SET_METADATA.intValue());
    }

    @Override
    protected void buildOperationContinue(Chain.Operation.Builder operation){
        Chain.OperationSetMetadata.Builder operationSetMetadata = Chain.OperationSetMetadata.newBuilder();
        operationSetMetadata.setKey(setMetadata.getKey());
        operationSetMetadata.setValue(setMetadata.getValue());
        if (setMetadata.getVersion() != 0) {
            operationSetMetadata.setVersion(setMetadata.getVersion() + 1);
        }
        operation.setSetMetadata(operationSetMetadata);
    }

    public static class Builder extends BaseBuilder<SetMetadataOperation, Builder>{

        @Override
        protected SetMetadataOperation newOperation(){
            return new SetMetadataOperation();
        }

        public Builder buildMetadata(SetMetadata setMetadata) throws SdkException{
            return buildTemplate(() -> operation.setMetadata = setMetadata);
        }

        public Builder buildMetadata(String key, String value) throws SdkException{
            return buildTemplate(() -> operation.setMetadata = new SetMetadata(key, value));
        }

        public Builder buildMetadata(String key, String value, long version) throws SdkException{
            return buildTemplate(() -> operation.setMetadata = new SetMetadata(key, value, version));
        }

        @Override
        public void checkPass() throws SdkException{
            Assert.notTrue(operation.setMetadata == null, SdkError.OPERATION_ERROR_SET_METADATA_EMPTY);
        }

    }

	public SetMetadata getSetMetadata() {
		return setMetadata;
	}

    
}
