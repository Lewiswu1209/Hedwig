package org.hedwig.sql.session;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

public class Session {
	
	public Session(Connection conn) {
		super();
		this.conn = conn;
	}
	
	private SessionFactory sessionFactory = null;

	SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Connection conn = null;

	Connection getConn() {
		return conn;
	}

	void setConn(Connection conn) {
		this.conn = conn;
	}

	public Transaction beginTransaction() throws SQLException {
        conn.setAutoCommit(false);
        Transaction tx = new Transaction();
        tx.setConn(conn);
        return tx;
    }

    public void close() throws SQLException {
    	DbUtils.close(conn);
    }

	public void closeQuietly() {
		DbUtils.closeQuietly(conn);
	}

}
