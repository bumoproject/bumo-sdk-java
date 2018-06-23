package io.bumo.model.request;

/**
 * @Author riven
 * @Date 2018/7/7 06:58
 */
public class ContractCallRequest {
    private String sourceAddress;
    private String contractAddress;
    private String code;
    private String input;
    private Long contractBalance;
    private Long feeLimit;
    private Long gasPrice;

    /**
     * @Author riven
     * @Method getSourceAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/7 07:00
     */
    public String getSourceAddress() {
        return sourceAddress;
    }

    /**
     * @Author riven
     * @Method setSourceAddress
     * @Params [sourceAddress]
     * @Return void
     * @Date 2018/7/7 07:00
     */
    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    /**
     * @Author riven
     * @Method getContractAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/7 07:00
     */
    public String getContractAddress() {
        return contractAddress;
    }

    /**
     * @Author riven
     * @Method setContractAddress
     * @Params [contractAddress]
     * @Return void
     * @Date 2018/7/7 07:00
     */
    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    /**
     * @Author riven
     * @Method getCode
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/7 07:00
     */
    public String getCode() {
        return code;
    }

    /**
     * @Author riven
     * @Method setCode
     * @Params [code]
     * @Return void
     * @Date 2018/7/7 07:00
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @Author riven
     * @Method getInput
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/7 07:00
     */
    public String getInput() {
        return input;
    }

    /**
     * @Author riven
     * @Method setInput
     * @Params [input]
     * @Return void
     * @Date 2018/7/7 07:00
     */
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * @Author riven
     * @Method getContractBalance
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/7 07:09
     */
    public Long getContractBalance() {
        return contractBalance;
    }

    /**
     * @Author riven
     * @Method setContractBalance
     * @Params [contractBalance]
     * @Return void
     * @Date 2018/7/7 07:09
     */
    public void setContractBalance(Long contractBalance) {
        this.contractBalance = contractBalance;
    }

    /**
     * @Author riven
     * @Method getFeeLimit
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/7 07:09
     */
    public Long getFeeLimit() {
        return feeLimit;
    }

    /**
     * @Author riven
     * @Method setFeeLimit
     * @Params [feeLimit]
     * @Return void
     * @Date 2018/7/7 07:10
     */
    public void setFeeLimit(Long feeLimit) {
        this.feeLimit = feeLimit;
    }

    /**
     * @Author riven
     * @Method getGasPrice
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/7 07:11
     */
    public Long getGasPrice() {
        return gasPrice;
    }

    /**
     * @Author riven
     * @Method setGasPrice
     * @Params [gasPrice]
     * @Return void
     * @Date 2018/7/7 07:11
     */
    public void setGasPrice(Long gasPrice) {
        this.gasPrice = gasPrice;
    }
}
