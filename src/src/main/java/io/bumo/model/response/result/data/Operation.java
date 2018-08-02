package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.encryption.utils.hex.HexFormat;

/**
 * @Author riven
 * @Date 2018/7/5 16:27
 */
public class Operation {
    @JSONField(name = "type")
    private int type;

    @JSONField(name = "source_address")
    private String sourceAddress;

    @JSONField(name = "metadata")
    private String metadata;

    @JSONField(name = "create_account")
    private AccountActiviateInfo createAccount;

    @JSONField(name = "issue_asset")
    private AssetIssueInfo issueAsset;

    @JSONField(name = "pay_asset")
    private AssetSendInfo sendAsset;

    @JSONField(name = "pay_coin")
    private BUSendInfo sendBU;

    @JSONField(name = "set_metadata")
    private AccountSetMetadataInfo setMetadata;

    @JSONField(name = "set_privilege")
    private AccountSetPrivilegeInfo setPrivilege;

    @JSONField(name = "log")
    private LogInfo log;

    /**
     * @Author riven
     * @Method getType
     * @Params []
     * @Return int
     * @Date 2018/7/5 16:48
     */
    public int getType() {
        return type;
    }

    /**
     * @Author riven
     * @Method setType
     * @Params [type]
     * @Return void
     * @Date 2018/7/5 16:48
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @Author riven
     * @Method getAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 16:48
     */
    public String getSourceAddress() {
        return sourceAddress;
    }

    /**
     * @Author riven
     * @Method setAddress
     * @Params [sourceAddress]
     * @Return void
     * @Date 2018/7/5 16:48
     */
    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    /**
     * @Author riven
     * @Method getMetadata
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 16:48
     */
    public String getMetadata() {
        return metadata;
    }

    /**
     * @Author riven
     * @Method setMetadata
     * @Params [metadata]
     * @Return void
     * @Date 2018/7/5 16:48
     */
    public void setMetadata(String metadata) {
        this.metadata = new String(HexFormat.hexToByte(metadata));
    }

    /**
     * @Author riven
     * @Method getCreateAccount
     * @Params []
     * @Return io.bumo.model.response.result.data.AccountActiviateInfo
     * @Date 2018/7/5 16:48
     */
    public AccountActiviateInfo getCreateAccount() {
        return createAccount;
    }

    /**
     * @Author riven
     * @Method setCreateAccount
     * @Params [createAccount]
     * @Return void
     * @Date 2018/7/5 16:48
     */
    public void setCreateAccount(AccountActiviateInfo createAccount) {
        this.createAccount = createAccount;
    }

    /**
     * @Author riven
     * @Method getIssueAsset
     * @Params []
     * @Return io.bumo.model.response.result.data.AssetIssueInfo
     * @Date 2018/7/5 16:48
     */
    public AssetIssueInfo getIssueAsset() {
        return issueAsset;
    }

    /**
     * @Author riven
     * @Method setIssueAsset
     * @Params [issueAsset]
     * @Return void
     * @Date 2018/7/5 16:48
     */
    public void setIssueAsset(AssetIssueInfo issueAsset) {
        this.issueAsset = issueAsset;
    }

    /**
     * @Author riven
     * @Method getSendAsset
     * @Params []
     * @Return io.bumo.model.response.result.data.AssetSendInfo
     * @Date 2018/7/5 16:48
     */
    public AssetSendInfo getSendAsset() {
        return sendAsset;
    }

    /**
     * @Author riven
     * @Method setSendAsset
     * @Params [sendAsset]
     * @Return void
     * @Date 2018/7/5 16:48
     */
    public void setSendAsset(AssetSendInfo sendAsset) {
        this.sendAsset = sendAsset;
    }

    /**
     * @Author riven
     * @Method getSendBU
     * @Params []
     * @Return io.bumo.model.response.result.data.BUSendInfo
     * @Date 2018/7/5 16:48
     */
    public BUSendInfo getSendBU() {
        return sendBU;
    }

    /**
     * @Author riven
     * @Method setSendBU
     * @Params [sendBU]
     * @Return void
     * @Date 2018/7/5 16:48
     */
    public void setSendBU(BUSendInfo sendBU) {
        this.sendBU = sendBU;
    }

    /**
     * @Author riven
     * @Method getSetMetadata
     * @Params []
     * @Return io.bumo.model.response.result.data.AccountSetMetadataInfo
     * @Date 2018/7/5 16:48
     */
    public AccountSetMetadataInfo getSetMetadata() {
        return setMetadata;
    }

    /**
     * @Author riven
     * @Method setSetMetadata
     * @Params [setMetadata]
     * @Return void
     * @Date 2018/7/5 16:48
     */
    public void setSetMetadata(AccountSetMetadataInfo setMetadata) {
        this.setMetadata = setMetadata;
    }

    /**
     * @Author riven
     * @Method getSetPrivilege
     * @Params []
     * @Return io.bumo.model.response.result.data.AccountSetPrivilegeInfo
     * @Date 2018/7/5 16:48
     */
    public AccountSetPrivilegeInfo getSetPrivilege() {
        return setPrivilege;
    }

    /**
     * @Author riven
     * @Method setSetPrivilege
     * @Params [setPrivilege]
     * @Return void
     * @Date 2018/7/5 16:48
     */
    public void setSetPrivilege(AccountSetPrivilegeInfo setPrivilege) {
        this.setPrivilege = setPrivilege;
    }

    /**
     * @Author riven
     * @Method getLog
     * @Params []
     * @Return io.bumo.model.response.result.data.LogInfo
     * @Date 2018/7/5 16:48
     */
    public LogInfo getLog() {
        return log;
    }

    /**
     * @Author riven
     * @Method setLog
     * @Params [log]
     * @Return void
     * @Date 2018/7/5 16:49
     */
    public void setLog(LogInfo log) {
        this.log = log;
    }
}
