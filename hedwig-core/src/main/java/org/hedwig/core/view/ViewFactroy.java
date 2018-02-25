package org.hedwig.core.view;

import org.hedwig.core.context.Context;

/**
 * This factory can create view and put it into content.
 * @author Lewis
 * @version 1.0
 */
public final class ViewFactroy {

	private ViewFactroy() {
	}

	public static void setHtmlView(Context context, String htmlSourceCode) {
		AbstractView view = new HtmlView(htmlSourceCode);
		context.setView(view);
	}
	
	public static void setJspView(Context context, String viewPath) {
		AbstractView view = new JspView(viewPath);
		context.setView(view);
	}
	
	public static void redirectView(Context context, String viewPath) {
		AbstractView view = new RedirectView(viewPath);
		context.setView(view);
	}

	public static void setErrorView(Context context, int errorCode) {
		AbstractView view = new ErrorView(errorCode);
		context.setView(view);
	}
}
