package org.hedwig.core.context;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.servlet.jsp.PageContext;

import org.hedwig.core.view.AbstractView;

public final class Context {

	public static final int APPLICATION_SCOPE = PageContext.APPLICATION_SCOPE;
	public static final int SESSION_SCOPE = PageContext.SESSION_SCOPE;
	public static final int REQUEST_SCOPE = PageContext.REQUEST_SCOPE;
	public static final int PAGE_SCOPE = PageContext.PAGE_SCOPE;

	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private ServletContext application;

	private Parameters paras;
	private Cookies cookies  = null;

	private LocalHost localhost  = null;
	private RemoteHost remote = null;

	private AbstractView view;

	public Context(ServletRequest request, ServletResponse response) {
		this.request = (HttpServletRequest)request;
		this.response = (HttpServletResponse)response;
		this.session = this.request.getSession();
		this.application = this.session.getServletContext();

		this.paras = new Parameters(this.request.getParameterMap());
		this.cookies = new Cookies(request, response);
		this.remote = new RemoteHost(request);
		this.localhost = new LocalHost(request);
	}
	
	public RemoteHost getRemote() {
		if (this.remote == null) {
			this.remote = new RemoteHost(request);
		}
		return this.remote;
	}

	public LocalHost getLocalhost() {
		if (this.localhost == null) {
			this.localhost = new LocalHost(request);
		}
		return localhost;
	}

	/**
	 * Returns the value of the named attribute as an Object, or null if no attribute of the given name exists.
	 * @param attrName a String specifying the name of the attribute.
	 * @param scope the scope of attribute
	 * @return an Object containing the value of the attribute, or null if the attribute does not exist.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(String attrName, int scope) {
		if (scope == REQUEST_SCOPE) {
			return (T)request.getAttribute(attrName);
		} else if (scope == SESSION_SCOPE) {
			return (T)session.getAttribute(attrName);
		} else {
			return (T)application.getAttribute(attrName);
		}
	}

	/**
	 * Stores an attribute in this request
	 * @param attrName a String specifying the name of the attribute
	 * @param value the Object to be stored
	 */
	public void setAttribute(String attrName, Object value, int scope) {
		if (scope == APPLICATION_SCOPE) {
			application.setAttribute(attrName, value);
		} else if (scope == SESSION_SCOPE) {
			session.setAttribute(attrName, value);
		} else {
			request.setAttribute(attrName, value);
		}
	}
	
	public void setResultBundle(ResultBundle<? extends Object> resultBundle) {
		setAttribute("resultBundle", resultBundle, Context.REQUEST_SCOPE);
	}

	/**
	 * Stores attributes in this request, key of the map as attribute name and value of the map as attribute value
	 * @param attrMap key and value as attribute of the map to be stored
	 */
	public void setAttributes(Map<String, Object> attrMap, int scope) {
		for (Map.Entry<String, Object> entry : attrMap.entrySet()) {
			if (scope == APPLICATION_SCOPE) {
				application.setAttribute(entry.getKey(), entry.getValue());
			} else if (scope == SESSION_SCOPE) {
				session.setAttribute(entry.getKey(), entry.getValue());
			} else {
				request.setAttribute(entry.getKey(), entry.getValue());
			}
		}
	}

	/**
	 * Removes an attribute from this request
	 * @param name a String specifying the name of the attribute to remove
	 */
	public void removeAttribute(String name, int scope) {
		if (scope == REQUEST_SCOPE) {
			request.removeAttribute(name);
		} else if (scope == SESSION_SCOPE) {
			session.removeAttribute(name);
		} else if (scope == APPLICATION_SCOPE) {
			application.removeAttribute(name);
		} else {
			return;
		}
	}
	
	/**
	 * Returns an Enumeration containing the names of the attributes available to this request.
	 * @return The attribute names
	 */
	public Enumeration<String> getAttributeNames(int scope) {
		if (scope == REQUEST_SCOPE) {
			return request.getAttributeNames();
		} else if (scope == SESSION_SCOPE) {
			return session.getAttributeNames();
		} else if (scope == APPLICATION_SCOPE) {
			return application.getAttributeNames();
		} else {
			return null;
		}
	}
	
	public Parameters getParameters() {
		return paras;
	}
	
	public Cookies getCookies() {
		if (this.cookies == null) {
			this.cookies = new Cookies(request, response);
		}
		return cookies;
	}
	
	public Part getPart(String partName) throws IOException, ServletException {
		return request.getPart(partName);
	}
	
	public Collection<Part> getParts() throws ServletException, IOException {
		return request.getParts();
	}

	public AbstractView getView() {
		return view;
	}

	public void setView(AbstractView view) {
		view.setRequest(request);
		view.setResponse(response);
		this.view = view;
	}

	public void resetSession() {
		session.invalidate();
	}

}
