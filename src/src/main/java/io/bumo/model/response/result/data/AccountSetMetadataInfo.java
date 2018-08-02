package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/4 17:16
 */
public class AccountSetMetadataInfo {
    @JSONField(name = "key")
    private String key;

    @JSONField(name = "value")
    private String value;

    @JSONField(name = "version")
    private Long version;

    @JSONField(name = "delete_flag")
    private Boolean deleteFlag;


    /**
     * @Author riven
     * @Method getKey
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/4 17:17
     */
    public String getKey() {
        return key;
    }

    /**
     * @Author riven
     * @Method setKey
     * @Params [key]
     * @Return void
     * @Date 2018/7/4 17:17
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @Author riven
     * @Method getValue
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/4 17:17
     */
    public String getValue() {
        return value;
    }

    /**
     * @Author riven
     * @Method setValue
     * @Params [value]
     * @Return void
     * @Date 2018/7/4 17:17
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @Author riven
     * @Method getVersion
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/4 17:17
     */
    public Long getVersion() {
        return version;
    }

    /**
     * @Author riven
     * @Method setVersion
     * @Params [version]
     * @Return void
     * @Date 2018/7/4 17:17
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * @Author riven
     * @Method getDeleteFlag
     * @Params []
     * @Return java.lang.Boolean
     * @Date 2018/7/4 17:17
     */
    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * @Author riven
     * @Method setDeleteFlag
     * @Params [deleteFlag]
     * @Return void
     * @Date 2018/7/4 17:17
     */
    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
