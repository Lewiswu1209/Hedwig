package org.hedwig.core.view;

import java.io.IOException;

import javax.servlet.ServletException;

public class JspView extends AbstractView {

	private String viewPath;

	public void setViewPath(String viewPath) {
		this.viewPath = viewPath;
	}

	public JspView(String viewPath) {
		this.viewPath = viewPath;
	}

	public void goPage() {
		try {
			getRequest().getRequestDispatcher(viewPath).forward(getRequest(), getResponse());
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
