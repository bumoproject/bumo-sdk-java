package io.bumo.sdk.core.utils.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * HTTP Service methods
 * 
 * @author bumo
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpAction {

	/**
	 * Request path
	 * 
	 * The default is empty
	 * 
	 * At this time, the final request path is generated according to the path attribute + method name of service
	 * 
	 * @return
	 */
	public String path() default "";

	/**
	 * HTTP Request method
	 * 
	 * @return
	 */
	public HttpMethod method();

	/**
	 * HTTP 的 Content-Type;
	 * @return
	 */
	public String contentType() default "";
	
	
	
	/**
	 * Custom return value converter; RequestParamFilter interface must be implemented
	 * 
	 * @return
	 */
	public Class<?> requestParamFilter() default RequestParamFilter.class;

	/**
	 * Custom return value converter; ResponseConverter interface must be implemented
	 * 
	 * @return
	 */
	public Class<?> responseConverter() default ResponseConverter.class;

	/**
	 * When a HTTP error is detected, does the HttpStatusException contain the contents of the reply?
	 * 
	 * default false；
	 * 
	 * @return
	 */
	public boolean resolveContentOnHttpError() default false;
}
