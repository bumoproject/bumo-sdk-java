package io.bumo.sdk.core.utils.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.bumo.sdk.core.utils.http.converters.ObjectToStringConverter;

/**
 * A parameter mapping of the identification method is part of the request path
 * <p>
 * Generally speaking, we should use String type as path parameter and mark it with this class
 * <p>
 * But if the type of the marked parameter is not String type, it is the other way to get the actual path parameter value through toString method
 *
 * @author bumo
 */
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PathParam{

    /**
     * The variable name in the path
     *
     * @return
     */
    public String name();

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
