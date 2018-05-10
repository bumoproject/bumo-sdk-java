package io.bumo.sdk.core.adapter.bc.response.converter;

import io.bumo.sdk.core.adapter.bc.response.Account;
import io.bumo.sdk.core.utils.http.util.SerializeUtils;

/**
 * Parsing the result in the result of RPC return
 *
 * @author bumo
 */
public class GetAccountResponseConverter extends AbstractResponseConverter{

    @Override
    public Object dealResult(ServiceResponse serviceResponse){
        //		return JSONObject.toJavaObject(serviceResponse.getResult(), Account.class);
        return SerializeUtils.deserializeAs(serviceResponse.getResult(), Account.class);
    }


}
