package org.hedwig.net.http;

public final class HttpGet extends HttpRequest {

	public HttpGet(String url) {
		super(url);
		this.method = "GET";
	}

}
