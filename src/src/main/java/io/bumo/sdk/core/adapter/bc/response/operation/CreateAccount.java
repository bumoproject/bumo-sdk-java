package io.bumo.sdk.core.adapter.bc.response.operation;

import com.alibaba.fastjson.annotation.JSONField;

import io.bumo.sdk.core.adapter.bc.response.Priv;
import io.bumo.sdk.core.adapter.bc.response.contract.Contract;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建账号
 */
public class CreateAccount{
	
    private String metadata;
    @JSONField(name = "dest_address")
    private String destAddress;
    @JSONField(name = "init_balance")
    private long initBalance;
    @JSONField(name = "init_input")
    private String initInput;

    private Contract contract = new Contract();// 合约
    private List<SetMetadata> metadatas = new ArrayList<>();// metadata
    private Priv priv = new Priv();// 权限


    public Contract getContract(){
        return contract;
    }

    public void setContract(Contract contract){
        this.contract = contract;
    }

    public List<SetMetadata> getMetadatas(){
        return metadatas;
    }

    public void setMetadatas(List<SetMetadata> metadatas){
        this.metadatas = metadatas;
    }

    public String getMetadata(){
        return metadata;
    }

    public void setMetadata(String metadata){
        this.metadata = metadata;
    }

    public String getDestAddress(){
        return destAddress;
    }

    public void setDestAddress(String destAddress){
        this.destAddress = destAddress;
    }

    public Priv getPriv(){
        return priv;
    }

    public void setPriv(Priv priv){
        this.priv = priv;
    }

	public long getInitBalance() {
		return initBalance;
	}

	public void setInitBalance(long initBalance) {
		this.initBalance = initBalance;
	}

	public String getInitInput() {
		return initInput;
	}

	public void setInitInput(String initInput) {
		this.initInput = initInput;
	}

    /**
     * 验证metadata的key或者value不能为空
     * @return
     */
    public boolean checkMetadata(){
        if(!(this.metadatas.size() == 0 || this.metadatas.isEmpty())){
            return metadatas.stream().anyMatch(md -> md.getKey() == null || md.getValue()==null);
        }
        return false;
    }

}
