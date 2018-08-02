package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.data.ContractInfo;

/**
 * @Author riven
 * @Date 2018/7/5 14:09
 */
public class ContractGetInfoResult {
    @JSONField(name = "contract")
    private ContractInfo contract;

    /**
     * @Author riven
     * @Method getContractService
     * @Params []
     * @Return io.bumo.model.response.result.data.ContractInfo
     * @Date 2018/7/5 14:10
     */
    public ContractInfo getContract() {
        return contract;
    }

    /**
     * @Author riven
     * @Method setContract
     * @Params [contract]
     * @Return void
     * @Date 2018/7/5 14:11
     */
    public void setContract(ContractInfo contract) {
        this.contract = contract;
    }
}
