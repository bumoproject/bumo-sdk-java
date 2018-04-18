package org.bumo.sdk.core.adapter.bc.response.converter;

import org.bumo.sdk.core.adapter.bc.response.TransactionHistory;

import com.alibaba.fastjson.JSONObject;

public class GetTransactionHistoryResponse extends AbstractResponseConverter{

    @Override
    public Object dealResult(ServiceResponse serviceResponse){
        if (!"0".equals(serviceResponse.getErrorCode())) {
            return null;
        }
        return JSONObject.toJavaObject(serviceResponse.getResult(), TransactionHistory.class);
    }

}
