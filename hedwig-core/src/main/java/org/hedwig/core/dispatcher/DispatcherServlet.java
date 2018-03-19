package org.hedwig.core.dispatcher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.jdbc.pool.PoolProperties;
//import org.hedwig.core.SQLSupport.SQLSupport;
import org.hedwig.core.config.Configuration;
import org.hedwig.core.context.Context;
import org.hedwig.core.controller.ActionAdapter;
import org.hedwig.core.controller.ActionHandler;
import org.hedwig.core.scheduler.Scheduler;
import org.hedwig.core.view.AbstractView;
import org.hedwig.core.view.ViewFactroy;
import org.hedwig.sql.pool.Pool;
import org.hedwig.sql.session.SessionFactory;
import org.hedwig.textutils.Charsets;
import org.hedwig.textutils.TextUtil;

@MultipartConfig
public final class DispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = 4991193703469706104L;

	private static final Log logger = LogFactory.getLog(DispatcherServlet.class);

	@Override
	public void init() throws ServletException {
		Configuration.setContextPath(getServletContext().getContextPath());
		Configuration.setWebRoot(getServletContext().getRealPath("/"));
		Configuration.setCharset(Charsets.UTF_8);
		Configuration.setActionExtension("hdw");

		String configClassName = getInitParameter("configClass");
		try {
			if (!TextUtil.isblank(configClassName)) {
				Class.forName(configClassName);
			}
		} catch (ClassNotFoundException e) {
		}

		if (Configuration.getDatabaseUrl()!=null) {
			PoolProperties properties = new PoolProperties();
			properties.setUrl(Configuration.getDatabaseUrl());
			properties.setPassword(Configuration.getDatabaseUser());
			properties.setUsername(Configuration.getDatabaseUser());
			DataSource dataSource = Pool.getDataSource(properties);
			//SQLSupport.sessionFactory = new SessionFactory();
			//SQLSupport.sessionFactory.setDataSource(dataSource);
		}
		
		getServletContext().setAttribute("APP_ROOT", Configuration.getContextPath());
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)	throws ServletException, IOException {

		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;

		request.setCharacterEncoding(Configuration.getCharset().name());

		String target = request.getRequestURI().substring(Configuration.getContextPath().length());

		if (target.trim().endsWith("/") || target.trim().isEmpty()) {
			target += String.format("%s%s", "index", Configuration.getActionExtension());
		}

		Context context = new Context(request, response);
		ActionHandler actionHandler = ActionAdapter.getHandler(target);
		
		logger.info(" INFO: Executing target \"" + target + "\"");
		
		if (actionHandler != null) {
			try {
				new InterceptorChain(actionHandler, context).invoke();
			} catch (RuntimeException e) {
				logger.error(e.getMessage(), e);
				ViewFactroy.setErrorView(context, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		} else {
			ViewFactroy.setErrorView(context, HttpServletResponse.SC_NOT_FOUND);
		}

		if (context.getView() == null) {
			logger.error("ERROR: Unknown Error \"" + target + "\"");
			ViewFactroy.setErrorView(context, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		AbstractView view = context.getView();
		view.goPage();
	}

	@Override
	public void destroy() {
		Scheduler.cancel();
	}
}
