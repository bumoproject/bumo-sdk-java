package io.bumo.sdk.core.utils.http.agent;

import java.util.Properties;

/**
 * Request parameter parser
 * 
 * @author bumo
 *
 */
interface RequestParamResolver {
	
	/**
	 * Parsing the list of method parameters into variable tables of request parameters
	 * 
	 * @param args Method parameter list
	 * @return
	 */
	Properties resolve(Object[] args);
	
}
