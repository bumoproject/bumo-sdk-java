package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/5 16:31
 */
public class AccountActiviateInfo {
    @JSONField(name = "dest_address")
    private String destAddress;

    @JSONField(name = "contract")
    private ContractInfo contract;

    @JSONField(name = "priv")
    private Priv priv;

    @JSONField(name = "metadatas")
    private MetadataInfo[] metadatas;

    @JSONField(name = "init_balance")
    private Long initBalance;

    @JSONField(name = "init_input")
    private String initInput;

    /**
     * @Author riven
     * @Method getDestAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 16:36
     */
    public String getDestAddress() {
        return destAddress;
    }

    /**
     * @Author riven
     * @Method setDestAddress
     * @Params [destAddress]
     * @Return void
     * @Date 2018/7/5 16:36
     */
    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    /**
     * @Author riven
     * @Method getContractService
     * @Params []
     * @Return io.bumo.model.response.result.data.ContractInfo
     * @Date 2018/7/5 16:37
     */
    public ContractInfo getContract() {
        return contract;
    }

    /**
     * @Author riven
     * @Method setContract
     * @Params [contract]
     * @Return void
     * @Date 2018/7/5 16:37
     */
    public void setContract(ContractInfo contract) {
        this.contract = contract;
    }

    /**
     * @Author riven
     * @Method getPriv
     * @Params []
     * @Return io.bumo.model.response.result.data.Priv
     * @Date 2018/7/5 16:37
     */
    public Priv getPriv() {
        return priv;
    }

    /**
     * @Author riven
     * @Method setPriv
     * @Params [priv]
     * @Return void
     * @Date 2018/7/5 16:37
     */
    public void setPriv(Priv priv) {
        this.priv = priv;
    }

    /**
     * @Author riven
     * @Method getMetadatas
     * @Params []
     * @Return io.bumo.model.response.result.data.MetadataInfo[]
     * @Date 2018/7/5 16:37
     */
    public MetadataInfo[] getMetadatas() {
        return metadatas;
    }

    /**
     * @Author riven
     * @Method setMetadatas
     * @Params [metadatas]
     * @Return void
     * @Date 2018/7/5 16:37
     */
    public void setMetadatas(MetadataInfo[] metadatas) {
        this.metadatas = metadatas;
    }

    /**
     * @Author riven
     * @Method getInitBalance
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/5 16:37
     */
    public Long getInitBalance() {
        return initBalance;
    }

    /**
     * @Author riven
     * @Method setInitBalance
     * @Params [initBalance]
     * @Return void
     * @Date 2018/7/5 16:37
     */
    public void setInitBalance(Long initBalance) {
        this.initBalance = initBalance;
    }

    /**
     * @Author riven
     * @Method getInitInput
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 16:37
     */
    public String getInitInput() {
        return initInput;
    }

    /**
     * @Author riven
     * @Method setInitInput
     * @Params [initInput]
     * @Return void
     * @Date 2018/7/5 16:37
     */
    public void setInitInput(String initInput) {
        this.initInput = initInput;
    }
}
