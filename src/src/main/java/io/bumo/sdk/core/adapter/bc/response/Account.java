package io.bumo.sdk.core.adapter.bc.response;

import com.alibaba.fastjson.annotation.JSONField;

import io.bumo.sdk.core.adapter.bc.response.contract.Contract;
import io.bumo.sdk.core.adapter.bc.response.operation.SetMetadata;

/**
 * 账号信息
 *
 * @author bumo
 */
public class Account{
    private String address;
    private Asset[] assets;

    private Long balance;

    /**
     * #3.0
     */
    @JSONField(name = "assets_hash")
    private String assetsHash;
    /**
     * #3.0 合约
     */
    private Contract contract;
    /**
     * #3.0
     */
    @JSONField(name = "storage_hash")
    private String storageHash;
    /**
     * #3.0  元数据
     */
    private SetMetadata[] metadatas;
    /**
     * #3.0 操作序列号
     */
    private long nonce;
    /**
     * #3.0 权限
     */
    private Priv priv;

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public Asset[] getAssets(){
        return assets;
    }

    public void setAssets(Asset[] assets){
        this.assets = assets;
    }

    public SetMetadata[] getMetadatas(){
        return metadatas;
    }

    public void setMetadatas(SetMetadata[] metadatas){
        this.metadatas = metadatas;
    }

    public String getAssetsHash(){
        return assetsHash;
    }

    public void setAssetsHash(String assetsHash){
        this.assetsHash = assetsHash;
    }

    public String getStorageHash(){
        return storageHash;
    }

    public void setStorageHash(String storageHash){
        this.storageHash = storageHash;
    }

    public Contract getContract(){
        return contract;
    }

    public void setContract(Contract contract){
        this.contract = contract;
    }

    public long getNonce(){
        return nonce;
    }

    public void setNonce(long nonce){
        this.nonce = nonce;
    }

    public Priv getPriv(){
        return priv;
    }

    public void setPriv(Priv priv){
        this.priv = priv;
    }

    public Long getBalance(){
        return balance;
    }

    public void setBalance(Long balance){
        this.balance = balance;
    }
}
