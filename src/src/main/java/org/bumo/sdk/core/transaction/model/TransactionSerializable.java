package org.bumo.sdk.core.transaction.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public class TransactionSerializable implements Serializable{

    private static final long serialVersionUID = 5370283195105108177L;

    private TransactionBlob transactionBlob;
    private List<Signature> signatures;
    private Long fee;
    private long feeLimit;
    private long gasPrice;

    public TransactionSerializable(){
    }

    public TransactionSerializable(TransactionBlob transactionBlob, List<Signature> signatures, long fee){
        this.transactionBlob = transactionBlob;
        this.signatures = signatures;
        this.fee = fee;

    }
    
    
    public TransactionSerializable(TransactionBlob transactionBlob, List<Signature> signatures, long feeLimit, long gasPrice){
        this.transactionBlob = transactionBlob;
        this.signatures = signatures;
        this.feeLimit = feeLimit;
        this.gasPrice = gasPrice;

    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public TransactionBlob getTransactionBlob(){
        return transactionBlob;
    }

    public void setTransactionBlob(TransactionBlob transactionBlob){
        this.transactionBlob = transactionBlob;
    }

    public List<Signature> getSignatures(){
        return signatures;
    }

    public void setSignatures(List<Signature> signatures){
        this.signatures = signatures;
    }

	public long getFeeLimit() {
		return feeLimit;
	}

	public void setFeeLimit(long feeLimit) {
		this.feeLimit = feeLimit;
	}

	public long getGasPrice() {
		return gasPrice;
	}

	public void setGasPrice(long gasPrice) {
		this.gasPrice = gasPrice;
	}
    
    
}
