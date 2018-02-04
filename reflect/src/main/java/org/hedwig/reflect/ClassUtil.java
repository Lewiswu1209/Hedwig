package org.hedwig.reflect;

import org.hedwig.textutils.TextUtil;

public final class ClassUtil {

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(String className) {
		if (TextUtil.isblank(className))
			throw new RuntimeException("Class name can not be blank!");

		Object obj = null;
		try {
			obj = Class.forName(className).newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException("Can not create object of " + className + "!", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Can not create object of " + className + "!", e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Can not create object of " + className + "!", e);
		}
		return (T)obj;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<?> objectClass) {
		Object obj = null;
		try {
			obj = objectClass.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException("Can not create object of " + objectClass.getName() + "!", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Can not create object of " + objectClass.getName() + "!", e);
		}
		return (T)obj;
	}
	
	private ClassUtil(){}

}
