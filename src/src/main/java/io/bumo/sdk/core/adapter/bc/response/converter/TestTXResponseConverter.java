package io.bumo.sdk.core.adapter.bc.response.converter;

import java.io.InputStream;

import com.alibaba.fastjson.JSONObject;

import io.bumo.sdk.core.adapter.bc.response.test.EvalTxResult;
import io.bumo.sdk.core.utils.http.HttpServiceContext;
import io.bumo.sdk.core.utils.http.ResponseConverter;
import io.bumo.sdk.core.utils.http.agent.ServiceRequest;
import io.bumo.sdk.core.utils.http.converters.JsonResponseConverter;
/***
 * 评估费用的响应转换器
 * @author 布萌
 *
 */
public class TestTXResponseConverter implements ResponseConverter{

	private JsonResponseConverter jsonResponseConverter = new JsonResponseConverter(ServiceResponse.class);
	@Override
	public Object getResponse(ServiceRequest request, InputStream responseStream, HttpServiceContext serviceContext)
			throws Exception {
		ServiceResponse serviceResponse = (ServiceResponse) jsonResponseConverter.getResponse(request, responseStream, null);
        if (serviceResponse == null || !"0".equals(serviceResponse.getErrorCode())) {
            return null;
        }//TestTransaction
        return JSONObject.toJavaObject(serviceResponse.getResult(), EvalTxResult.class);
	}

}
