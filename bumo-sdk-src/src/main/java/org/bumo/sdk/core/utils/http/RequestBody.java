package org.bumo.sdk.core.utils.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies how a method parameter is mapped to the body of HTTP request
 * 
 * Note: in one way, only one parameter can be identified as RequestBody
 * 
 * @author bumo
 *
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestBody {
	
	public boolean required() default true;
	
	/**
	 * The type of the parameter value converter
	 * 
	 * The specified parameter value converter must implement RequestBodyConverter interface
	 * 
	 * If it is not specified, 
	 * 
	 * For InputStream or byte array types, the output of byte contents is directly output
	 * 
	 * For other types, we get the text value by the Object.toString () method according to the specified encoding
	 * 
	 * @return
	 */
	public Class<?> converter() default RequestBodyConverter.class;
	
}
