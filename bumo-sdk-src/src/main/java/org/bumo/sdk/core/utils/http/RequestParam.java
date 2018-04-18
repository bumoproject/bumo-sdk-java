package org.bumo.sdk.core.utils.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.bumo.sdk.core.utils.http.converters.ObjectToStringConverter;

/**
 * It is used to identify a method parameter or how a member of a class can be represented as a parameter in the HTTP request <p>
 * <p>
 * If the HTTP GET method is used to initiate the request, the parameters of the RequestParam identification will be submitted by URL in the way of query parameter <p>
 * <p>
 * If the HTTP POST method is used to initiate the request, the parameters of the RequestParam will be submitted in the form field <p>
 *
 * @author bumo
 */
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam{

    /**
     * Parameter name；
     *
     * @return
     */
    public String name();

    /**
     * Whether the parameters must be provided <p>
     * <p>
     * If the value of the necessary parameter is null, it will cause an exception<p>
     * <p>
     * If the value of the non essential parameter is null, then this parameter is ignored <p>
     * <p>
     * default is true；
     *
     * @return
     */
    public boolean required() default true;

    /**
     * Neglecting the value
     * <p>
     * When require is false, it is effective, indicating that the parameter will be ignored when the value of the parameter is null or equal to ignoreValue
     *
     * @return
     */
    public String ignoreValue() default "";

    /**
     * The type of the parameter value converter
     * <p>
     * The specified parameter value converter must implement StringConverter interface
     * <p>
     * If it is not specified, the default text value is obtained by toString () by default
     *
     * @return
     */
    public Class<?> converter() default ObjectToStringConverter.class;
}
