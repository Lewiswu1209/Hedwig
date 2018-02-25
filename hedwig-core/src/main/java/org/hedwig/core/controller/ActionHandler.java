package org.hedwig.core.controller;

import java.lang.reflect.Method;

import org.hedwig.core.interceptor.Interceptor;

public final class ActionHandler {

	public ActionHandler(String actionKey, Class<? extends ActionSupport> controllerClass, Method method, Class<? extends Interceptor>[] interceptors) {
		this.actionKey = actionKey;
		this.controllerClass = controllerClass;
		this.method = method;
		this.interceptors = interceptors;
	}

	private final Class<? extends ActionSupport> controllerClass;
	
	public Class<? extends ActionSupport> getControllerClass() {
		return controllerClass;
	}

	private final String actionKey;
	
	public String getActionKey() {
		return actionKey;
	}

	private final Method method;
	
	public Method getMethod() {
		return method;
	}

	private final Class<? extends Interceptor>[] interceptors;

	public Class<? extends Interceptor>[] getInterceptors() {
		return this.interceptors;
	}
}
