package io.bumo.sdk.core.adapter.bc.response.converter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * rpc返回结果
 * @author 布萌
 *
 */
public class ServiceResponse {
	@JSONField(name="error_code")
	private String errorCode;
	private JSONObject result;
	
	public JSONObject getResult() {
		return result;
	}
	public void setResult(JSONObject result) {
		this.result = result;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
