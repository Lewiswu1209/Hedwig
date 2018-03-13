package org.hedwig.sql.pool;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class Pool {
	
    /**
     * 数据库URL
     */
    public static final String URL = "URL";
    /**
     * 数据库驱动
     */
    public static final String DRIVER = "DRIVER";
    /**
     * 用户名
     */
    public static final String USERNAME = "USERNAME";
    /**
     * 密码
     */
    public static final String PASSWORD = "PASSWORD";
    /**
     * 初始化创建连接数
     */
    public static final String INITIALSIZE = "INITIALSIZE";
    /**
     * 连接池最大连接数
     */
    public static final String MAXACTIVE = "MAXACTIVE";
    /**
     * 最大空闲数，数据库连接的最大空闲时间。超过空闲时间，数据库连接将被释放。设为0表示无限制
     */
    public static final String MAXIDLE = "MAXIDLE";
    /**
     * 最小空闲连接:连接池中容许保持空闲状态的最小连接数量,低于这个数量将创建新的连接
     */
    public static final String MINIDLE = "MINIDLE";
    /**
     * 最大建立连接等待时间
     */
    public static final String MAXWAIT = "MAXWAIT";
    /**
     * 超过removeAbandonedTimeout时间后，是否进行没用连接（废弃）的回收（默认为false，调整为true)
     */
    public static final String REMOVEABANDONED = "REMOVEABANDONED";
    /**
     * 超过时间限制，回收没有用(废弃)的连接（默认为300秒，调整为180）
     */
    public static final String REMOVEABANDONEDTIMEOUT = "REMOVEABANDONEDTIMEOUT";
	
	public static DataSource getDataSource(PoolProperties properties) {
	     DataSource datasource = new DataSource();
	     datasource.setPoolProperties(properties);
	     
	     return datasource;
	}
	
	public static DataSource getDataSource(Properties properties) {
        String url = properties.getProperty(URL);
        String driver = properties.getProperty(DRIVER);
        String username = properties.getProperty(USERNAME);
        String password = properties.getProperty(PASSWORD);
        int initialSize = Integer.valueOf(properties.getProperty(INITIALSIZE));
        int maxActive = Integer.valueOf(properties.getProperty(MAXACTIVE));
        int maxIdle = Integer.valueOf(properties.getProperty(MAXIDLE));
        int minIdle = Integer.valueOf(properties.getProperty(MINIDLE));
        int maxWait = Integer.valueOf(properties.getProperty(MAXWAIT));
        Boolean removeAbandoned = Boolean.valueOf(properties.getProperty(REMOVEABANDONED));
        int removeAbandonedTimeout = Integer.valueOf(properties.getProperty(REMOVEABANDONEDTIMEOUT));

		PoolProperties poolProperties = new PoolProperties();
        poolProperties.setUrl(url);
        poolProperties.setDriverClassName(driver);
        poolProperties.setUsername(username);
        poolProperties.setPassword(password);
        poolProperties.setInitialSize(initialSize);
        poolProperties.setMaxActive(maxActive);
        poolProperties.setMaxIdle(maxIdle);
        poolProperties.setMinIdle(minIdle);
        poolProperties.setMaxWait(maxWait);
        poolProperties.setRemoveAbandoned(removeAbandoned);
        poolProperties.setRemoveAbandonedTimeout(removeAbandonedTimeout);

        DataSource dataSource = new DataSource();  
        dataSource.setPoolProperties(poolProperties);
        
        return dataSource;
	}
	
	public static DataSource getDataSource(String name) throws NamingException {
		Context initContext = new InitialContext();
		Context envContext = (Context) initContext.lookup("java:/comp/env");
		return (DataSource) envContext.lookup(name);
	}

}
