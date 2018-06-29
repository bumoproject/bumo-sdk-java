package io.bumo.model.request;

/**
 * @Author riven
 * @Date 2018/7/15 15:28
 */
public class TokenCheckValidRequest {
    private String contractAddress;

    /**
     * @Author riven
     * @Method getContractAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/15 15:30
     */
    public String getContractAddress() {
        return contractAddress;
    }

    /**
     * @Author riven
     * @Method setContractAddress
     * @Params [contractAddress]
     * @Return void
     * @Date 2018/7/15 15:35
     */
    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }
}
