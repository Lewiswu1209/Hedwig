package org.hedwig.sql.session;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

public class Transaction {
	
	private Connection conn;

	void setConn(Connection conn) {
		this.conn = conn;
	}

	private Connection getConn() throws SQLException {
		return conn;
	}

    final protected <T> T query(String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
    	Connection conn = this.getConn();
        QueryRunner queryRunner = new QueryRunner();
        return queryRunner.query(conn, sql, rsh, params);
    }

    final protected <T> T query(String sql, ResultSetHandler<T> rsh) throws SQLException {
    	Connection conn = this.getConn();
        QueryRunner queryRunner = new QueryRunner();
        return queryRunner.query(conn, sql, rsh);
    }

    final protected int update(String sql) throws SQLException {
    	Connection conn = this.getConn();
        QueryRunner queryRunner = new QueryRunner();
        return queryRunner.update(conn, sql);
    }

    final protected int update(String sql, Object... params) throws SQLException {
    	Connection conn = this.getConn();
        QueryRunner queryRunner = new QueryRunner();
        return queryRunner.update(conn, sql, params);
    }

    final protected <T> T insert(String sql, ResultSetHandler<T> rsh) throws SQLException {
    	Connection conn = this.getConn();
        QueryRunner queryRunner = new QueryRunner();
        return queryRunner.insert(conn, sql, rsh);
    }

    final protected <T> T insert(String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
    	Connection conn = this.getConn();
        QueryRunner queryRunner = new QueryRunner();
        return queryRunner.insert(conn, sql, rsh, params);
    }

    final protected <T> T insertBatch(String sql, ResultSetHandler<T> rsh, Object[][] params) throws SQLException {
    	Connection conn = this.getConn();
        QueryRunner queryRunner = new QueryRunner();
        return queryRunner.insertBatch(conn, sql, rsh, params);
    }
    
	public void rollback() throws SQLException {
		DbUtils.rollback(conn);
	}

    public void commit() throws SQLException {
    	conn.commit();
    }

}
