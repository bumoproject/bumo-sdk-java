package io.bumo.sdk.core.utils;

import java.util.HashMap;
import java.util.Map;

public class PrimitiveUtils{

    private static Map<Class<?>, Converter> converters = new HashMap<>();

    static{
        converters.put(Boolean.class, new BooleanConverter());
        converters.put(boolean.class, new BooleanValueConverter());
        converters.put(Byte.class, new ByteConverter());
        converters.put(byte.class, new ByteValueConverter());
        converters.put(Character.class, new CharConverter());
        converters.put(char.class, new CharValueConverter());
        converters.put(Integer.class, new IntegerConverter());
        converters.put(int.class, new IntegerValueConverter());
        converters.put(Long.class, new LongConverter());
        converters.put(long.class, new LongValueConverter());
        converters.put(Double.class, new DoubleConverter());
        converters.put(double.class, new DoubleValueConverter());
        converters.put(Float.class, new FloatConverter());
        converters.put(float.class, new FloatValueConverter());
    }

    /**
     * Determine whether the specified two types are each other as a wrapper
     *
     * @param type1
     * @param type2
     * @return
     */
    public static boolean isWrapping(Class<?> type1, Class<?> type2){
        Converter converter = converters.get(type1);
        return converter != null && converter.isWrapping(type2);
    }

    /**
     * Determine whether the specified two types are each other as a wrapper
     *
     * @param clazz
     * @return
     */
    public static boolean isPrimitiveType(Class<?> clazz){
        return converters.containsKey(clazz);
    }

    /**
     * Returns the default value of the specified type
     * <p>
     * If the specified type is a native type, it returns the value of the default original type; otherwise, it returns to null
     *
     * @param clazz
     * @return
     */
    public static Object getDefaultValue(Class<?> clazz){
        Converter converter = converters.get(clazz);
        if (converter == null) {
            return null;
        }
        return converter.defaultValue();
    }

    /**
     * Transfer the specified data to the specified type<br>
     * <p>
     * If the specified type is not the basic type, it always returns to null
     *
     * @param data
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <T> T castTo(Object data, Class<T> clazz){
        Converter converter = converters.get(clazz);
        if (converter == null) {
            return null;
        }
        return (T) converter.convert(data);
    }

    private static interface Converter{

        Object defaultValue();

        Object convert(Object data);

        boolean isWrapping(Class<?> type);

    }

    private static class BooleanConverter implements Converter{

        @Override
        public Object convert(Object data){
            if (data == null) {
                return null;
            }
            if (data instanceof Boolean) {
                return (Boolean) data;
            }
            return Boolean.parseBoolean(data.toString());
        }

        @Override
        public Object defaultValue(){
            return null;
        }

        @Override
        public boolean isWrapping(Class<?> type){
            return type == boolean.class || type == Boolean.class;
        }
    }

    private static class BooleanValueConverter extends BooleanConverter{

        @Override
        public Object convert(Object data){
            Boolean value = (Boolean) super.convert(data);
            if (value == null) {
                return false;
            }
            return value;
        }

        @Override
        public Object defaultValue(){
            return false;
        }
    }

    private static class CharConverter implements Converter{

        @Override
        public Object convert(Object data){
            if (data == null) {
                return null;
            }
            if (data instanceof Character) {
                return (char) ((Character) data).charValue();
            }
            if (data instanceof Byte) {
                return (char) ((Byte) data).byteValue();
            }
            if (data instanceof String) {
                String str = (String) data;
                if (str.length() == 1) {
                    return str.charAt(0);
                }
            }
            throw new IllegalArgumentException("Cann't conver [" + data + "] to charater!");
        }

        @Override
        public Object defaultValue(){
            return null;
        }

        @Override
        public boolean isWrapping(Class<?> type){
            return type == char.class || type == Character.class;
        }
    }

    private static class CharValueConverter extends CharConverter{

        @Override
        public Object convert(Object data){
            Character value = (Character) super.convert(data);
            if (value == null) {
                throw new IllegalArgumentException("Cann't convert null charater to char!");
            }
            return value;
        }

        @Override
        public Object defaultValue(){
            return (char) 0;
        }
    }

    private static class ByteConverter implements Converter{

        @Override
        public Object convert(Object data){
            if (data == null) {
                return null;
            }
            if (data instanceof Byte) {
                return (int) ((Byte) data).byteValue();
            }
            return Byte.parseByte(data.toString());
        }

        @Override
        public Object defaultValue(){
            return null;
        }

        @Override
        public boolean isWrapping(Class<?> type){
            return type == byte.class || type == Byte.class;
        }
    }

    private static class ByteValueConverter extends ByteConverter{

        @Override
        public Object convert(Object data){
            Byte value = (Byte) super.convert(data);
            if (value == null) {
                return Byte.valueOf((byte) 0);
            }
            return value;
        }

        @Override
        public Object defaultValue(){
            return (byte) 0;
        }
    }

    private static class IntegerConverter implements Converter{

        @Override
        public Object convert(Object data){
            if (data == null) {
                return null;
            }
            if (data instanceof Integer) {
                return (Integer) data;
            }
            if (data instanceof Byte) {
                return ((Byte) data).intValue();
            }
            return Integer.parseInt(data.toString());
        }

        @Override
        public Object defaultValue(){
            return null;
        }

        @Override
        public boolean isWrapping(Class<?> type){
            return type == int.class || type == Integer.class;
        }
    }

    private static class IntegerValueConverter extends IntegerConverter{

        @Override
        public Object convert(Object data){
            Integer value = (Integer) super.convert(data);
            if (value == null) {
                return 0;
            }
            return value;
        }

        @Override
        public Object defaultValue(){
            return (int) 0;
        }
    }

    private static class LongConverter implements Converter{

        @Override
        public Object convert(Object data){
            if (data == null) {
                return null;
            }
            if (data instanceof Long) {
                return (Long) data;
            }
            if (data instanceof Integer) {
                return ((Integer) data).longValue();
            }
            if (data instanceof Byte) {
                return ((Byte) data).longValue();
            }
            return Long.parseLong(data.toString());
        }

        @Override
        public Object defaultValue(){
            return null;
        }

        @Override
        public boolean isWrapping(Class<?> type){
            return type == long.class || type == Long.class;
        }
    }

    private static class LongValueConverter extends LongConverter{

        @Override
        public Object convert(Object data){
            Long value = (Long) super.convert(data);
            if (value == null) {
                return Long.valueOf(0);
            }
            return value;
        }

        @Override
        public Object defaultValue(){
            return 0L;
        }
    }

    private static class DoubleConverter implements Converter{

        @Override
        public Object convert(Object data){
            if (data == null) {
                return null;
            }
            if (data instanceof Double) {
                return (Double) data;
            }
            if (data instanceof Float) {
                return ((Float) data).doubleValue();
            }
            if (data instanceof Long) {
                return ((Long) data).doubleValue();
            }
            if (data instanceof Integer) {
                return ((Integer) data).doubleValue();
            }
            if (data instanceof Byte) {
                return ((Byte) data).doubleValue();
            }
            return Double.parseDouble(data.toString());
        }

        @Override
        public Object defaultValue(){
            return null;
        }

        @Override
        public boolean isWrapping(Class<?> type){
            return type == double.class || type == Double.class;
        }
    }

    private static class DoubleValueConverter extends DoubleConverter{

        @Override
        public Object convert(Object data){
            Long value = (Long) super.convert(data);
            if (value == null) {
                return Long.valueOf(0);
            }
            return value;
        }

        @Override
        public Object defaultValue(){
            return (double) 0;
        }
    }

    private static class FloatConverter implements Converter{

        @Override
        public Object convert(Object data){
            if (data == null) {
                return null;
            }
            if (data instanceof Float) {
                return (Float) data;
            }
            if (data instanceof Long) {
                return ((Long) data).floatValue();
            }
            if (data instanceof Integer) {
                return ((Integer) data).floatValue();
            }
            if (data instanceof Byte) {
                return ((Byte) data).floatValue();
            }
            return Float.parseFloat(data.toString());
        }

        @Override
        public Object defaultValue(){
            return null;
        }

        @Override
        public boolean isWrapping(Class<?> type){
            return type == float.class || type == Float.class;
        }
    }

    private static class FloatValueConverter extends FloatConverter{
        private static final Float DEFAULT_FLOAT = Float.valueOf(0);

        @Override
        public Object convert(Object data){
            Float value = (Float) super.convert(data);
            if (value == null) {
                return DEFAULT_FLOAT;
            }
            return value;
        }

        @Override
        public Object defaultValue(){
            return (float) 0;
        }
    }
}
