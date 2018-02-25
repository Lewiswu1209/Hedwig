package org.hedwig.core.interceptor;

import org.hedwig.core.dispatcher.InterceptorChain;


/**
 * Interceptor.
 * @author Lewis
 * @version 1.0
 */
public interface Interceptor {
	void intercept(InterceptorChain chain) throws Exception;
}
