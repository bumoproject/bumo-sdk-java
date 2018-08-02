package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/8/1 15:35
 */
public class ContractAddressInfo {
    @JSONField(name = "contract_address")
    private String contractAddress;

    @JSONField(name = "operation_index")
    private Integer operationIndex;

    /**
     * @Author riven
     * @Method getContractAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/8/1 15:05
     */
    public String getContractAddress() {
        return contractAddress;
    }

    /**
     * @Author riven
     * @Method setContractAddress
     * @Params [contractAddress]
     * @Return void
     * @Date 2018/8/1 15:05
     */
    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    /**
     * @Author riven
     * @Method getOperationIndex
     * @Params []
     * @Return java.lang.Integer
     * @Date 2018/8/1 15:36
     */
    public Integer getOperationIndex() {
        return operationIndex;
    }

    /**
     * @Author riven
     * @Method setOperationIndex
     * @Params [operationIndex]
     * @Return void
     * @Date 2018/8/1 15:36
     */
    public void setOperationIndex(Integer operationIndex) {
        this.operationIndex = operationIndex;
    }
}
