package io.bumo.sdk.core.operation.impl;

import java.util.List;

import io.bumo.sdk.core.adapter.bc.OperationType;
import io.bumo.sdk.core.adapter.bc.response.Priv;
import io.bumo.sdk.core.adapter.bc.response.Signer;
import io.bumo.sdk.core.adapter.bc.response.Threshold;
import io.bumo.sdk.core.adapter.bc.response.TypeThreshold;
import io.bumo.sdk.core.adapter.bc.response.operation.CreateAccount;
import io.bumo.sdk.core.adapter.bc.response.operation.SetMetadata;
import io.bumo.sdk.core.config.SDKConfig;
import io.bumo.sdk.core.exception.SdkError;
import io.bumo.sdk.core.exception.SdkException;
import io.bumo.sdk.core.extend.protobuf.Chain;
import io.bumo.sdk.core.extend.protobuf.Common;
import io.bumo.sdk.core.operation.AbstractBcOperation;
import io.bumo.sdk.core.operation.builder.BaseBuilder;
import io.bumo.sdk.core.utils.Assert;
import io.bumo.sdk.core.utils.spring.StringUtils;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public class CreateAccountOperation extends AbstractBcOperation{

    private CreateAccount createAccount = new CreateAccount();

    private CreateAccountOperation(){
        super(OperationType.CREATE_ACCOUNT.intValue());
    }

    @Override
    protected void buildOperationContinue(Chain.Operation.Builder operation){
        Chain.OperationCreateAccount.Builder operationCreateAccount = Chain.OperationCreateAccount.newBuilder();
        operationCreateAccount.setDestAddress(createAccount.getDestAddress());
        operationCreateAccount.setInitBalance(createAccount.getInitBalance());
        if(createAccount.getInitInput() != null){
            operationCreateAccount.setInitInput(createAccount.getInitInput());
        }
        // Meta Data
        List<SetMetadata> metadatas = createAccount.getMetadatas();
        if (metadatas != null && metadatas.size() > 0) {
            metadatas.forEach(m -> {
                Common.KeyPair.Builder keyPair = Common.KeyPair.newBuilder();
                keyPair.setKey(m.getKey());
                keyPair.setValue(m.getValue());
                keyPair.setVersion(m.getVersion());	
                operationCreateAccount.addMetadatas(keyPair);
            });
        }

        // right
        Priv priv = createAccount.getPriv();
        Chain.AccountPrivilege.Builder accountPrivilegeBuilder = Chain.AccountPrivilege.newBuilder();
        long masterWeight = priv.getMasterWeight();
        if (masterWeight == 0) {
        	masterWeight = 1;
        }
        accountPrivilegeBuilder.setMasterWeight(masterWeight);
        // signer
        if (priv.getSigners() != null) {
            priv.getSigners().forEach(privileger -> {
                Chain.Signer.Builder sign = Chain.Signer.newBuilder();
                sign.setAddress(privileger.getAddress());
                sign.setWeight(privileger.getWeight());
                accountPrivilegeBuilder.addSigners(sign);
            });
        }
        // threshold
        Threshold threshold = priv.getThreshold();
        if (threshold != null) {
            Chain.AccountThreshold.Builder accountThresholdBuilder = Chain.AccountThreshold.newBuilder();
            long txThreshold = threshold.getTxThreshold();
            if (txThreshold == 0) {
            	txThreshold = 1;
            }
            accountThresholdBuilder.setTxThreshold(txThreshold);

            // type_thresholds
            Chain.OperationTypeThreshold.Builder typeThresholdBuilder = Chain.OperationTypeThreshold.newBuilder();
            List<TypeThreshold> typeThresholds = threshold.getTypeThresholds();
            if (typeThresholds != null && typeThresholds.size() > 0) {
                typeThresholds.forEach(typeThreshold -> {
                    typeThresholdBuilder.setType(Chain.Operation.Type.forNumber(Integer.valueOf("" + typeThreshold.getType())));
                    typeThresholdBuilder.setThreshold(typeThreshold.getThreshold());
                    accountThresholdBuilder.addTypeThresholds(typeThresholdBuilder);
                });
            }

            accountPrivilegeBuilder.setThresholds(accountThresholdBuilder);
        }

        operationCreateAccount.setPriv(accountPrivilegeBuilder);

        // Contract
        String script = createAccount.getContract().getPayload();
        if (!StringUtils.isEmpty(script)) {
            Chain.Contract.Builder contractBuilder = Chain.Contract.newBuilder();
            contractBuilder.setPayload(script);
            operationCreateAccount.setContract(contractBuilder);
        }
        operation.setCreateAccount(operationCreateAccount);
    }


    public static class Builder extends BaseBuilder<CreateAccountOperation, Builder>{

        private CreateAccount createAccount;

        @Override
        protected CreateAccountOperation newOperation(){
            CreateAccountOperation createAccountOperation = new CreateAccountOperation();
            this.createAccount = createAccountOperation.createAccount;
            return createAccountOperation;
        }

        public Builder buildDestAddress(String destAddress) throws SdkException{
            return buildTemplate(() -> createAccount.setDestAddress(destAddress));
        }

        public Builder buildScript(String script) throws SdkException{
            return buildTemplate(() -> createAccount.getContract().setPayload(script));
        }

        public Builder buildAddMetadata(String key, String value) throws SdkException{
            return buildTemplate(() -> createAccount.getMetadatas().add(new SetMetadata(key, value)));
        }
        
        public Builder buildAddInitInput(String initInput) throws SdkException{
            return buildTemplate(() -> createAccount.setInitInput(initInput));
        }
        
        
        public Builder buildAddInitBalance(long initBalance) throws SdkException{
            return buildTemplate(() -> createAccount.setInitBalance(initBalance));
        }

        public Builder buildPriMasterWeight(long masterWeight) throws SdkException{
            return buildTemplate(() -> createAccount.getPriv().setMasterWeight(masterWeight));
        }

        public Builder buildAddPriSigner(String address, long weight) throws SdkException{
            return buildTemplate(() -> createAccount.getPriv().getSigners().add(new Signer(address, weight)));
        }

        public Builder buildPriTxThreshold(long txThreshold) throws SdkException{
            return buildTemplate(() -> createAccount.getPriv().getThreshold().setTxThreshold(txThreshold));
        }

        public Builder buildAddPriTypeThreshold(OperationType operationTypeV3, long threshold) throws SdkException{
            return buildTemplate(() -> createAccount.getPriv().getThreshold().getTypeThresholds().add(new TypeThreshold(operationTypeV3.intValue(), threshold)));
        }

        @Override
        public void checkPass() throws SdkException{
            Assert.notEmpty(operation.createAccount.getDestAddress(), SdkError.OPERATION_ERROR_NOT_DESC_ADDRESS);
            if(SDKConfig.initBalanceEnable){
                Assert.notTrue(operation.createAccount.getInitBalance() <= 0, SdkError.OPERATION_ERROR_INITBALANCE_ILLEGAL);
            }
            Assert.notTrue(operation.createAccount.checkMetadata(),SdkError.OPERATION_ERROR_NOT_METADATA);
        }

		
        
        
    }
    public CreateAccount getCreateAccount() {
		return createAccount;
	}
}
