package io.bumo.asset;

import io.bumo.model.request.AssetGetInfoRequest;
import io.bumo.model.response.AssetGetInfoResponse;

/**
 * @Author riven
 * @Date 2018/7/3 17:21
 */
public interface AssetService {
    /**
     * @Author riven
     * @Method getInfo
     * @Params [assetGetRequest]
     * @Return io.bumo.model.response.AssetGetInfoResponse
     * @Date 2018/7/5 12:05
     */
    public AssetGetInfoResponse getInfo(AssetGetInfoRequest assetGetRequest);
}
