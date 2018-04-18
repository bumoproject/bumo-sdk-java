package org.bumo.sdk.core.utils.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * It is used to identify a POJO type parameter and convert its member to request parameter table
 * 
 * This annotation is applied to the parameter of the method, and the attribute marked as RequestParam in the parameter object is added as the request parameter to the parameter list
 * 
 * @author bumo
 *
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParamMap {

	/**
	 * Attribute prefix
	 * 
	 * When a multiple parameter table is defined by RequestParams in a method, different name prefixes can be defined to prevent property name conflicts
	 * 
	 * @return
	 */
	public String prefix() default "";

	/**
	 * Attribute delimiters
	 * 
	 * The final parameter name is composed of prefix + separator and original parameter name
	 * 
	 * @return
	 */
	public String seperator() default ".";

	/**
	 * Whether the parameters must be provided
	 * 
	 * If the parameter is null at runtime, it will cause an exception
	 * 
	 * default is true；
	 * 
	 * @return
	 */
	public boolean required() default true;

	/**
	 * Custom converter
	 * 
	 * must achieve PropertiesConverter interface；
	 * 
	 * @return
	 */
	public Class<?>converter() default PropertiesConverter.class;

}
