package org.bumo.sdk.core.operation;

import org.bumo.sdk.core.exception.SdkException;
import org.bumo.sdk.core.extend.protobuf.Chain;
import org.bumo.sdk.core.utils.SwallowUtil;
import org.bumo.sdk.core.utils.spring.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public abstract class AbstractBcOperation implements BcOperation{

    private int type;
    private String operationSourceAddress;
    private String operationMetadata;

    protected AbstractBcOperation(int type){
        this.type = type;
    }

    @SuppressWarnings("deprecation")
	@Override
    public void buildTransaction(Chain.Transaction.Builder builder, long maxSeq) throws SdkException{
        Chain.Operation.Builder operation = builder.addOperationsBuilder();
        operation.setType(Chain.Operation.Type.valueOf(type));
        if (!StringUtils.isEmpty(operationSourceAddress))
            operation.setSourceAddress(operationSourceAddress);
        if (!StringUtils.isEmpty(operationMetadata))
            operation.setMetadata(ByteString.copyFrom(SwallowUtil.getBytes(operationMetadata)));
        buildOperation(operation);
    }

    private void buildOperation(Chain.Operation.Builder operation) throws SdkException{
        buildOperationContinue(operation);
    }

    @Override
    public BcOperation generateOperation(JSONObject originJson){
        // todo Reverse generating operation objects, there is no need now, and it will not be realized temporarily
        return null;
    }

    /**
     * Subclass continuation build
     */
    protected abstract void buildOperationContinue(Chain.Operation.Builder operation);


    public String getOperationSourceAddress(){
        return operationSourceAddress;
    }

    public void setOperationSourceAddress(String operationSourceAddress){
        this.operationSourceAddress = operationSourceAddress;
    }

    public String getOperationMetadata(){
        return operationMetadata;
    }

    public void setOperationMetadata(String operationMetadata){
        this.operationMetadata = operationMetadata;
    }


}
