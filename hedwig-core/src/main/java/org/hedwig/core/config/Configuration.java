package org.hedwig.core.config;

import java.nio.charset.Charset;
import java.util.HashMap;

public final class Configuration {
	
	public static final String KEY_CONTENT_PATH = "CONTENT_PATH";
	public static final String KEY_WEB_ROOT = "WEB_ROOT";
	public static final String KEY_CHARSET = "CHARSET";
	public static final String KEY_ACTION_EXTENSION = "ACTION_EXTENSION";
	public static final String KEY_DATABASE_URL = "DATABASE_URL";
	public static final String KEY_DATABASE_USER = "DATABASE_USER";
	public static final String KEY_DATABASE_PASSWORD = "DATABASE_PASSWORD";
	
    protected static HashMap<String, Object> data = new HashMap<String, Object>();

    public static void set(String key, Object value){
        data.put(key, value);
    }

    public static String getString(String key){
        return get(key);
    }
    public static Boolean getBoolean(String key){
    	return get(key);
    }
    public static Integer getInteger(String key){
        return get(key);
    }
    public static Long getLong(String key){
        return get(key);
    }
    public static Double getDouble(String key){
        return get(key);
    }

    @SuppressWarnings("unchecked")
	protected static <T> T get(String key){
        return (T)data.get(key);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getOrDefault(String key, T defaultValue){
        Object value = data.get(key);
        if(value == null){
            return defaultValue;
        }else{
            return (T)value;
        }
    }

	public static String getContextPath() {
		return getString(KEY_CONTENT_PATH);
	}

	public static void setContextPath(String contextPath) {
		set(KEY_CONTENT_PATH, contextPath);
	}

	public static String getWebRoot() {
		return getString(KEY_WEB_ROOT);
	}

	public static void setWebRoot(String webRoot) {
		set(KEY_WEB_ROOT, webRoot);
	}

	public static Charset getCharset() {
		return get(KEY_CHARSET);
	}

	public static void setCharset(Charset charset) {
		set(KEY_CHARSET, charset);
	}
	
	public static String getDatabaseUrl() {
		return get(KEY_DATABASE_URL);
	}

	public static void setDatabaseUrl(String url) {
		set(KEY_DATABASE_URL, url);
	}
	
	public static String getDatabaseUser() {
		return get(KEY_DATABASE_USER);
	}

	public static void setDatabaseUser(String username) {
		set(KEY_DATABASE_USER, username);
	}
	
	public static String getDatabasePassword() {
		return get(KEY_DATABASE_PASSWORD);
	}

	public static void setDatabasePassword(String password) {
		set(KEY_DATABASE_PASSWORD, password);
	}

	public static String getActionExtension() {
		return getString(KEY_ACTION_EXTENSION);
	}

	public static void setActionExtension(String actionExtension) {
		if (!actionExtension.startsWith(".")) {
			actionExtension = String.format(".%s", actionExtension);
		}
		set(KEY_ACTION_EXTENSION, actionExtension);
	}

}
