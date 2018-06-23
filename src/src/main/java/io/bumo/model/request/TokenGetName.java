package io.bumo.model.request;

/**
 * @Author riven
 * @Date 2018/7/6 13:40
 */
public class TokenGetName {
    private String contractName;

    /**
     * @Author riven
     * @Method getContractName
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/6 13:41
     */
    public String getContractName() {
        return contractName;
    }

    /**
     * @Author riven
     * @Method setContractName
     * @Params [contractName]
     * @Return void
     * @Date 2018/7/6 13:41
     */
    public void setContractName(String contractName) {
        this.contractName = contractName;
    }
}
