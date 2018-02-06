package org.hedwig.sql.source;

import org.sqlite.SQLiteDataSource;

public class DataSourceFactory {
	
	private static final String PREFIX_SQLITE = "jdbc:sqlite:";

	private String url = null;
	
	private String username = null;
	
	private String password = null;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public javax.sql.DataSource getDataSource() {
		
		javax.sql.DataSource ds = null;
		
		if (url.startsWith(PREFIX_SQLITE)) {
			SQLiteDataSource sqlite = new SQLiteDataSource();
			sqlite.setUrl(url);
			
			ds = sqlite;
		}

		return ds;
	}

	public static javax.sql.DataSource getDataSource(String url, String username, String password) {
		DataSourceFactory dsf = new DataSourceFactory();
		dsf.setUrl(url);
		dsf.setUsername(username);
		dsf.setPassword(password);
		return dsf.getDataSource();
	}

}
