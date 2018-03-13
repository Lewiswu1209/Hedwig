package org.hedwig.sql.session;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class SessionFactory {
	
	private DataSource dataSource;
	
	private ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();

	public Session getLocalSession() throws SQLException {
		Session session = threadLocal.get();
		if (session == null) {
			Connection conn = dataSource.getConnection();
			session = new Session(conn);
			session.setSessionFactory(this);
			threadLocal.set(session);
		}
		return session;
	}
	
	public Session getSession() throws SQLException {
		Connection conn = dataSource.getConnection();
		Session session = new Session(conn);
		return session;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
