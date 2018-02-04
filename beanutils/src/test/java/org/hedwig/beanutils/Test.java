package org.hedwig.beanutils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

import org.hedwig.beanutils.converter.DateConverter;

/**
 * Unit test for simple App.
 */
public class Test {

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    	DateConverter converter = new DateConverter("yyyy-MM-dd");
    	TestBeanClass a = new TestBeanClass();
    	ConvertUtils.register(converter, Date.class);
    	Map<String, String> b = BeanUtils.describe(a);
    	System.out.println(b);
    }
}
