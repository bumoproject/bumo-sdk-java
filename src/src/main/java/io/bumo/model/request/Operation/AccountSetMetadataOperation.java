package io.bumo.model.request.Operation;

import io.bumo.common.OperationType;

/**
 * @Author riven
 * @Date 2018/7/9 16:47
 */
public class AccountSetMetadataOperation extends BaseOperation {
    private OperationType operationType = OperationType.ACCOUNT_SET_METADATA;
    private String key;
    private String value;
    private Long version;
    private Boolean deleteFlag;

    /**
     * @Author riven
     * @Method getOperationType
     * @Params []
     * @Return io.bumo.common.OperationType
     * @Date 2018/7/9 17:12
     */
    @Override
    public OperationType getOperationType() {
        return operationType;
    }

    /**
     * @Author riven
     * @Method getKey
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 16:47
     */
    public String getKey() {
        return key;
    }

    /**
     * @Author riven
     * @Method setKey
     * @Params [key]
     * @Return void
     * @Date 2018/7/9 16:47
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @Author riven
     * @Method getValue
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 16:47
     */
    public String getValue() {
        return value;
    }

    /**
     * @Author riven
     * @Method setValue
     * @Params [value]
     * @Return void
     * @Date 2018/7/9 16:48
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @Author riven
     * @Method getVersion
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/9 16:48
     */
    public Long getVersion() {
        return version;
    }

    /**
     * @Author riven
     * @Method setVersion
     * @Params [version]
     * @Return void
     * @Date 2018/7/9 16:48
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * @Author riven
     * @Method getDeleteFlag
     * @Params []
     * @Return java.lang.Boolean
     * @Date 2018/7/9 16:48
     */
    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * @Author riven
     * @Method setDeleteFlag
     * @Params [deleteFlag]
     * @Return void
     * @Date 2018/7/9 16:52
     */
    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
