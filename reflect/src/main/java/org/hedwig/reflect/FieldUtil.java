package org.hedwig.reflect;

public final class FieldUtil {
/*
	@SuppressWarnings("unchecked")
	public static <T> T getFieldValue(Field field, Object obj) {
		Object rs = null;
		try {
			PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
			Method readMethod = pd.getReadMethod();
			rs = readMethod.invoke(obj);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return (T)rs;
	}

	public static Method getReadMethod(Field field, Class<?> objClass) {
		Method readMethod = null;
		try {
			PropertyDescriptor pd = new PropertyDescriptor(field.getName(), objClass);
			readMethod = pd.getReadMethod();
		} catch (IntrospectionException e) {
		}
		return readMethod;
	}

	public static Method getWriteMethod(Field field, Class<?> objClass) {
		Method writeMethod = null;
		try {
			PropertyDescriptor pd = new PropertyDescriptor(field.getName(), objClass);
			writeMethod = pd.getWriteMethod();
		} catch (IntrospectionException e) {
		}
		return writeMethod;
	}

	public static void setFieldValue(Field field, Object obj, Object arg) {
		try {
			PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
			Method writeMethod = pd.getWriteMethod();
			writeMethod.invoke(obj, arg);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
*/
	private FieldUtil(){}
}
