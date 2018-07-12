package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/3 17:13
 */

public class AccountCheckValidResult {
    @JSONField(name = "is_valid")
    private boolean isValid;

    /**
     * @Author riven
     * @Method isValid
     * @Params []
     * @Return boolean
     * @Date 2018/7/4 15:12
     */
    public boolean isValid() {
        return this.isValid;
    }

    /**
     * @Author riven
     * @Method setValid
     * @Params [isValid]
     * @Return void
     * @Date 2018/7/4 15:10
     */
    private void setValid(boolean isValid) {
        this.isValid = isValid;
    }
}
