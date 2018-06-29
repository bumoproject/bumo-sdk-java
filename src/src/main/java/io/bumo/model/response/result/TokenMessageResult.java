package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.data.ContractInfo;
import io.bumo.model.response.result.data.MetadataInfo;

/**
 * @Author riven
 * @Date 2018/7/15 15:51
 */
public class TokenMessageResult {
    @JSONField(name = "metadatas")
    private MetadataInfo[] metadatas;

    @JSONField(name = "contract")
    private ContractInfo contract;

    /**
     * @Author riven
     * @Method getMetadatas
     * @Params []
     * @Return io.bumo.model.response.result.data.MetadataInfo[]
     * @Date 2018/7/15 15:52
     */
    public MetadataInfo[] getMetadatas() {
        return metadatas;
    }

    /**
     * @Author riven
     * @Method setMetadatas
     * @Params [metadatas]
     * @Return void
     * @Date 2018/7/15 15:52
     */
    public void setMetadatas(MetadataInfo[] metadatas) {
        this.metadatas = metadatas;
    }

    /**
     * @Author riven
     * @Method getContract
     * @Params []
     * @Return io.bumo.model.response.result.data.ContractInfo
     * @Date 2018/7/15 15:52
     */
    public ContractInfo getContract() {
        return contract;
    }

    /**
     * @Author riven
     * @Method setContract
     * @Params [contract]
     * @Return void
     * @Date 2018/7/16 12:02
     */
    public void setContract(ContractInfo contract) {
        this.contract = contract;
    }
}
