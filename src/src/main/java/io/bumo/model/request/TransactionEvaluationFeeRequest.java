package io.bumo.model.request;

import io.bumo.model.request.Operation.BaseOperation;

import java.util.Arrays;

/**
 * @Author riven
 * @Date 2018/7/5 15:53
 */
public class TransactionEvaluationFeeRequest {
    private String sourceAddress;
    private Long nonce;
    private BaseOperation[] operations;
    private int signatureNumber;
    private Long ceilLedgerSeq;
    private String metadata;

    /**
     * @Author riven
     * @Method getSourceAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 15:54
     */
    public String getSourceAddress() {
        return sourceAddress;
    }

    /**
     * @Author riven
     * @Method setSourceAddress
     * @Params [sourceAddress]
     * @Return void
     * @Date 2018/7/5 15:54
     */
    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    /**
     * @Author riven
     * @Method getNonce
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/5 15:54
     */
    public Long getNonce() {
        return nonce;
    }

    /**
     * @Author riven
     * @Method setNonce
     * @Params [nonce]
     * @Return void
     * @Date 2018/7/5 15:54
     */
    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }

    /**
     * @Author riven
     * @Method getOperations
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 15:49
     */
    public BaseOperation[] getOperations() {
        return operations;
    }

    /**
     * @Author riven
     * @Method setOperations
     * @Params [operations]
     * @Return void
     * @Date 2018/7/5 15:49
     */
    public void setOperation(BaseOperation operation) {
        if (null == this.operations) {
            this.operations = new BaseOperation[1];
        } else {
            operations = Arrays.copyOf(operations, 1);
        }
        this.operations[0] = operation;
    }

    /**
     * @Author riven
     * @Method addOperation
     * @Params [operation]
     * @Return void
     * @Date 2018/7/10 11:39
     */
    public void addOperation(BaseOperation operation) {
        if (null == operations) {
            operations = new BaseOperation[1];
        } else {
            operations = Arrays.copyOf(operations, operations.length + 1);
        }
        operations[operations.length - 1] = operation;
    }
    
    /**
     * @Author riven
     * @Method getSignatureNumber
     * @Params []
     * @Return int
     * @Date 2018/7/5 15:54
     */
    public int getSignatureNumber() {
        return signatureNumber;
    }

    /**
     * @Author riven
     * @Method setSignatureNumber
     * @Params [signatureNumber]
     * @Return void
     * @Date 2018/7/5 15:54
     */
    public void setSignatureNumber(int signatureNumber) {
        this.signatureNumber = signatureNumber;
    }

    /**
     * @Author riven
     * @Method getCeilLedgerSeq
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 19:20
     */
    public Long getCeilLedgerSeq() {
        return ceilLedgerSeq;
    }

    /**
     * @Author riven
     * @Method setCeilLedgerSeq
     * @Params [ceilLedgerSeq]
     * @Return void
     * @Date 2018/7/5 19:20
     */
    public void setCeilLedgerSeq(Long ceilLedgerSeq) {
        this.ceilLedgerSeq = ceilLedgerSeq;
    }

    /**
     * @Author riven
     * @Method getMetadata
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 15:54
     */
    public String getMetadata() {
        return metadata;
    }

    /**
     * @Author riven
     * @Method setMetadata
     * @Params [metadata]
     * @Return void
     * @Date 2018/7/5 15:55
     */
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
