package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/5 15:29
 */
public class ContractCheckValidResult {
    @JSONField(name = "is_valid")
    private Boolean isValid;

    /**
     * @Author riven
     * @Method getValid
     * @Params []
     * @Return java.lang.Boolean
     * @Date 2018/7/23 11:44
     */
    public Boolean getValid() {
        return isValid;
    }

    /**
     * @Author riven
     * @Method setValid
     * @Params [valid]
     * @Return void
     * @Date 2018/7/23 11:44
     */
    public void setValid(Boolean valid) {
        isValid = valid;
    }
}
