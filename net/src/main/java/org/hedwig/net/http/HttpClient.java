package org.hedwig.net.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpClient {

    private HttpRequest request = null;
    private HttpURLConnection con = null;

    public HttpClient(HttpRequest httpResquest) {
        this.request = httpResquest;
    }
    
    public HttpResponse connect() throws IOException {
    	URL url = null;

        InputStream is = null;
        byte[] outputData=null;

    	int code = -1;
        int maxRedirect = Math.max(0, this.request.maxRedirectCount);
        HttpResponse response = null;

        if (this.request instanceof HttpGet && this.request.outputStringBuffer != null) {
        	url = new URL(String.format("%s?%s", this.request.url, this.request.outputStringBuffer.toString()));
        } else {
        	url = new URL(this.request.url);
        }

        response = new HttpResponse(url);

        for (int redirect = 0; redirect <= maxRedirect; redirect++) {
            if (this.request.proxy == null) {
                con = (HttpURLConnection) url.openConnection();
            } else {
                con = (HttpURLConnection) url.openConnection(this.request.proxy);
            }

            this.request.config(con);

            if (this.request.outputStringBuffer!=null && this.request.dooutput) {
            	outputData = this.request.outputStringBuffer.toString().getBytes();
            }

            if(outputData!=null){
                OutputStream os=con.getOutputStream();
                os.write(outputData);
                os.close();
            }

            code = con.getResponseCode();
            response.setCode(code);

            boolean needFollow = true;
            switch (code) {
                case HttpURLConnection.HTTP_MOVED_PERM:
                case HttpURLConnection.HTTP_MOVED_TEMP:
                    if (redirect == this.request.maxRedirectCount) {
                        throw new RuntimeException("redirect to much time");
                    }
                    String location = con.getHeaderField("Location");
                    if (location == null) {
                        throw new RuntimeException("redirect with no location");
                    }
                    url = new URL(url, location);
                    response.setUrl(url);
                    continue;
                default:
                	needFollow = false;
                    break;
            }
            if (!needFollow) {
                break;
            }
        }

        if(code==HttpURLConnection.HTTP_OK) {
	        is = con.getInputStream();
	        String contentEncoding = con.getContentEncoding();
	        if (contentEncoding != null && contentEncoding.equals("gzip")) {
	            is = new GZIPInputStream(is);
	        }
	        response.setInputStream(is);
        }

        response.setHeaders(con.getHeaderFields());

        return response;
    }
    
    public void disconnect() {
    	con.disconnect();
    }

    static {
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
        };

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception ex) {
        }

		if (CookieHandler.getDefault() == null)
			CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
    }

}
