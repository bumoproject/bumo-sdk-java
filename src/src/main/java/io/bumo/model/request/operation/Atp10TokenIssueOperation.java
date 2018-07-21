package io.bumo.model.request.operation;

import io.bumo.common.OperationType;
import io.bumo.model.request.other.IssueType;

/**
 * @Author riven
 * @Date 2018/8/2 16:44
 */
public class Atp10TokenIssueOperation extends Atp10TokenBaseOperation {
    private IssueType type;
    private Long supply;
    private Long nowSupply;
    private Integer decimals;
    private String description;

    public Atp10TokenIssueOperation() {
        operationType = OperationType.APT10TOKEN_ISSUE;
    }

    /**
     * @Author riven
     * @Method getType
     * @Params []
     * @Return io.bumo.model.request.operation.Atp10TokenIssueOperation.IssueType
     * @Date 2018/8/6 18:45
     */
    public IssueType getType() {
        return type;
    }

    /**
     * @Author riven
     * @Method setType
     * @Params [type]
     * @Return void
     * @Date 2018/8/6 18:45
     */
    public void setType(IssueType type) {
        this.type = type;
    }

    /**
     * @Author riven
     * @Method getSupply
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/8/6 18:45
     */
    public Long getSupply() {
        return supply;
    }

    /**
     * @Author riven
     * @Method setSupply
     * @Params [supply]
     * @Return void
     * @Date 2018/8/6 18:45
     */
    public void setSupply(Long supply) {
        this.supply = supply;
    }

    /**
     * @Author riven
     * @Method getNowSupply
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/8/7 15:30
     */
    public Long getNowSupply() {
        return nowSupply;
    }

    /**
     * @Author riven
     * @Method setNowSupply
     * @Params [nowSupply]
     * @Return void
     * @Date 2018/8/7 15:31
     */
    public void setNowSupply(Long nowSupply) {
        this.nowSupply = nowSupply;
    }

    /**
     * @Author riven
     * @Method getDecimals
     * @Params []
     * @Return java.lang.Integer
     * @Date 2018/8/6 18:45
     */
    public Integer getDecimals() {
        return decimals;
    }

    /**
     * @Author riven
     * @Method setDecimals
     * @Params [decimals]
     * @Return void
     * @Date 2018/8/6 18:45
     */
    public void setDecimals(Integer decimals) {
        this.decimals = decimals;
    }

    /**
     * @Author riven
     * @Method getDescription
     * @Params []
     * @Return java.lang.String
     * @Date 2018/8/6 18:45
     */
    public String getDescription() {
        return description;
    }

    /**
     * @Author riven
     * @Method setDescription
     * @Params [description]
     * @Return void
     * @Date 2018/8/6 18:45
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
