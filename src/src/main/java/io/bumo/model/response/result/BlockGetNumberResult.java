package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.data.BlockNumber;

/**
 * @Author riven
 * @Date 2018/7/6 01:52
 */
public class BlockGetNumberResult {
    @JSONField(name = "header")
    private BlockNumber header;

    /**
     * @Author riven
     * @Method getHeader
     * @Params []
     * @Return io.bumo.model.response.result.data.BlockHeader
     * @Date 2018/7/6 01:53
     */
    public BlockNumber getHeader() {
        return header;
    }

    /**
     * @Author riven
     * @Method setHeader
     * @Params [header]
     * @Return void
     * @Date 2018/7/6 02:03
     */
    public void setHeader(BlockNumber header) {
        this.header = header;
    }
}
