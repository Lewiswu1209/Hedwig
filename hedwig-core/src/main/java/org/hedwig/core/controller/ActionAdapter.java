package org.hedwig.core.controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.hedwig.core.config.Configuration;
import org.hedwig.core.context.Context;
import org.hedwig.core.interceptor.Interceptor;
import org.hedwig.core.interceptor.Interceptors;

public final class ActionAdapter {

	private static final Map<String, ActionHandler> mapping = new HashMap<String, ActionHandler>();

	public static void add(String controllerPath, Class<? extends ActionSupport> c) {
		controllerPath = fixControllerPath(controllerPath);
		
		for(Method method : c.getDeclaredMethods()) {
			Class<?>[] methodPara = method.getParameterTypes();
			if (method.getReturnType().equals(void.class) && 1 == methodPara.length && methodPara[0].equals(Context.class) ) {
				String actionKey = String.format("%s%s%s", controllerPath, method.getName(), Configuration.getActionExtension());

				Class<? extends Interceptor>[] inters = null;
				Interceptors interceptorAnnotation = method.getAnnotation(Interceptors.class);
				if (interceptorAnnotation != null) {
					inters = interceptorAnnotation.value();
				}
				ActionHandler action = new ActionHandler(actionKey, c, method, inters);
				mapping.put(actionKey, action);
			}
		}
	}
	
	public static ActionHandler getHandler(String path) {
		return mapping.get(path);
	}

	private static String fixControllerPath(String path) {
		if (!path.endsWith("/")) {
			path += "/";
		}
		return path;
	}
}
