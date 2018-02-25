package org.hedwig.core.context;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class Parameters implements Map<String, String[]>{
	
	private Map<String, String[]> parameters;

	public Parameters(Map<String, String[]> parameters) {
		this.parameters = parameters;
	}
	
	public String getString(String name) {
		return getString(name, null);
	}
	
	public String getString(String name, String defaultValue) {
		String value = defaultValue;
		
		String[] paras = parameters.get(name);
		if (paras != null && paras.length > 0){
			value = paras[0];
		}
		
		return value;
	}
	
	public int getInt(String name) {
		return getInt(name, 0);
	}
	
	public int getInt(String name, int defaultValue) {
		int value = defaultValue;
		
		String[] paras = parameters.get(name);
		if (paras != null && paras.length > 0){
			value = Integer.valueOf(paras[0]);
		}
		
		return value;
	}
	
	public long getLong(String name) {
		return getLong(name, 0L);
	}
	
	public long getLong(String name, long defaultValue) {
		long value = defaultValue;
		
		String[] paras = parameters.get(name);
		if (paras != null && paras.length > 0){
			value = Long.valueOf(paras[0]);
		}
		
		return value;
	}
	
	public float getFloat(String name) {
		return getFloat(name, 0);
	}
	
	public float getFloat(String name, float defaultValue) {
		float value = defaultValue;
		
		String[] paras = parameters.get(name);
		if (paras != null && paras.length > 0){
			value = Float.valueOf(paras[0]);
		}
		
		return value;
	}
	
	public double getDouble(String name) {
		return getDouble(name, 0);
	}
	
	public double getDouble(String name, double defaultValue) {
		double value = defaultValue;
		
		String[] paras = parameters.get(name);
		if (paras != null && paras.length > 0){
			value = Double.valueOf(paras[0]);
		}
		
		return value;
	}
	
	public boolean getBoolean(String name) {
		return getBoolean(name, false);
	}
	
	public boolean getBoolean(String name, boolean defaultValue) {
		boolean value = defaultValue;
		
		String[] paras = parameters.get(name);
		if (paras != null && paras.length > 0){
			value = Boolean.valueOf(paras[0]);
		}
		
		return value;
	}

	public Map<String, String> getParameterMap() {
		Map<String, String> map = new HashMap<String, String>();
		for (String key : this.parameters.keySet()) {
			String[] values = parameters.get(key);
			String value = null;
			if (values != null && values.length > 0){
				value = values[0];
			}
			map.put(key, value);
		}
		return map;
	}

	public void clear() {
		parameters.clear();
	}

	public boolean containsKey(Object key) {
		return parameters.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return parameters.containsValue(value);
	}

	public Set<Entry<String, String[]>> entrySet() {
		return parameters.entrySet();
	}

	public String[] get(Object key) {
		return parameters.get(key);
	}

	public boolean isEmpty() {
		return parameters.isEmpty();
	}

	public Set<String> keySet() {
		return parameters.keySet();
	}

	public String[] put(String key, String[] value) {
		return parameters.put(key, value);
	}

	public void putAll(Map<? extends String, ? extends String[]> m) {
		parameters.putAll(m);
	}

	public String[] remove(Object key) {
		return parameters.remove(key);
	}

	public int size() {
		return parameters.size();
	}

	public Collection<String[]> values() {
		return parameters.values();
	}

}
