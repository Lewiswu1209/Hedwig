package org.hedwig.core.view;

import java.io.IOException;
import java.io.PrintWriter;

import org.hedwig.core.config.Configuration;

public class HtmlView extends AbstractView {

	private String html = null;

	public HtmlView(String html) {
		this.html = html;
	}

	@Override
	public void goPage() {
		PrintWriter writer = null;
		getResponse().setHeader("Content-type", "text/html;charset=" + Configuration.getCharset().name());
		getResponse().setCharacterEncoding(Configuration.getCharset().name());
		try {
	        writer = getResponse().getWriter();
	        writer.write(html);
	        writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (writer != null)
				writer.close();
		}
	}

}
