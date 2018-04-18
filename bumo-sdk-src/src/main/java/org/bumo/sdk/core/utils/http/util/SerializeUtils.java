package org.bumo.sdk.core.utils.http.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.*;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.bumo.sdk.core.utils.PrimitiveUtils;

/**
 * SerializeUtils provides unified serialization and anti serialization operation
 *
 * @author bumo
 */
public abstract class SerializeUtils{

    private static final RuntimeDeserializer RUNTIME_DESERIALIZER = new RuntimeDeserializer();

    public static void addTypeMap(Class<?> fromClazz, Class<?> toClazz){
        RUNTIME_DESERIALIZER.addTypeMap(fromClazz, toClazz);
        ParserConfig.getGlobalInstance().putDeserializer(fromClazz, RUNTIME_DESERIALIZER);
    }

    private SerializeUtils(){
    }

    public static Type getGenericType(Object obj){
        return getGenericTypes(obj)[0];
    }

    public static Type[] getGenericTypes(Object obj){
        Type superClass = obj.getClass().getGenericSuperclass();

        Type[] types = ((ParameterizedType) superClass).getActualTypeArguments();
        return types;
    }

    /**
     * Determine whether it is a JSON character
     * <p>
     * <p>
     * The way to judge JSON characters is to check whether the specified characters are surrounded by braces, {} or square brackets [] or double quotes
     * <p>
     * <p>
     * This is simply a simplified but not rigorous way to check that the content of the back to true does not mean that the contents of the brackets {} or the brackets [] or the double quotes "" are consistent with the JSON syntax
     *
     * @param str
     * @return
     */
    public static boolean isJSON(String str){
        return isJSONObject(str) || isJSONOArray(str) || isJSONOValue(str);
    }

    /**
     * Whether it is a JSON object or not
     * <p>
     * <p>
     * The method of JSON object judged by this method is to check whether the specified character is surrounded by braces, {}
     * <p>
     * <p>
     * This is a simplified but not rigorous method. Checking by returning true does not mean that the content between brackets and {} is in line with JSON syntax
     *
     * @param str
     * @return
     */
    public static boolean isJSONObject(String str){
        return str.startsWith("{") && str.endsWith("}");
    }

    /**
     * Determine whether it is an JSONArray
     * <p>
     * <p>
     * The method of JSON array determined by this method is to check whether the specified characters are surrounded by square brackets
     * <p>
     * <p>
     * This is a simplified but not rigorous method. Checking by returning true does not mean that the content between square brackets [] is in line with JSON syntax
     *
     * @param str
     * @return
     */
    public static boolean isJSONOArray(String str){
        return str.startsWith("[") && str.endsWith("]");
    }

    /**
     * Whether it is JSON value or not
     * <p>
     * <p>
     * The method of judging the JSON character by this method is to check whether the specified character is surrounded by double quotes
     * <p>
     * <p>
     * This is a simplified but not rigorous method. Checking through returning true does not mean that the content between double quotes is in line with JSON syntax
     *
     * @param str
     * @return
     */
    public static boolean isJSONOValue(String str){
        return str.startsWith("\"") && str.endsWith("\"");
    }

    /**
     * Serialize objects into JSON strings; (compact format)
     *
     * @param data
     * @return
     */
    public static String serializeToJSON(Object data){
        return serializeToJSON(data, null, false);
    }

    /**
     * Serialize objects into JSON strings; (compact format)
     *
     * @param data
     * @return
     */
    public static String serializeToJSON(Object data, Class<?> serializedType){
        return serializeToJSON(data, serializedType, false);
    }

    /**
     * Serialize the object into a JSON string
     *
     * @param data         An object to be serialized
     * @param prettyFormat Does it output JSON in a good format including line changing and indentation?
     * @return
     */
    public static String serializeToJSON(Object data, boolean prettyFormat){
        return serializeToJSON(data, null, prettyFormat);
    }

    /**
     * Serialize the object into a JSON string
     *
     * @param data           The object to be serialized
     * @param serializedType The type of output of the object to be serialized<br>
     *                       Specifies the type of the object's parent or the interface type of an implementation. The serialized output JSON will contain only the attributes of that type<br>
     *                       If NULL is specified, it is serialized according to the type of the object itself
     * @param prettyFormat   Does it output JSON in a good format including line changing and indentation?
     * @return
     */
    public static String serializeToJSON(Object data, Class<?> serializedType, boolean prettyFormat){
        return serializeToJSON(data, serializedType, null, prettyFormat);
    }

    /**
     * Serialize the object into a JSON string
     *
     * @param data           The object to be serialized
     * @param serializedType The type of output of the object to be serialized<br>
     *                       Specifies the type of the object's parent or the interface type of an implementation. The serialized output JSON will contain only the attributes of that type<br>
     *                       If NULL is specified, it is serialized according to the type of the object itself
     * @param dateFormat     Date format
     * @param prettyFormat   Does it output JSON in a good format including line changing and indentation?
     * @return
     */
    public static String serializeToJSON(Object data, Class<?> serializedType, String dateFormat,
                                         boolean prettyFormat){
        SerializeWriter out;

        if (prettyFormat) {
            out = new SerializeWriter((Writer) null, JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.PrettyFormat);
        } else {
            out = new SerializeWriter((Writer) null, JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.EMPTY);
        }

        try {
            if (data == null) {
                return null;
            } else {
                // Determine the type to be serialized
                if (serializedType == null) {
                    serializedType = data.getClass();
                } else if ((!PrimitiveUtils.isPrimitiveType(serializedType))
                        && (!PrimitiveUtils.isPrimitiveType(data.getClass()))
                        && (!serializedType.isAssignableFrom(data.getClass()))) {
                    throw new IllegalArgumentException("The serialized type[" + serializedType.getName()
                            + "] isn't assignable from the data type[" + data.getClass().getName() + "]!");
                }

                if (PrimitiveUtils.isWrapping(data.getClass(), serializedType)) {
                    // Avoid the serialization exception of fastjson when avoiding the serializedType native value type
                    serializedType = data.getClass();
                }

                JSONSerializer serializer = new JSONSerializer(out, SerializeConfig.globalInstance);

                // Configuration date format
                if (dateFormat != null && dateFormat.length() != 0) {
                    serializer.setDateFormat(dateFormat);
                    serializer.config(SerializerFeature.WriteDateUseDateFormat, true);
                }

                // Serialization
                ObjectSerializer writer = serializer.getObjectWriter(serializedType);

                writer.write(serializer, data, null, null, JSON.DEFAULT_GENERATE_FEATURE);
            }
            return out.toString();
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Error occurred on serializing type[" + serializedType.getName() + "]! --" + e.getMessage(), e);
        } finally {
            out.close();
        }
    }

    /**
     * @param json
     * @param dataClazz
     * @return
     */
    public static <T> T deserializeFromJSON(String json, Class<T> dataClazz){
        return JSON.parseObject(json, dataClazz);
    }

    /**
     * @param json
     * @param dataClazz
     * @return
     */
    public static <T> T deserializeFromJSON(String json, GenericType<T> type){
        return JSON.parseObject(json, type.getTypeArgument());
    }

    @SuppressWarnings("unchecked")
	public static <T> T deserializeAs(Object data, Class<T> clazz){
        if (data == null) {
            return null;
        }
        if (data instanceof JSON) {
            return ((JSON) data).toJavaObject(clazz);
        }
        if (data instanceof JSONBean) {
            return ((JSONBean) data).toJavaBean(clazz);
        }
        if (data instanceof String) {
            if (isJSON((String) data)) {
                return deserializeFromJSON((String) data, clazz);
            }
        }
        if (data instanceof JSONString) {
            String jsonStr = ((JSONString) data).toString();
            if (isJSON(jsonStr)) {
                return deserializeFromJSON(jsonStr, clazz);
            } else {
                data = jsonStr;
            }
        }
        if (PrimitiveUtils.isPrimitiveType(clazz)) {
            return PrimitiveUtils.castTo(data, clazz);
        }
        if (clazz.isAssignableFrom(data.getClass())) {
            return (T) data;
        }
        if (clazz.isAssignableFrom(String.class)) {
            return (T) data.toString();
        }
        throw new IllegalArgumentException("Unsupported deserialization from type[" + data.getClass().toString()
                + "] to type[" + clazz.toString() + "]!");
    }

}
