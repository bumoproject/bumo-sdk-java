package io.bumo.model.request;

/**
 * @Author riven
 * @Date 2018/7/5 15:28
 */
public class ContractCheckValidRequest {
    private String contractAddress;

    /**
     * @Author riven
     * @Method getContractAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 15:28
     */
    public String getContractAddress() {
        return contractAddress;
    }

    /**
     * @Author riven
     * @Method setContractAddress
     * @Params [contractAddress]
     * @Return void
     * @Date 2018/7/5 15:28
     */
    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }
}
