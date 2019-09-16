package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.data.ValidatorInfo;

/**
 * @Author riven
 * @Date 2018/7/12 01:07
 */
public class BlockGetValidatorsResult {
    @JSONField(name = "validators")
    private String[] validators;

    /**
     * @Author riven
     * @Method getValidators
     * @Params []
     * @Return io.bumo.model.response.result.data.ValidatorInfo[]
     * @Date 2018/7/12 10:21
     */
    public String[] getValidators() {
        return validators;
    }

    /**
     * @Author riven
     * @Method setValidators
     * @Params [validators]
     * @Return void
     * @Date 2018/7/12 10:27
     */
    public void setValidators(String[] validators) {
        this.validators = validators;
    }
}
