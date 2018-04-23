package io.bumo.sdk.core.pool;

import java.util.List;

import io.bumo.sdk.core.exception.SdkException;
import io.bumo.sdk.core.operation.BcOperation;
import io.bumo.sdk.core.operation.impl.CreateAccountOperation;
import io.bumo.sdk.core.transaction.model.Signature;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 * Provide the ability to operate the account when the account pool is built
 */
public interface SponsorAccountConfig{
    /**
     * The account pool calls this method to get the account creation operation
     *
     * @param address     
     * @param publicKey   
     * @param privateKey  
     * @param accountMark 
     */
    CreateAccountOperation createAccountOperation(String address, String publicKey, String privateKey, String accountMark) throws SdkException;

    /**
     * Add other operations to an account
     *
     * @param address Account pool address
     */
    List<BcOperation> provideBcOperations(String address) throws SdkException;

    /**
     * If there are other operations, additional signature information provided here
     */
    List<Signature> provideSignature() throws SdkException;

}
