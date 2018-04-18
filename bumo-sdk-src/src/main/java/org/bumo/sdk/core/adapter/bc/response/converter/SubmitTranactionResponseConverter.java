package org.bumo.sdk.core.adapter.bc.response.converter;

import java.io.InputStream;

import org.bumo.sdk.core.adapter.exception.BlockchainError;
import org.bumo.sdk.core.adapter.exception.BlockchainException;
import org.bumo.sdk.core.utils.http.HttpServiceContext;
import org.bumo.sdk.core.utils.http.ResponseConverter;
import org.bumo.sdk.core.utils.http.agent.ServiceRequest;
import org.bumo.sdk.core.utils.http.converters.StringResponseConverter;
import org.bumo.sdk.core.utils.spring.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class SubmitTranactionResponseConverter implements ResponseConverter{

    private Logger logger = LoggerFactory.getLogger(SubmitTranactionResponseConverter.class);

    @Override
    public Object getResponse(ServiceRequest request, InputStream responseStream, HttpServiceContext serviceContext) throws Exception{
        String jsonResponse = (String) StringResponseConverter.INSTANCE.getResponse(request, responseStream, null);
        if (jsonResponse == null) {
            return null;
        }
        jsonResponse = jsonResponse.trim();

        JSONObject responseJSON = JSONObject.parseObject(jsonResponse);
        JSONArray results = responseJSON.getJSONArray("results");
        if (results.size() == 0) {
            throw new BlockchainException("Submit transaction fail! --Response empty results!");
        }

        JSONObject result = results.getJSONObject(0);
        if (responseJSON.getInteger("success_count") == 0) {
            logger.error(result.toJSONString());
            int errorCode = result.getInteger("error_code");
            String errorDesc = result.getString("error_desc");
            if (StringUtils.isEmpty(errorDesc)) {
                errorDesc = BlockchainError.getDescription(errorCode);
            }
            throw new BlockchainException(errorCode, " sync submit transaction fail! --[ErrorCode=" + errorCode + "] --" + errorDesc);
        }

        return result.getString("hash");
    }

}
