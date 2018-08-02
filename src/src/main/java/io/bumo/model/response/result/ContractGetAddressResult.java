package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.data.ContractAddressInfo;

import java.util.List;

/**
 * @Author riven
 * @Date 2018/8/1 15:04
 */
public class ContractGetAddressResult {
    @JSONField(name = "contract_address_infos")
    private List<ContractAddressInfo> contractAddressInfos;


    /**
     * @Author riven
     * @Method getContractAddressInfos
     * @Params []
     * @Return java.util.List<io.bumo.model.response.result.data.ContractAddressInfo>
     * @Date 2018/8/1 15:56
     */
    public List<ContractAddressInfo> getContractAddressInfos() {
        return contractAddressInfos;
    }

    /**
     * @Author riven
     * @Method setContractAddressInfos
     * @Params [contractAddressInfos]
     * @Return void
     * @Date 2018/8/1 15:57
     */
    public void setContractAddressInfos(List<ContractAddressInfo> contractAddressInfos) {
        this.contractAddressInfos = contractAddressInfos;
    }
}
