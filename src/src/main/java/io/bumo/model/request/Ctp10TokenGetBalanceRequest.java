package io.bumo.model.request;

/**
 * @Author riven
 * @Date 2018/7/6 16:33
 */
public class Ctp10TokenGetBalanceRequest {
    private String contractAddress;
    private String tokenOwner;

    /**
     * @Author riven
     * @Method getContractAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/6 16:34
     */
    public String getContractAddress() {
        return contractAddress;
    }

    /**
     * @Author riven
     * @Method setContractAddress
     * @Params [contractAddress]
     * @Return void
     * @Date 2018/7/6 16:34
     */
    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    /**
     * @Author riven
     * @Method getTokenOwner
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/6 16:34
     */
    public String getTokenOwner() {
        return tokenOwner;
    }

    /**
     * @Author riven
     * @Method setTokenOwner
     * @Params [tokenOwner]
     * @Return void
     * @Date 2018/7/6 16:34
     */
    public void setTokenOwner(String tokenOwner) {
        this.tokenOwner = tokenOwner;
    }
}
