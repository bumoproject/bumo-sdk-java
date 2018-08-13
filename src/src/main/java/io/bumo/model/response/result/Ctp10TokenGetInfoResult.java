package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.data.TokenInfo;

/**
 * @Author riven
 * @Date 2018/7/6 13:34
 */
public class Ctp10TokenGetInfoResult {
    @JSONField(name = "contractInfo")
    private TokenInfo contractInfo;

    /**
     * @Author riven
     * @Method getContractInfo
     * @Params []
     * @Return io.bumo.model.response.result.data.TokenInfo
     * @Date 2018/7/11 23:02
     */
    public TokenInfo getContractInfo() {
        return contractInfo;
    }

    /**
     * @Author riven
     * @Method setContractInfo
     * @Params [contractInfo]
     * @Return void
     * @Date 2018/7/11 23:04
     */
    public void setContractInfo(TokenInfo contractInfo) {
        this.contractInfo = contractInfo;
    }
}
