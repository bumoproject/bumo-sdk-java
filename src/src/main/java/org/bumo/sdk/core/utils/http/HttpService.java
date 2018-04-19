package org.bumo.sdk.core.utils.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpService {

	/**
	 * The path of service
	 * 
	 * It is the root path of all service methods, and the default is /
	 * 
	 * @return
	 */
	public String path() default "/";

	/**
	 * Default reply converter
	 * 
	 * When the service method does not specify a custom reply converter, if the default reply converter is adopted and the default reply converter of the service is not set, the system predefined default reply converter is used
	 * 
	 * @return
	 */
	public Class<?>defaultResponseConverter() default ResponseConverter.class;
	
	/**
	 * Recovery Converter Factory
	 * 
	 * When a reply converter is not specified in the service method, and the default reply converter is not specified on the service, the reply converter of each method is created by the reply Converter Factory specified by this property
	 * 
	 * If this property is not set, the default recovery converter that is defined by the system is adopted

	 * @return
	 */
	public Class<?> responseConverterFactory() default ResponseConverterFactory.class;

}
