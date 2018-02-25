package org.hedwig.core.dispatcher;

import java.lang.reflect.InvocationTargetException;

import org.hedwig.core.context.Context;
import org.hedwig.core.controller.ActionHandler;
import org.hedwig.core.controller.ActionSupport;
import org.hedwig.core.interceptor.Interceptor;
import org.hedwig.reflect.ClassUtil;

/**
 * ActionInvocation invoke the action
 * @author Lewis
 * @version 1.1
 */
public final class InterceptorChain {
	private Interceptor[] inters;
	private ActionHandler action;
	private Context context;
	private ActionSupport controller;

	private int index = 0;

	public InterceptorChain(ActionHandler action, Context context) {
		this.context = context;
		this.action = action;
		this.inters = createInterceptor();
		
		this.controller = context.getAttribute(action.getControllerClass().getName(), Context.SESSION_SCOPE);

		if (this.controller == null) {
			this.controller = ClassUtil.newInstance(action.getControllerClass());
		}
	}

	/**
	 * Invoke the action.
	 * @throws RuntimeException 
	 */
	public void invoke() {
		try {
			if (index < inters.length) {
				inters[index++].intercept(this);
			} else if (index++ == inters.length) {
				action.getMethod().invoke(controller, context);
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getTargetException().getMessage(), e.getTargetException());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public Context getContext() {
		return context;
	}

	private Interceptor[] createInterceptor() {
		Class<? extends Interceptor>[] interceptors = action.getInterceptors();
		Interceptor[] interInstances = null;
		if (interceptors != null) {
			interInstances = new Interceptor[interceptors.length];
			for (int ii = 0; ii < interceptors.length; ii++) {
				interInstances[ii] = ClassUtil.newInstance(interceptors[ii]);
			}
		} else {
			interInstances = new Interceptor[0];
		}
		return interInstances;
	}

}
