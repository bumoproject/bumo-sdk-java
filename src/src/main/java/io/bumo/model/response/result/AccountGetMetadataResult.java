package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.data.MetadataInfo;

/**
 * @Author riven
 * @Date 2018/7/15 14:59
 */
public class AccountGetMetadataResult {
    @JSONField(name = "metadatas")
    private MetadataInfo[] metadatas;

    /**
     * @Author riven
     * @Method getMetadatas
     * @Params []
     * @Return io.bumo.model.response.result.data.MetadataInfo[]
     * @Date 2018/7/15 14:59
     */
    public MetadataInfo[] getMetadatas() {
        return metadatas;
    }

    /**
     * @Author riven
     * @Method setMetadatas
     * @Params [metadatas]
     * @Return void
     * @Date 2018/7/15 15:02
     */
    public void setMetadatas(MetadataInfo[] metadatas) {
        this.metadatas = metadatas;
    }
}
