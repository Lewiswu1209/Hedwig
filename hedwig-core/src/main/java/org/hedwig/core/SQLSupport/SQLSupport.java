package org.hedwig.core.SQLSupport;

import org.hedwig.core.dispatcher.InterceptorChain;
import org.hedwig.core.interceptor.Interceptor;
import org.hedwig.sql.transaction.Transaction;

public class SQLSupport implements Interceptor {

	public void intercept(InterceptorChain chain) throws Exception {
		Transaction.begin();
		try {
			chain.invoke();
		} catch (RuntimeException e) {
			Transaction.rollbackAndCloseQuietly();
			throw e;
		} finally {
			Transaction.commitAndClose();
		}
	}

}
