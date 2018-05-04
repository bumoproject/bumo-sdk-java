package io.bumo.sdk.core.adapter.bc.response;

import com.alibaba.fastjson.annotation.JSONField;

import io.bumo.sdk.core.adapter.bc.response.operation.*;

/**
 * 操作信息
 *
 * @author 布萌
 */
public class Operation{
    @JSONField(name = "create_account")
    private CreateAccount createAccount;
    @JSONField(name = "invoke_contract")
    private InvokeContract invokeContract;
    @JSONField(name = "issue_asset")
    private IssueAsset issueAsset;
    @JSONField(name = "pay_asset")
    private PayAsset payAsset;
    //TODO
    @JSONField(name = "pay_coin")
    private PayCoin payCoin;
    @JSONField(name = "set_metadata")
    private SetMetadata setMetadata;
    @JSONField(name = "set_signer_weight")
    private SetSignerWeight setSignerWeight;
    @JSONField(name = "set_threshold")
    private SetThreshold setThreshold;


    private String metadata;
    @JSONField(name = "source_address")
    private String sourceAddress;
    private int type;

    public CreateAccount getCreateAccount(){
        return createAccount;
    }

    public void setCreateAccount(CreateAccount createAccount){
        this.createAccount = createAccount;
    }

    public InvokeContract getInvokeContract(){
        return invokeContract;
    }

    public void setInvokeContract(InvokeContract invokeContract){
        this.invokeContract = invokeContract;
    }

    public IssueAsset getIssueAsset(){
        return issueAsset;
    }

    public void setIssueAsset(IssueAsset issueAsset){
        this.issueAsset = issueAsset;
    }

    public PayAsset getPayAsset(){
        return payAsset;
    }

    public void setPayAsset(PayAsset payAsset){
        this.payAsset = payAsset;
    }

    public SetMetadata getSetMetadata(){
        return setMetadata;
    }

    public void setSetMetadata(SetMetadata setMetadata){
        this.setMetadata = setMetadata;
    }

    public SetSignerWeight getSetSignerWeight(){
        return setSignerWeight;
    }

    public void setSetSignerWeight(SetSignerWeight setSignerWeight){
        this.setSignerWeight = setSignerWeight;
    }

    public SetThreshold getSetThreshold(){
        return setThreshold;
    }

    public void setSetThreshold(SetThreshold setThreshold){
        this.setThreshold = setThreshold;
    }

    public String getMetadata(){
        return metadata;
    }

    public void setMetadata(String metadata){
        this.metadata = metadata;
    }

    public String getSourceAddress(){
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress){
        this.sourceAddress = sourceAddress;
    }

    public int getType(){
        return type;
    }

    public void setType(int type){
        this.type = type;
    }

	public PayCoin getPayCoin() {
		return payCoin;
	}

	public void setPayCoin(PayCoin payCoin) {
		this.payCoin = payCoin;
	}

}
