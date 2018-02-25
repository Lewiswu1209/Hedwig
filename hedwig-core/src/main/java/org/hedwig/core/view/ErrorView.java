package org.hedwig.core.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.hedwig.core.config.Configuration;

public class ErrorView extends AbstractView {
	
	protected static final String copyright = "<center><a href=\"http://www.baidu.com/\"><b>Powered by Hedwig</b></a></center>";
	
	protected static final String HTML_NOT_FOUND = "<html><head><title>404 Not Found</title></head><body bgcolor='white'><center><h1>404 Not Found</h1></center><hr>" + copyright + "</body></html>";
	protected static final String HTML_INTERNAL_SERVER_ERROR = "<html><head><title>500 Internal Server Error</title></head><body bgcolor='white'><center><h1>500 Internal Server Error</h1></center><hr>" + copyright + "</body></html>";
	protected static final String HTML_UNAUTHORIZED = "<html><head><title>401 Unauthorized</title></head><body bgcolor='white'><center><h1>401 Unauthorized</h1></center><hr>" + copyright + "</body></html>";
	protected static final String HTML_FORBIDDEN = "<html><head><title>403 Forbidden</title></head><body bgcolor='white'><center><h1>403 Forbidden</h1></center><hr>" + copyright + "</body></html>";
	
	int errorCode = 0;
	String errorPage = null;

	public ErrorView(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public ErrorView(int errorCode, String errorPage) {
		this.errorCode = errorCode;
		this.errorPage = errorPage;
	}

	@Override
	public void goPage() {
		getResponse().setStatus(errorCode);
		getResponse().setHeader("Content-type", "text/html;charset=" + Configuration.getCharset().name());
		getResponse().setCharacterEncoding(Configuration.getCharset().name());
		
		if (errorPage != null) {
			AbstractView view = new JspView(errorPage);
			view.setRequest(getRequest());
			view.setResponse(getResponse());
			view.goPage();
			return;
		}
		
		PrintWriter writer = null;
		try {
	        writer = getResponse().getWriter();
	        writer.write(getErrorHtml());
	        writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (writer != null)
				writer.close();
		}
	}

	private String getErrorHtml() {
		if (errorCode == HttpServletResponse.SC_NOT_FOUND)
			return HTML_NOT_FOUND;
		if (errorCode == HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
			return HTML_INTERNAL_SERVER_ERROR;
		if (errorCode == HttpServletResponse.SC_UNAUTHORIZED)
			return HTML_UNAUTHORIZED;
		if (errorCode == HttpServletResponse.SC_FORBIDDEN)
			return HTML_FORBIDDEN;
		return "<html><head><title>" + errorCode + " Error</title></head><body bgcolor='white'><center><h1>" + errorCode + " Error</h1></center><hr>" + copyright + "</body></html>";
	}

}
