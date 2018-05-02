package io.bumo.sdk.core.adapter.bc.response.converter;

import io.bumo.sdk.core.adapter.bc.response.Account;
import io.bumo.sdk.core.utils.http.util.SerializeUtils;

/**
 * 解析rpc返回结果中的result
 *
 * @author 布萌
 */
public class GetAccountResponseConverter extends AbstractResponseConverter{

    @Override
    public Object dealResult(ServiceResponse serviceResponse){
        //		return JSONObject.toJavaObject(serviceResponse.getResult(), Account.class);
        return SerializeUtils.deserializeAs(serviceResponse.getResult(), Account.class);
    }


}
