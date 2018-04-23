package io.bumo.sdk.core.utils.http;

import java.util.Properties;

/**
 * Request parameter filter
 * 
 * @author bumo
 *
 */
public interface RequestParamFilter {
	
	void filter(HttpMethod requestMethod, Properties requestParams);
	
}
