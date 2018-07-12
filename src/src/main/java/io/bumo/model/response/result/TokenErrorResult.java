package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.data.TokenErrorInfo;

/**
 * @Author riven
 * @Date 2018/7/11 23:31
 */
public class TokenErrorResult {
    @JSONField(name = "data")
    private TokenErrorInfo data;

    /**
     * @Author riven
     * @Method getData
     * @Params []
     * @Return io.bumo.model.response.result.data.TokenErrorInfo
     * @Date 2018/7/11 23:40
     */
    public TokenErrorInfo getData() {
        return data;
    }

    /**
     * @Author riven
     * @Method setData
     * @Params [data]
     * @Return void
     * @Date 2018/7/12 00:37
     */
    public void setData(TokenErrorInfo data) {
        this.data = data;
    }
}
