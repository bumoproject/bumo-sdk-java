package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/5 16:33
 */
public class MetadataInfo {
    @JSONField(name = "key")
    private String key;

    @JSONField(name = "value")
    private String value;

    @JSONField(name = "version")
    private Long version;

    /**
     * @Author riven
     * @Method getKey
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 16:34
     */
    public String getKey() {
        return key;
    }

    /**
     * @Author riven
     * @Method setKey
     * @Params [key]
     * @Return void
     * @Date 2018/7/5 16:34
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @Author riven
     * @Method getValue
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 16:34
     */
    public String getValue() {
        return value;
    }

    /**
     * @Author riven
     * @Method setValue
     * @Params [value]
     * @Return void
     * @Date 2018/7/5 16:34
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @Author riven
     * @Method getVersion
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/5 16:34
     */
    public Long getVersion() {
        return version;
    }

    /**
     * @Author riven
     * @Method setVersion
     * @Params [version]
     * @Return void
     * @Date 2018/7/5 16:34
     */
    public void setVersion(Long version) {
        this.version = version;
    }
}
