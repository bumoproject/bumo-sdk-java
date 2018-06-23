package io.bumo.model.request.Operation;

import io.bumo.common.OperationType;

/**
 * @Author riven
 * @Date 2018/7/9 17:20
 */
public class ContractInvokeByBUOperation extends BaseOperation {
    private OperationType operationType = OperationType.CONTRACT_INVOKE_BY_BU;
    private String contractAddress;
    private Long amount;
    private String input;

    /**
     * @Author riven
     * @Method getOperationType
     * @Params []
     * @Return io.bumo.common.OperationType
     * @Date 2018/7/9 17:21
     */
    public OperationType getOperationType() {
        return operationType;
    }

    /**
     * @Author riven
     * @Method getContractAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 17:21
     */
    public String getContractAddress() {
        return contractAddress;
    }

    /**
     * @Author riven
     * @Method setContractAddress
     * @Params [contractAddress]
     * @Return void
     * @Date 2018/7/9 17:21
     */
    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    /**
     * @Author riven
     * @Method getAmount
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/9 17:21
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * @Author riven
     * @Method setAmount
     * @Params [amount]
     * @Return void
     * @Date 2018/7/9 17:21
     */
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    /**
     * @Author riven
     * @Method getInput
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 17:21
     */
    public String getInput() {
        return input;
    }

    /**
     * @Author riven
     * @Method setInput
     * @Params [input]
     * @Return void
     * @Date 2018/7/9 17:29
     */
    public void setInput(String input) {
        this.input = input;
    }
}
