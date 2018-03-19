package org.hedwig.core.SQLSupport;

import java.sql.SQLException;

import org.hedwig.core.dispatcher.InterceptorChain;
import org.hedwig.core.interceptor.Interceptor;
import org.hedwig.sql.session.Session;
import org.hedwig.sql.session.SessionFactory;
/*
public class SQLSupport implements Interceptor {
	
	public static SessionFactory sessionFactory = null;

	public void intercept(InterceptorChain chain) throws SQLException {
		try {
			Session.beginTransaction();
			chain.invoke();
		} catch (RuntimeException e) {
			Session.rollbackAndCloseQuietly();
			throw e;
		} finally {
			Session.commitAndClose();
		}
	}

}
*/