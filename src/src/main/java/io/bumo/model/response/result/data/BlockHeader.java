package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/6 01:52
 */
public class BlockHeader {
    @JSONField(name = "close_time")
    private Long closeTime;

    @JSONField(name = "seq")
    private Long number;

    @JSONField(name = "tx_count")
    private Long txCount;

    @JSONField(name = "version")
    private String version;

    /**
     * @Author riven
     * @Method getCloseTime
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/12 00:36
     */
    public Long getCloseTime() {
        return closeTime;
    }

    /**
     * @Author riven
     * @Method setCloseTime
     * @Params [closeTime]
     * @Return void
     * @Date 2018/7/12 00:36
     */
    public void setCloseTime(Long closeTime) {
        this.closeTime = closeTime;
    }

    /**
     * @Author riven
     * @Method getNumber
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/12 00:36
     */
    public Long getNumber() {
        return number;
    }

    /**
     * @Author riven
     * @Method setNumber
     * @Params [number]
     * @Return void
     * @Date 2018/7/12 00:36
     */
    public void setNumber(Long number) {
        this.number = number;
    }

    /**
     * @Author riven
     * @Method getTxCount
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/12 00:36
     */
    public Long getTxCount() {
        return txCount;
    }

    /**
     * @Author riven
     * @Method setTxCount
     * @Params [txCount]
     * @Return void
     * @Date 2018/7/12 00:36
     */
    public void setTxCount(Long txCount) {
        this.txCount = txCount;
    }

    /**
     * @Author riven
     * @Method getVersion
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/12 00:36
     */
    public String getVersion() {
        return version;
    }

    /**
     * @Author riven
     * @Method setVersion
     * @Params [version]
     * @Return void
     * @Date 2018/7/12 00:37
     */
    public void setVersion(String version) {
        this.version = version;
    }
}
