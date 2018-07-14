package io.bumo.model.request;

/**
 * @Author riven
 * @Date 2018/7/6 16:31
 */
public class Ctp10TokenGetSymbolRequest {
    private String contractAddress;

    /**
     * @Author riven
     * @Method getContractAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/6 16:31
     */
    public String getContractAddress() {
        return contractAddress;
    }

    /**
     * @Author riven
     * @Method setContractAddress
     * @Params [contractAddress]
     * @Return void
     * @Date 2018/7/6 16:31
     */
    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }
}
