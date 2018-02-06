package org.hedwig.sql.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.hedwig.sql.transaction.Transaction;

public abstract class DaoSupport {

    final protected <T> T query(String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
    	Connection conn = Transaction.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        return queryRunner.query(conn, sql, rsh, params);
    }

    final protected <T> T query(String sql, ResultSetHandler<T> rsh) throws SQLException {
    	Connection conn = Transaction.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        return queryRunner.query(conn, sql, rsh);
    }

    final protected int update(String sql) throws SQLException {
    	Connection conn = Transaction.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        return queryRunner.update(conn, sql);
    }

    final protected int update(String sql, Object... params) throws SQLException {
    	Connection conn = Transaction.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        return queryRunner.update(conn, sql, params);
    }

    final protected <T> T insert(String sql, ResultSetHandler<T> rsh) throws SQLException {
    	Connection conn = Transaction.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        return queryRunner.insert(conn, sql, rsh);
    }

    final protected <T> T insert(String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
    	Connection conn = Transaction.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        return queryRunner.insert(conn, sql, rsh, params);
    }

    final protected <T> T insertBatch(String sql, ResultSetHandler<T> rsh, Object[][] params) throws SQLException {
    	Connection conn = Transaction.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        return queryRunner.insertBatch(conn, sql, rsh, params);
    }

}
