package io.bumo.sdk.core.utils.http.agent;

import java.util.Map;

/**
 * Path parameter parser
 * 
 * @author bumo
 *
 */
interface PathParamResolver {
	
	/**
	 * The list of method parameters is parsed into a variable table of path parameters
	 * 
	 * @param args Method parameter list
	 * @return
	 */
	Map<String, String> resolve(Object[] args);
	
}
