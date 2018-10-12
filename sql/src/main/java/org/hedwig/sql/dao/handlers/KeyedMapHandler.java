package org.hedwig.sql.dao.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.handlers.AbstractKeyedHandler;

public class KeyedMapHandler<K, V> extends AbstractKeyedHandler<K, V> {
	
	private String keyColumnLabel = null;
	
	private int keyColumnNumber = -1;
	
	private String valueColumnLabel = null;
	
	private int valueColumnNumber = -1;

	public KeyedMapHandler(int keyColumnNumber, int valueColumnNumber) {
		super();
		this.keyColumnNumber = keyColumnNumber;
		this.valueColumnNumber = valueColumnNumber;
		this.keyColumnLabel = null;
		this.valueColumnLabel = null;
	}
	
	public KeyedMapHandler(String keyColumnLabel, String valueColumnLabel) {
		super();
		this.keyColumnLabel = keyColumnLabel;
		this.valueColumnLabel = valueColumnLabel;
		this.keyColumnNumber = -1;
		this.valueColumnNumber = -1;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected K createKey(ResultSet rs) throws SQLException {
		if (keyColumnLabel!=null) {
			return (K) rs.getObject(keyColumnLabel);
		} else {
			return (K) rs.getObject(keyColumnNumber);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected V createRow(ResultSet rs) throws SQLException {
		if (valueColumnLabel!=null) {
			return (V) rs.getObject(valueColumnLabel);
		} else {
			return (V) rs.getObject(valueColumnNumber);
		}
	}

}
