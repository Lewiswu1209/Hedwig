package org.hedwig.core.context;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Cookies {
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private Map<String, Cookie> cookies = new HashMap<String, Cookie>();
	
	public Cookies(ServletRequest request, ServletResponse response) {
		this.request = (HttpServletRequest) request;
		this.response = (HttpServletResponse) response;
		
		Cookie[] cookies = this.request.getCookies();

		if (cookies != null) {
			for(Cookie cookie : cookies) {
				this.cookies.put(cookie.getName(), cookie);
			}
		}
	}

	public Cookie getCookie(String name) {
		return cookies.get(name);
	}
	
	public String getCookieValue(String name) {
		return cookies.get(name).getValue();
	}
	
	public void setCookie(String name, String value, int maxAge) {
		Cookie cookie = cookies.get(name);
		
		if (cookie != null) {
			cookie.setValue(value);
		} else {
			cookie = new Cookie(name, value);
		}
		
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
	
	public void removeCookie(String name) {
		Cookie cookie = cookies.get(name);
		
		if (cookie != null) {
			cookie.setMaxAge(0);
		}
	}
}
