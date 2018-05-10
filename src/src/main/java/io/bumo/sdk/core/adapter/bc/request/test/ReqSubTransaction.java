package io.bumo.sdk.core.adapter.bc.request.test;



import io.bumo.sdk.core.adapter.bc.response.SubTransaction;
/***
 * 
 * @author bumo
 * @since 2018/3/16 P.M.2:09.
 *
 */
public class ReqSubTransaction extends SubTransaction {
	private long fee;

	public long getFee() {
		return fee;
	}

	public void setFee(long fee) {
		this.fee = fee;
	}
	
}
