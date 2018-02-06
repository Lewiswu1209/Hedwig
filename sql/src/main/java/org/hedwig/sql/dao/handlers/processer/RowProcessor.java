package org.hedwig.sql.dao.handlers.processer;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.Converter;
import org.hedwig.beanutils.BeanUtils;
import org.hedwig.beanutils.ConvertUtils;
import org.hedwig.reflect.ClassUtil;

public class RowProcessor implements org.apache.commons.dbutils.RowProcessor {

	public RowProcessor() {
	}

    public RowProcessor(Converter converter, Class<?> clazz) {
    	ConvertUtils.register(converter, clazz);
	}

	public Object[] toArray(ResultSet rs) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        int cols = meta.getColumnCount();
        Object[] result = new Object[cols];

        for (int i = 0; i < cols; i++) {
            result[i] = rs.getObject(i + 1);
        }

        return result;
	}

	public <T> T toBean(ResultSet rs, Class<? extends T> type) throws SQLException {
		T bean = ClassUtil.newInstance(type);
		Map<String, Object> properties = this.toMap(rs);
		try {
			BeanUtils.populate(bean, properties);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return bean;
	}

	public <T> List<T> toBeanList(ResultSet rs, Class<? extends T> type) throws SQLException {
		List<T> rows = new ArrayList<T>();
        while (rs.next()) {
            rows.add(this.toBean(rs, type));
        }
		return rows;
	}

	public Map<String, Object> toMap(ResultSet rs) throws SQLException {
        Map<String, Object> result = new CaseInsensitiveHashMap();
        ResultSetMetaData rsmd = rs.getMetaData();
        int cols = rsmd.getColumnCount();

        for (int i = 1; i <= cols; i++) {
            String columnName = rsmd.getColumnLabel(i);
            if (null == columnName || 0 == columnName.length()) {
              columnName = rsmd.getColumnName(i);
            }
            result.put(columnName, rs.getObject(i));
        }

        return result;
	}

	/**
	 * A Map that converts all keys to lowercase Strings for case insensitive
	 * lookups.  This is needed for the toMap() implementation because
	 * databases don't consistently handle the casing of column names.
	 *
	 * <p>The keys are stored as they are given [BUG #DBUTILS-34], so we maintain
	 * an internal mapping from lowercase keys to the real keys in order to
	 * achieve the case insensitive lookup.
	 *
	 * <p>Note: This implementation does not allow {@code null}
	 * for key, whereas {@link LinkedHashMap} does, because of the code:
	 * <pre>
	 * key.toString().toLowerCase()
	 * </pre>
	 */
	private static class CaseInsensitiveHashMap extends LinkedHashMap<String, Object> {
	    /**
	     * The internal mapping from lowercase keys to the real keys.
	     *
	     * <p>
	     * Any query operation using the key
	     * ({@link #get(Object)}, {@link #containsKey(Object)})
	     * is done in three steps:
	     * <ul>
	     * <li>convert the parameter key to lower case</li>
	     * <li>get the actual key that corresponds to the lower case key</li>
	     * <li>query the map with the actual key</li>
	     * </ul>
	     * </p>
	     */
	    private final Map<String, String> lowerCaseMap = new HashMap<String, String>();
	
	    /**
	     * Required for serialization support.
	     *
	     * @see java.io.Serializable
	     */
	    private static final long serialVersionUID = -2848100435296897392L;
	
	    /** {@inheritDoc} */
	    @Override
	    public boolean containsKey(Object key) {
	        Object realKey = lowerCaseMap.get(key.toString().toLowerCase(Locale.ENGLISH));
	        return super.containsKey(realKey);
	        // Possible optimisation here:
	        // Since the lowerCaseMap contains a mapping for all the keys,
	        // we could just do this:
	        // return lowerCaseMap.containsKey(key.toString().toLowerCase());
	    }
	
	    /** {@inheritDoc} */
	    @Override
	    public Object get(Object key) {
	        Object realKey = lowerCaseMap.get(key.toString().toLowerCase(Locale.ENGLISH));
	        return super.get(realKey);
	    }
	
	    /** {@inheritDoc} */
	    @Override
	    public Object put(String key, Object value) {
	        /*
	         * In order to keep the map and lowerCaseMap synchronized,
	         * we have to remove the old mapping before putting the
	         * new one. Indeed, oldKey and key are not necessaliry equals.
	         * (That's why we call super.remove(oldKey) and not just
	         * super.put(key, value))
	         */
	        Object oldKey = lowerCaseMap.put(key.toLowerCase(Locale.ENGLISH), key);
	        Object oldValue = super.remove(oldKey);
	        super.put(key, value);
	        return oldValue;
	    }
	
	    /** {@inheritDoc} */
	    @Override
	    public void putAll(Map<? extends String, ?> m) {
	        for (Map.Entry<? extends String, ?> entry : m.entrySet()) {
	            String key = entry.getKey();
	            Object value = entry.getValue();
	            this.put(key, value);
	        }
	    }
	
	    /** {@inheritDoc} */
	    @Override
	    public Object remove(Object key) {
	        Object realKey = lowerCaseMap.remove(key.toString().toLowerCase(Locale.ENGLISH));
	        return super.remove(realKey);
	    }
	}
}
