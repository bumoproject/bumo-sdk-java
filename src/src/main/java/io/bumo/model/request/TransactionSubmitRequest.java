package io.bumo.model.request;

import io.bumo.model.response.result.data.Signature;

/**
 * @Author riven
 * @Date 2018/7/5 16:11
 */
public class TransactionSubmitRequest {
    private String transactionBlob;
    private Signature[] signatures;

    /**
     * @Author riven
     * @Method getTransactionBlob
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 16:12
     */
    public String getTransactionBlob() {
        return transactionBlob;
    }

    /**
     * @Author riven
     * @Method setTransactionBlob
     * @Params [transactionBlob]
     * @Return void
     * @Date 2018/7/5 16:12
     */
    public void setTransactionBlob(String transactionBlob) {
        this.transactionBlob = transactionBlob;
    }

    /**
     * @Author riven
     * @Method getSignatures
     * @Params []
     * @Return io.bumo.model.response.result.data.Signature[]
     * @Date 2018/7/5 16:12
     */
    public Signature[] getSignatures() {
        return signatures;
    }

    /**
     * @Author riven
     * @Method setSignatures
     * @Params [signatures]
     * @Return void
     * @Date 2018/7/5 16:12
     */
    public void setSignatures(Signature[] signatures) {
        this.signatures = signatures;
    }

    public void addSignatures(Signature[] signatures) {
        this.signatures = signatures;
    }
}
