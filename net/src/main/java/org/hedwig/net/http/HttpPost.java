package org.hedwig.net.http;

public final class HttpPost extends HttpRequest {

	public HttpPost(String url) {
		super(url);
		this.dooutput = true;
		this.method = "POST";
	}

}
