package org.hedwig.net.http;

import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class HttpRequest {
    protected String url = null;
    
	protected int maxRedirectCount = 2;
    protected boolean doinput = true;
    protected boolean dooutput = false;
    protected boolean followRedirects = false;
    protected int connectTimeout = 3000;
    protected int readTimeout = 10000;
    protected Map<String, List<String>> headerMap = null;

    protected StringBuffer outputStringBuffer = null;

    protected Proxy proxy = null;

    protected String method = null;

	public HttpRequest(String url) {
		this.url = url;
	}

	public int getMaxRedirectCount() {
		return maxRedirectCount;
	}

	public void setMaxRedirectCount(int maxRedirectCount) {
		this.maxRedirectCount = maxRedirectCount;
	}

	public boolean isFollowRedirects() {
		return followRedirects;
	}

	public void setFollowRedirects(boolean followRedirects) {
		this.followRedirects = followRedirects;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public Proxy getProxy() {
		return proxy;
	}

	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}
	
    public void setUserAgent(String userAgent) {
        setHeader("User-Agent", userAgent);
    }

    public void setCookie(String cookie) {
        setHeader("Cookie", cookie);
    }

    public void addHeader(String key, String value) {
        if (key == null) {
            throw new NullPointerException("key is null");
        }
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        initHeaderMap();
        List<String> valueList = headerMap.get(key);
        if (valueList == null) {
            valueList = new ArrayList<String>();
            headerMap.put(key, valueList);
        }
        valueList.add(value);
    }

    public void removeHeader(String key) {
        if (key == null) {
            throw new NullPointerException("key is null");
        }

        if (headerMap != null) {
            headerMap.remove(key);
        }
    }

    public void setHeader(String key, String value) {
        if (key == null) {
            throw new NullPointerException("key is null");
        }
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        initHeaderMap();
        List<String> valueList = new ArrayList<String>();
        valueList.add(value);
        headerMap.put(key, valueList);
    }
    
    public Map<String, List<String>> getHeaders() {
        return headerMap;
    }

    public List<String> getHeader(String key) {
        if (headerMap == null) {
            return null;
        }
        return headerMap.get(key);
    }

    public String getFirstHeader(String key) {
        if (headerMap == null) {
            return null;
        }
        List<String> valueList = headerMap.get(key);
        if (valueList.size() > 0) {
            return valueList.get(0);
        } else {
            return null;
        }
    }
	
	protected void config(HttpURLConnection con) {
		try {
			con.setRequestMethod(method);
		} catch (ProtocolException e) {
		}
        con.setInstanceFollowRedirects(followRedirects);
        con.setDoInput(doinput);
        con.setDoOutput(dooutput);
        con.setConnectTimeout(connectTimeout);
        con.setReadTimeout(readTimeout);

        if (headerMap != null) {
            for (Map.Entry<String, List<String>> entry : headerMap.entrySet()) {
                String key = entry.getKey();
                List<String> valueList = entry.getValue();
                for (String value : valueList) {
                    con.addRequestProperty(key, value);
                }
            }
        }
    }

	public void addData(String key, String value) {
    	if (this.outputStringBuffer == null) {
    		this.outputStringBuffer = new StringBuffer();
    	} else {
    		this.outputStringBuffer.append("&");
    	}
    	this.outputStringBuffer.append(key).append("=").append(value);
    }
	
    private void initHeaderMap() {
        if (headerMap == null) {
            headerMap = new HashMap<String, List<String>>();
        }
    }

}
