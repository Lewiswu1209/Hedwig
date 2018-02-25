package org.hedwig.core.view;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class RedirectView extends AbstractView {

	private String viewPath;

	public void setViewPath(String viewPath) {
		this.viewPath = viewPath;
	}

	public RedirectView(String viewPath) {
		this.viewPath = viewPath;
	}

	public void goPage() {
		try {
			getResponse().sendRedirect(viewPath);
		} catch (IOException e) {
			AbstractView view = new ErrorView(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			view.setRequest(getRequest());
			view.setResponse(getResponse());
			view.goPage();
		}
	}
}
