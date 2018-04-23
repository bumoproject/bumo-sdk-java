package io.bumo.sdk.core.operation.builder;

import io.bumo.sdk.core.exception.SdkError;
import io.bumo.sdk.core.exception.SdkException;
import io.bumo.sdk.core.operation.AbstractBcOperation;
import io.bumo.sdk.core.operation.BuildConsume;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 * Move the creation part of the operation to builder
 */
@SuppressWarnings("rawtypes")
public abstract class BaseBuilder<T extends AbstractBcOperation, R extends BaseBuilder>{

    protected T operation;
    private String operationSourceAddress;
    private String operationMetadata;
    private boolean complete = false;

    protected BaseBuilder(){
        this.operation = newOperation();
    }

    /**
     * Generating actual operating objects
     */
    protected abstract T newOperation();

    @SuppressWarnings("unchecked")
    protected R buildTemplate(BuildConsume buildConsume) throws SdkException{
        checkOperationCanExecute();
        buildConsume.build();
        return (R) this;
    }

    private void checkOperationCanExecute() throws SdkException{
        if (complete) throw new SdkException(SdkError.OPERATION_ERROR_STATUS);
    }

    /**
     * Construction operation sourceAddress
     */
    public R buildOperationSourceAddress(String operationSourceAddress) throws SdkException{
        return buildTemplate(() -> this.operationSourceAddress = operationSourceAddress);
    }

    /**
     * Construction operation metadata
     */
    public R buildOperationMetadata(String operationMetadata) throws SdkException{
        return buildTemplate(() -> this.operationMetadata = operationMetadata);
    }

    /**
     * Generating operation object
     */
    public T build() throws SdkException{
        complete();
        operation.setOperationSourceAddress(operationSourceAddress);
        operation.setOperationMetadata(operationMetadata);
        return operation;
    }

    private void complete() throws SdkException{
        if (!complete) {
            checkPass();
            complete = true;
        }
    }

    /**
     * Operation parameter check
     */
    public abstract void checkPass() throws SdkException;

}
