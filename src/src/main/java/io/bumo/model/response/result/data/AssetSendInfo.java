package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/5 16:40
 */
public class AssetSendInfo {
    @JSONField(name = "dest_address")
    private String destAddress;

    @JSONField(name = "asset")
    private AssetInfo asset;

    @JSONField(name = "input")
    private String input;

    /**
     * @Author riven
     * @Method getDestAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 16:42
     */
    public String getDestAddress() {
        return destAddress;
    }

    /**
     * @Author riven
     * @Method setDestAddress
     * @Params [destAddress]
     * @Return void
     * @Date 2018/7/5 16:42
     */
    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    /**
     * @Author riven
     * @Method getAssetService
     * @Params []
     * @Return io.bumo.model.response.result.data.AssetInfo
     * @Date 2018/7/5 16:42
     */
    public AssetInfo getAsset() {
        return asset;
    }

    /**
     * @Author riven
     * @Method setAsset
     * @Params [asset]
     * @Return void
     * @Date 2018/7/5 16:42
     */
    public void setAsset(AssetInfo asset) {
        this.asset = asset;
    }

    /**
     * @Author riven
     * @Method getInput
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 16:42
     */
    public String getInput() {
        return input;
    }

    /**
     * @Author riven
     * @Method setInput
     * @Params [input]
     * @Return void
     * @Date 2018/7/5 16:42
     */
    public void setInput(String input) {
        this.input = input;
    }
}
