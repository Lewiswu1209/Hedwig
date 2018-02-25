package org.hedwig.core.context;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class RemoteHost {

	private HttpServletRequest request;

	private String remoteAddr = null;

	public RemoteHost(ServletRequest request) {
		this.request = (HttpServletRequest) request;
	}
	
	public String getRemoteUser() {
		return request.getRemoteUser();
	}

	public String getRemoteAddr() {
		if (remoteAddr == null) {
			remoteAddr = request.getHeader("x-forwarded-for");
			
			if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
				remoteAddr = request.getHeader("Proxy-Client-IP");
			}
			
			if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
				remoteAddr = request.getHeader("WL-Proxy-Client-IP");
			}
			
			if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
		}
		
		return remoteAddr;
	}

	public String getRemoteHost() {
		return request.getRemoteHost();
	}

	public int getRemotePort() {
		return request.getRemotePort();
	}

}
