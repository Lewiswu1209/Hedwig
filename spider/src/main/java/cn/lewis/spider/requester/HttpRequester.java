package cn.lewis.spider.requester;

import java.io.IOException;
import java.util.HashSet;

import org.hedwig.net.http.HttpClient;
import org.hedwig.net.http.HttpGet;
import org.hedwig.net.http.HttpRequest;
import org.hedwig.net.http.HttpResponse;

import cn.edu.hfut.dmic.webcollector.conf.DefaultConfigured;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.Requester;

public class HttpRequester extends DefaultConfigured implements Requester{

    protected HashSet<Integer> successCodeSet;

    protected HashSet<Integer> createSuccessCodeSet(){
        HashSet<Integer> result = new HashSet<Integer>();
        result.add(200);
        result.add(301);
        result.add(302);
        result.add(404);
        return result;
    }

    public HttpRequester() {
        successCodeSet = createSuccessCodeSet();
    }

    @Override
    public Page getResponse(String url) throws Exception {
        return getResponse(new CrawlDatum(url));
    }

    @Override
    public Page getResponse(CrawlDatum datum) throws Exception {
    	HttpRequest request = new HttpGet(datum.url());
        if(conf.getDefaultUserAgent()!=null){
        	request.setUserAgent(conf.getDefaultUserAgent());
        }
        request.setMaxRedirectCount(conf.getMaxRedirect());
        request.setConnectTimeout(conf.getConnectTimeout());
        request.setReadTimeout(conf.getReadTimeout());
        HttpClient client = new HttpClient(request);
        HttpResponse response = client.connect();
        if (!successCodeSet.contains(response.getCode())) {
            throw new IOException(String.format("Server returned HTTP response code: %d", response.getCode()));
        }
        Page page = new Page(
        		datum,
                response.getContentType(),
                response.getContent()
        );
        client.disconnect();

        return page;
    }
}
