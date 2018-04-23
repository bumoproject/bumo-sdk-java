package io.bumo.sdk.core.utils.http.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.util.TypeUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

import static com.alibaba.fastjson.util.TypeUtils.*;

@JSONType(serializer = JSONBeanSerializer.class, deserializer = JSONBeanDeserializer.class)
public class JSONBean{

    /**
     * The regular expression of the attribute path of JSON Bean <br>
     * <p>
     * Attribute path by the field name, plus optional array index "[position]", followed by a period "." <br>
     * <p>
     * 如： contract.addresses[1].telephone
     */
    private static final String KEY_PATH_REGEX = "\\A([\\w\\$]+(\\[\\d+\\])?\\.)*[\\w\\$]+\\z";

    private static final Pattern KEY_PATH_PATTERN = Pattern.compile(KEY_PATH_REGEX);

    private JSONObject jsonObject;

    public JSONBean(){
        this(new JSONObject());
    }

    protected JSONBean(JSONObject jsonObj){
        this.jsonObject = jsonObj;
    }


    public JSONObject getJsonObject(){
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    public boolean isEmpty(){
        return jsonObject.isEmpty();
    }

    /**
     * Merge all attributes of the specified JSON Bean to the current object <br>
     * <p>
     * A replacement strategy is applied to array type attributes, and {@link ArrayMergeStrategy.REPLACE} is combined；
     *
     * @param JsonBean to merge JSON objects
     * @return
     */
    public JSONBean merge(JSONBean jsonBean){
        if (jsonBean == this) {
            return this;
        }
        return merge(jsonBean, ArrayMergeStrategy.REPLACE);
    }

    protected Object get(String keyPath){
        if (!KEY_PATH_PATTERN.matcher(keyPath).matches()) {
            throw new IllegalArgumentException("Illegal key path! --keyPath=[" + keyPath + "]");
        }
        int startInx = 0;
        int endIdx = -1;
        Object value = null;
        do {
            // Piecewise decomposition properties
            endIdx = keyPath.indexOf('.', startInx);
            String key;
            if (endIdx > startInx) {
                key = keyPath.substring(startInx, endIdx);

                startInx = endIdx + 1;// Update the starting index of the next search
            } else {
                // The last item
                key = keyPath.substring(startInx);
            }
            String field = key;
            int leftIdx = key.indexOf('[');
            int position = -1;
            if (leftIdx > 0) {
                field = key.substring(0, leftIdx);
                position = Integer.parseInt(key.substring(leftIdx + 1, key.length() - 1));
                // Note: the regular expression of KeyPath limits the number of array subscript, which cannot be negative. Otherwise, it cannot pass the check
            }

            // Parsing object attributes
            if (value == null) {
                value = jsonObject.get(field);
            } else {
                if (value instanceof JSONObject) {
                    value = ((JSONObject) value).get(field);
                } else if (value instanceof JSONArray) {
                    throw new IllegalArgumentException("Array cann't be accessed by field[" + field + "]!");
                } else {
                    throw new IllegalArgumentException(
                            "JSON primitive value cann't be accessed by field[" + field + "]!");
                }
            }
            if (value == null) {
                return null;
            }
            // Parsing array elements
            if (position > -1) {
                if (value instanceof JSONArray) {
                    JSONArray arrayValue = (JSONArray) value;
                    if (position < arrayValue.size()) {
                        value = arrayValue.get(position);
                    } else {
                        throw new IndexOutOfBoundsException(
                                "Index[" + position + "] out of the size of array field[" + field + "]!");
                    }
                } else if (value instanceof JSONObject) {
                    throw new IllegalArgumentException(
                            "JSON object field[" + field + "] cann't be accessed by array index!");
                } else {
                    throw new IllegalArgumentException(
                            "JSON primitive value field[" + field + "] cann't be accessed by array index!");
                }
            }
        } while (endIdx > -1);

        return value;
    }


    /**
     * Set properties
     * <p>
     * Note: unrealized analysis of key path
     *
     * @param key
     * @param value
     */
    protected void set(String key, Object value){
        //TODO: No achieve Parsing key path
        jsonObject.put(key, value);
    }


    public <T> T getObject(String key, Class<T> clazz){
        Object obj = get(key);
        return TypeUtils.castToJavaBean(obj, clazz);
    }

    public Boolean getBoolean(String key){
        Object value = get(key);

        if (value == null) {
            return null;
        }

        return castToBoolean(value);
    }

    public byte[] getBytes(String key){
        Object value = get(key);

        if (value == null) {
            return null;
        }

        return castToBytes(value);
    }

    public boolean getBooleanValue(String key){
        Object value = get(key);

        if (value == null) {
            return false;
        }

        return castToBoolean(value).booleanValue();
    }

    public Byte getByte(String key){
        Object value = get(key);

        return castToByte(value);
    }

    public byte getByteValue(String key){
        Object value = get(key);

        if (value == null) {
            return 0;
        }

        return castToByte(value).byteValue(); // TODO if value is ""、"null" or "NULL"，There may be a null pointer. Do we need to deal with it? There are similar situations in other transformations
    }

    public Short getShort(String key){
        Object value = get(key);

        return castToShort(value);
    }

    public short getShortValue(String key){
        Object value = get(key);

        if (value == null) {
            return 0;
        }

        return castToShort(value).shortValue();
    }

    public Integer getInteger(String key){
        Object value = get(key);

        return castToInt(value);
    }

    public int getIntValue(String key){
        Object value = get(key);

        if (value == null) {
            return 0;
        }

        return castToInt(value).intValue();
    }

    public Long getLong(String key){
        Object value = get(key);

        return castToLong(value);
    }

    public long getLongValue(String key){
        Object value = get(key);

        if (value == null) {
            return 0L;
        }

        return castToLong(value).longValue();
    }

    public Float getFloat(String key){
        Object value = get(key);

        return castToFloat(value);
    }

    public float getFloatValue(String key){
        Object value = get(key);

        if (value == null) {
            return 0F;
        }

        return castToFloat(value).floatValue();
    }

    public Double getDouble(String key){
        Object value = get(key);

        return castToDouble(value);
    }

    public double getDoubleValue(String key){
        Object value = get(key);

        if (value == null) {
            return 0D;
        }

        return castToDouble(value);
    }

    public BigDecimal getBigDecimal(String key){
        Object value = get(key);

        return castToBigDecimal(value);
    }

    public BigInteger getBigInteger(String key){
        Object value = get(key);

        return castToBigInteger(value);
    }

    public String getString(String key){
        Object value = get(key);

        if (value == null) {
            return null;
        }

        return value.toString();
    }

    public Date getDate(String key){
        Object value = get(key);

        return castToDate(value);
    }

    public java.sql.Date getSqlDate(String key){
        Object value = get(key);

        return castToSqlDate(value);
    }

    public java.sql.Timestamp getTimestamp(String key){
        Object value = get(key);

        return castToTimestamp(value);
    }

    @Override
    public String toString(){
        return jsonObject.toJSONString();
    }

    public <T> T toJavaBean(Class<T> beanClass){
        return jsonObject.toJavaObject(beanClass);
    }

    /**
     * Merge all attributes of the specified JSON Bean to the current object
     *
     * @param jsonBean           JSON objects to be merged
     * @param arrayMergeStrategy Merge strategy for attributes of an array type
     * @return
     */
    public JSONBean merge(JSONBean jsonBean, ArrayMergeStrategy arrayMergeStrategy){
        mergeJSONObject(this.jsonObject, jsonBean.jsonObject, arrayMergeStrategy);
        return this;
    }

    private static void mergeJSONObject(JSONObject target, JSONObject from, ArrayMergeStrategy arrayMergeStrategy){
        for (Map.Entry<String, Object> field : from.entrySet()) {
            if (target.containsKey(field.getKey())) {
                Object targetFieldValue = target.get(field.getKey());
                Object fromFieldValue = field.getValue();
                if ((targetFieldValue instanceof JSONObject) && (fromFieldValue instanceof JSONObject)) {
                    // It's all JSON objects, deep merge
                    mergeJSONObject((JSONObject) targetFieldValue, (JSONObject) fromFieldValue, arrayMergeStrategy);
                } else if ((targetFieldValue instanceof JSONArray) && (fromFieldValue instanceof JSONArray)) {
                    // It's an array of JSON, and the array is merged
                    mergeJSONArray((JSONArray) targetFieldValue, (JSONArray) fromFieldValue, arrayMergeStrategy);
                } else {
                    // Different types, direct replacement
                    target.put(field.getKey(), field.getValue());
                }
            } else {
                target.put(field.getKey(), field.getValue());
            }
        }
    }

    private static void mergeJSONArray(JSONArray target, JSONArray from, ArrayMergeStrategy strategy){
        switch (strategy) {
            case REPLACE:
                if (target.size() > from.size()) {
                    for (int i = 0; i < from.size(); i++) {
                        target.set(i, from.get(i));
                    }
                } else {
                    target.clear();
                    target.addAll(from);
                }
                break;
            case APPEND:
                target.addAll(from);
                break;
            case DEEP_MERGE:
                // Amalgamation of the same elements
                int mergeCount = Math.min(target.size(), from.size());
                Object targetFieldValue;
                Object fromFieldValue;
                for (int i = 0; i < mergeCount; i++) {
                    targetFieldValue = target.get(i);
                    fromFieldValue = from.get(i);
                    if ((targetFieldValue instanceof JSONObject) && (fromFieldValue instanceof JSONObject)) {
                        // It's all JSON objects, deep merge
                        mergeJSONObject((JSONObject) targetFieldValue, (JSONObject) fromFieldValue, strategy);
                    } else if ((targetFieldValue instanceof JSONArray) && (fromFieldValue instanceof JSONArray)) {
                        // It's an array of JSON, and the array is merged
                        mergeJSONArray((JSONArray) targetFieldValue, (JSONArray) fromFieldValue, strategy);
                    } else {
                        // Different types, direct replacement
                        target.set(i, fromFieldValue);
                    }
                }
                // Additional elements are added
                if (from.size() > target.size()) {
                    for (int i = target.size(); i < from.size(); i++) {
                        target.add(from.get(i));
                    }
                }
                break;
            default:
                throw new IllegalStateException("Unsupported strategy[" + strategy + "] for merging json array!");
        }
    }

    public static JSONBean parse(String json){
        JSONObject jsonObj = parseJSONObject(json);
        return new JSONBean(jsonObj);
    }

    public static JSONBean wrap(Object object){
        if (object == null) {
            throw new IllegalArgumentException("Null object!");
        }
        if (object instanceof JSONBean) {
            return (JSONBean) object;
        }
        JSONObject jsonObj = toJSONObject(object);
        return new JSONBean(jsonObj);
    }

    public static JSONBean tryWrap(Object object){
        if (object == null) {
            return null;
        }
        if (object instanceof JSONBean) {
            return (JSONBean) object;
        }
        JSONObject jsonObj = tryToJSONObject(object);
        if (jsonObj == null) {
            return null;
        }
        return new JSONBean(jsonObj);
    }

    public static JSONObject tryToJSONObject(Object javaObject){
        if (javaObject == null) {
            return null;
        }

        if (javaObject instanceof JSONObject) {
            return (JSONObject) javaObject;
        }

        if (javaObject instanceof Collection<?>) {
            return null;
        }
        Class<?> cls = javaObject.getClass();
        if (cls.isArray()) {
            return null;
        }
        if (cls.isEnum()) {
            return null;
        }
        if (isPrimitive(cls)) {
            return null;
        }

        Object obj = JSONObject.toJSON(javaObject);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }

        return null;
    }

    private static JSONObject parseJSONObject(String json){
        Object obj = JSON.parse(json);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        throw new IllegalArgumentException("The specified json string is not a JSON Object!");
    }

    private static JSONObject toJSONObject(Object javaObject){
        if (javaObject == null) {
            throw new IllegalArgumentException("The wrapped java object is null!");
        }

        if (javaObject instanceof JSONObject) {
            return (JSONObject) javaObject;
        }

        if (javaObject instanceof Collection<?>) {
            throw new IllegalArgumentException("The wrapped java object is a collection!");
        }
        Class<?> cls = javaObject.getClass();
        if (cls.isArray()) {
            throw new IllegalArgumentException("The wrapped java object is a array!");
        }
        if (cls.isEnum()) {
            throw new IllegalArgumentException("The wrapped java object is a enum!");
        }
        if (isPrimitive(cls)) {
            throw new IllegalArgumentException("The wrapped java object is a primitive value!");
        }

        Object obj = JSONObject.toJSON(javaObject);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }

        throw new IllegalArgumentException("The wrapped java object cann't be converted to a JSON Object!");
    }


    private static boolean isPrimitive(Class<?> clazz){
        return clazz.isPrimitive() //
                || clazz == Boolean.class //
                || clazz == Character.class //
                || clazz == Byte.class //
                || clazz == Short.class //
                || clazz == Integer.class //
                || clazz == Long.class //
                || clazz == Float.class //
                || clazz == Double.class //
                || clazz == BigInteger.class //
                || clazz == BigDecimal.class //
                || clazz == String.class //
                || clazz == Date.class //
                || clazz == java.sql.Date.class //
                || clazz == java.sql.Time.class //
                || clazz == java.sql.Timestamp.class //
                || clazz.isEnum() //
                ;
    }

}
