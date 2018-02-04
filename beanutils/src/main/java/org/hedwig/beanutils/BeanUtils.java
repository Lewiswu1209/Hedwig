package org.hedwig.beanutils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.beanutils.ContextClassLoaderLocal;

public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {
	
	/**
     * Contains <code>BeanUtilsBean</code> instances indexed by context classloader.
     */
    private static final ContextClassLoaderLocal<BeanUtilsBean2>
            BEANS_BY_CLASSLOADER = new ContextClassLoaderLocal<BeanUtilsBean2>() {
                        @Override
                        protected BeanUtilsBean2 initialValue() {
                            return new BeanUtilsBean2();
                        }
                    };

    /**
     * Gets the instance which provides the functionality for {@link BeanUtils}.
     * This is a pseudo-singleton - an single instance is provided per (thread) context classloader.
     * This mechanism provides isolation for web apps deployed in the same container.
     *
     * @return The (pseudo-singleton) BeanUtils bean instance
     */
    protected static BeanUtilsBean2 getInstance() {
        return BEANS_BY_CLASSLOADER.get();
    }

    /**
     * <p>Return the entire set of properties for which the specified bean
     * provides a read method.</p>
     *
     * <p>For more details see <code>BeanUtilsBean</code>.</p>
     *
     * @param bean Bean whose properties are to be extracted
     * @return Map of property descriptors
     *
     * @throws IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @throws InvocationTargetException if the property accessor method
     *  throws an exception
     * @throws NoSuchMethodException if an accessor method for this
     *  property cannot be found
     * @see BeanUtilsBean#describe
     */
    public static Map<String, String> describe(final Object bean)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {

        return getInstance().describe(bean);
    }

}
