package io.bumo.model.request.operation;

import io.bumo.common.OperationType;

/**
 * @Author riven
 * @Date 2018/8/6 20:32
 */
public class Atp10TokenAppendToIssueOperation extends Atp10TokenBaseOperation {
    private Long appendSupply;

    public Atp10TokenAppendToIssueOperation() {
        operationType = OperationType.ATP10TOKEN_APPEND_TO_TOKEN;
    }

    /**
     * @Author riven
     * @Method getAppendSupply
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/8/6 20:34
     */
    public Long getAppendSupply() {
        return appendSupply;
    }

    /**
     * @Author riven
     * @Method setAppendSupply
     * @Params [appendSupply]
     * @Return void
     * @Date 2018/8/6 20:34
     */
    public void setAppendSupply(Long appendSupply) {
        this.appendSupply = appendSupply;
    }
}
