package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/5 15:49
 */
public class TransactionBuildBlobResult {
    @JSONField(name = "transaction_blob")
    private String transactionBlob;

    @JSONField(name = "hash")
    private String hash;

    /**
     * @Author riven
     * @Method getTransactionBlob
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 15:50
     */
    public String getTransactionBlob() {
        return transactionBlob;
    }

    /**
     * @Author riven
     * @Method setTransactionBlob
     * @Params [transactionBlob]
     * @Return void
     * @Date 2018/7/5 15:50
     */
    public void setTransactionBlob(String transactionBlob) {
        this.transactionBlob = transactionBlob;
    }

    /**
     * @Author riven
     * @Method getHash
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/10 13:08
     */
    public String getHash() {
        return hash;
    }

    /**
     * @Author riven
     * @Method setHash
     * @Params [hash]
     * @Return void
     * @Date 2018/7/10 13:08
     */
    public void setHash(String hash) {
        this.hash = hash;
    }
}
