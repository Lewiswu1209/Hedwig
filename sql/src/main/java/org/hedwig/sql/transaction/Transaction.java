package org.hedwig.sql.transaction;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

public class Transaction {
	
	private Transaction(){
	}

	private static javax.sql.DataSource dataSource = null;
	private static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();

    public static javax.sql.DataSource getDataSource() {
		return dataSource;
	}

	public static void setDataSource(javax.sql.DataSource dataSource) {
		Transaction.dataSource = dataSource;
	}

	public static void begin() throws SQLException {
        Connection conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        connectionThreadLocal.set(conn);
    }

    public static void rollbackAndClose() throws SQLException {
        Connection conn = connectionThreadLocal.get();
        DbUtils.rollbackAndClose(conn);
        connectionThreadLocal.remove();
    }

    public static void commitAndClose() throws SQLException {
        Connection conn = connectionThreadLocal.get();
        DbUtils.commitAndClose(conn);
        connectionThreadLocal.remove();
    }
    
    public static void rollbackAndCloseQuietly() {
        Connection conn = connectionThreadLocal.get();
        DbUtils.rollbackAndCloseQuietly(conn);
        connectionThreadLocal.remove();
    }

    public static void commitAndCloseQuietly() {
        Connection conn = connectionThreadLocal.get();
        DbUtils.commitAndCloseQuietly(conn);
        connectionThreadLocal.remove();
    }
    
    public static void closeQuietly() {
    	Connection conn = connectionThreadLocal.get();
    	DbUtils.closeQuietly(conn);
    	connectionThreadLocal.remove();
    }

    public static Connection getConnection() {
        return connectionThreadLocal.get();
    }
}
