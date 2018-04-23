package io.bumo.sdk.core.operation.test;

import io.bumo.sdk.core.operation.impl.CreateAccountOperation;
import io.bumo.sdk.core.operation.impl.PayCoinOperation;

/**
 * @author bumo
 * @since 18/03/16 10:02 a.m.
 * 
 */
@Deprecated
public class BcOperationBuilder {
	/**
	 * 1. Create an account
	 * @return
	 */
	public static CreateAccountOperation.Builder newCreateAccountBuilder() {
		return new CreateAccountOperation.Builder();
	}
	/***
	 * 7. The operation of payment of BU currency
	 * @return
	 */
    public static PayCoinOperation.Builder newPayCoinBuilder() {
    	return new PayCoinOperation.Builder();
    }

}
