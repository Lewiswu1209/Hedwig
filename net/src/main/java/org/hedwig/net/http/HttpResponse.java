package org.hedwig.net.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.hedwig.ioutils.CopyUtils;
import org.hedwig.textutils.CharsetDetector;

public class HttpResponse {

    private URL url;
    private int code;
    private Map<String, List<String>> headers = null;
    private byte[] content = null;
    private InputStream inputStream = null;

	HttpResponse(URL url) {
	    this.url = url;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

    public URL getUrl() {
        return url;
    }

    void setUrl(URL url) {
        this.url = url;
    }

    public int getCode() {
	    return code;
	}

	void setCode(int code) {
	    this.code = code;
	}

	public Map<String, List<String>> getHeaders() {
	    if (headers == null) {
	        return null;
	    }
	    return headers;
	}

	void setHeaders(Map<String, List<String>> headers) {
	    this.headers = headers;
	}

	public String getHtml(String charset) throws IOException {
		getContent();
        if (content == null) {
            return null;
        }
        try {
            String html = new String(content, charset);
            return html;
        } catch (UnsupportedEncodingException ex) {
            throw ex;
        }
    }

    public String getHtmlByCharsetDetect() throws IOException {
		getContent();
        if (content == null) {
            return null;
        }
        String charset = CharsetDetector.guessEncoding(content);
        try {
            String html = new String(content, charset);
            return html;
        } catch (UnsupportedEncodingException ex) {
            return null;
        }
    }

    public List<String> getHeader(String name) {
        if (headers == null) {
            return null;
        }
        return headers.get(name);
    }

    public String getContentType() {
        String contentType;
        List<String> contentTypeList = getHeader("Content-Type");
        if (contentTypeList == null) {
            contentType = null;
        } else {
            contentType = contentTypeList.get(0);
        }
        return contentType;
    }

	public byte[] getContent() throws IOException {
	    if(content == null && inputStream != null){
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();

	        CopyUtils.copy(inputStream, bos);
	        content = bos.toByteArray();

	        bos.close();
	        inputStream.close();
	    }
	    return this.content;
	}

}
