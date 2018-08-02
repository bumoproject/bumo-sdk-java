package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.data.AssetInfo;

/**
 * @Author riven
 * @Date 2018/7/5 11:51
 */
public class AccountGetAssetsResult {
    @JSONField(name = "assets")
    private AssetInfo[] assets;

    /**
     * @Author riven
     * @Method getAssets
     * @Params []
     * @Return io.bumo.model.response.result.data.AssetInfo[]
     * @Date 2018/7/5 11:52
     */
    public AssetInfo[] getAssets() {
        return assets;
    }

    /**
     * @Author riven
     * @Method setAssets
     * @Params [assets]
     * @Return void
     * @Date 2018/7/5 11:52
     */
    public void setAssets(AssetInfo[] assets) {
        this.assets = assets;
    }
}
