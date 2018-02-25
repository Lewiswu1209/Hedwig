package org.hedwig.core.context;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class LocalHost {

	private HttpServletRequest request;

	public LocalHost(ServletRequest request) {
		this.request = (HttpServletRequest) request;
	}

	public String getLocalAddr() {
		return request.getLocalAddr();
	}

	public String getLocalName() {
		return request.getLocalName();
	}

	public int getLocalPort() {
		return request.getLocalPort();
	}

}
