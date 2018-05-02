package io.bumo.sdk.core.utils;
import java.lang.reflect.Field;

import java.lang.reflect.Method;

import java.util.Date;

import java.util.HashMap;

import java.util.Map;





@SuppressWarnings("all")

public class ReflectUtil {

	

	/** The Constant SETTER. */

	public static final String SETTER = "set";

	

	/** The Constant GETTER. */

	public static final String GETTER = "get";

	

	/**

	 * Inits the bean val.

	 *

	 * @param <T> the generic type

	 * @param cls the cls

	 * @return the T

	 */

	public static <T> T initBeanVal(Class<T> cls){

		

		T t = null;

		try {

			t = cls.newInstance();

		} catch (InstantiationException e1) {

			e1.printStackTrace();

			return null;

		} catch (IllegalAccessException e1) {

			e1.printStackTrace();

			return null;

		}finally{

			

		}

		Method[] methods = cls.getDeclaredMethods();

		Field[] fields = cls.getDeclaredFields();

		for (Field field : fields) {

			try {

				

				String setterMethodName = concatSetter(field.getName());

				if (!checkSetter(methods, setterMethodName)) {

					continue;

				}

				String fieldType = field.getType().getSimpleName();

				

				Method setterMethod = null;

				if (String.class.getSimpleName().equals(fieldType)) {

					setterMethod = cls.getMethod(setterMethodName, new Class[]{String.class});

					setterMethod.invoke(t, "");

				} else if (Date.class.getSimpleName().equals(fieldType)) {

					setterMethod = cls.getMethod(setterMethodName, new Class[]{Date.class});

					setterMethod.invoke(t, "");

				} else if (Integer.class.getSimpleName().equals(fieldType)	|| "int".equals(fieldType)) {

					setterMethod = cls.getMethod(setterMethodName, new Class[]{int.class});

					setterMethod.invoke(t, 0);

				} else if (Long.class.getSimpleName().equalsIgnoreCase(fieldType)) {

					setterMethod = cls.getMethod(setterMethodName, new Class[]{long.class});

					setterMethod.invoke(t, 0L);

				} else if (Float.class.getSimpleName().equalsIgnoreCase(fieldType)) {

					setterMethod = cls.getMethod(setterMethodName, new Class[]{float.class});

					setterMethod.invoke(t, 0.0f);

				} else if (Double.class.getSimpleName().equalsIgnoreCase(fieldType)) {

					setterMethod = cls.getMethod(setterMethodName, new Class[]{double.class});

					setterMethod.invoke(t, 0.0d);

				} else if (Boolean.class.getSimpleName().equalsIgnoreCase(fieldType)) {

					setterMethod = cls.getMethod(setterMethodName, new Class[]{boolean.class});

					setterMethod.invoke(t, false);

					

				} else {

					System.out.println("not support type" + fieldType);

				}

			} catch (Exception e) {

				return null;

			}

		}

		

		return t;

	}

	

	/**

	 * MAP for the relationship between the attribute and the value of the Bean.

	 *

	 * @param bean the bean

	 * @return Map

	 */

	public static Map<String, String> getFieldValueMap(Object bean) {

		Class<?> cls = bean.getClass();

		Map<String, String> valueMap = new HashMap<String, String>();

		Method[] methods = cls.getDeclaredMethods();

		Field[] fields = cls.getDeclaredFields();

		for (Field field : fields) {

			try {

				String fieldType = field.getType().getSimpleName();

				String getter = concatGetter(field.getName());

				if (!checkGetter(methods, getter)) {

					continue;

				}

				Method method = cls.getMethod(getter, new Class[] {});

				Object retVal = method.invoke(bean, new Object[] {});

				String result = null;

				if (Date.class.getSimpleName().equals(fieldType)) {

					result = DateUtil.toStringLocaleUK((Date) retVal);

				} else {

					if (null != retVal) {

						result = String.valueOf(retVal);

					}

				}

				valueMap.put(field.getName(), result);

			} catch (Exception e) {

				continue;

			}

		}

		return valueMap;

	}



	/**

	 * The value of the set property to t.

	 *

	 * @param <T> the generic type

	 * @param t the t

	 * @param kvs the kvs

	 * @return the T

	 */

	public static<T> T setFieldValue(T t, Map<String, String> kvs) {

		Class<?> cls = t.getClass();

		Method[] methods = cls.getDeclaredMethods();

		Field[] fields = cls.getDeclaredFields();



		for (Field field : fields) {

			try {

				String fieldSetName = concatSetter(field.getName());

				if (!checkSetter(methods, fieldSetName)) {

					continue;

				}

				Method method = cls.getMethod(fieldSetName,

						field.getType());

				String  fieldName = field.getName();

				String value = kvs.get(fieldName);

				if (null != value && !"".equals(value)) {

					String fieldType = field.getType().getSimpleName();

					if (String.class.getSimpleName().equals(fieldType)) {

						method.invoke(t, value);

					} else if (Date.class.getSimpleName().equals(fieldType)) {

						Date temp = DateUtil.toDateLocaleUK(value);

						method.invoke(t, temp);

					} else if (Integer.class.getSimpleName().equals(fieldType)	|| "int".equals(fieldType)) {

						Integer intval = Integer.parseInt(value);

						method.invoke(t, intval);

					} else if (Long.class.getSimpleName().equalsIgnoreCase(fieldType)) {

						Long temp = Long.parseLong(value);

						method.invoke(t, temp);

					} else if (Float.class.getSimpleName().equalsIgnoreCase(fieldType)) {

						float temp = Float.parseFloat(value);

						method.invoke(t, temp);

					} else if (Double.class.getSimpleName().equalsIgnoreCase(fieldType)) {

						Double temp = Double.parseDouble(value);

						method.invoke(t, temp);

					} else if (Boolean.class.getSimpleName().equalsIgnoreCase(fieldType)) {

						Boolean temp = Boolean.parseBoolean(value);

						method.invoke(t, temp);

					} else {

						System.out.println("not support type" + fieldType);

					}

				}

			} catch (Exception e) {

				continue;

			}

		}

		return t;

	}





	/**

	 * Set method to determine whether there is an attribute.

	 *

	 * @param methods the methods

	 * @param fieldSetMet the field set met

	 * @return boolean

	 */

	public static boolean checkSetter(Method[] methods, String fieldSetMet) {

		for (Method met : methods) {

			if (fieldSetMet.equals(met.getName())) {

				return true;

			}

		}

		return false;

	}



	/**

	 * A get method to determine whether there is an attribute

	 *

	 * @param methods the methods

	 * @param fieldGetMet the field get met

	 * @return boolean

	 */

	public static boolean checkGetter(Method[] methods, String fieldGetMet) {

		for (Method met : methods) {

			if (fieldGetMet.equals(met.getName())) {

				return true;

			}

		}

		return false;

	}



	/**

	 * Get method for splicing a certain attribute

	 *

	 * @param fieldName the field name

	 * @return String

	 */

	public static String concatGetter(String fieldName) {

		if (null == fieldName || "".equals(fieldName)) {

			return null;

		}

		int startIndex = 0;

		if (fieldName.charAt(0) == '_')

			startIndex = 1;

		return GETTER	+ fieldName.substring(startIndex, startIndex + 1).toUpperCase()	+ fieldName.substring(startIndex + 1);

	}



	/**

	 * An attribute set method

	 *

	 * @param fieldName the field name

	 * @return String

	 */

	public static String concatSetter(String fieldName) {

		if (null == fieldName || "".equals(fieldName)) {

			return null;

		}

		int startIndex = 0;

		if (fieldName.charAt(0) == '_')

			startIndex = 1;

		return SETTER	+ fieldName.substring(startIndex, startIndex + 1).toUpperCase()	+ fieldName.substring(startIndex + 1);

	}



	/**

	 * Get the store's key name

	 *

	 * @param fieldName the field name

	 * @return the string

	 */

	public static String parKeyName(String fieldName) {

		String getter = concatGetter(fieldName);

		if (getter != null && getter.trim() != ""	&& getter.length() > 3) {

			return getter.substring(3);

		}

		return getter;

	}



}