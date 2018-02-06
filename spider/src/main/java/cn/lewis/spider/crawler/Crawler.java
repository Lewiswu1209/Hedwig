package cn.lewis.spider.crawler;

import cn.lewis.spider.fetcher.Executor;
import cn.lewis.spider.fetcher.Visitor;
import cn.lewis.spider.model.CrawlerItem;
import cn.lewis.spider.model.CrawlerItemList;
import cn.lewis.spider.model.Links;
import cn.lewis.spider.model.RegexRule;
import cn.lewis.spider.model.Page;
import org.jsoup.nodes.Document;

import java.net.HttpURLConnection;

import org.hedwig.utility.net.http.*;

public abstract class Crawler extends AbstractCrawler implements Executor, Visitor {

    protected boolean autoParse = true;
    protected Visitor visitor;

    public Crawler(boolean autoParse) {
        this.autoParse = autoParse;
        this.visitor = this;
        this.executor = this;
    }
    
    public HttpRequest OnRequest(CrawlerItem item) {
    	HttpRequest request = new HttpGet(item.getUrl());
        if(conf.getDefaultUserAgent()!=null){
        	request.setUserAgent(conf.getDefaultUserAgent());
        }
        request.setMaxRedirectCount(conf.getMaxRedirect());
        request.setConnectTimeout(conf.getConnectTimeout());
        request.setReadTimeout(conf.getReadTimeout());
        return request;
    }

    protected RegexRule regexRule = new RegexRule();

    public void execute(CrawlerItem item, CrawlerItemList newDetectedItemList) throws Exception {
    	HttpRequest request = OnRequest(item);
        HttpClient client = new HttpClient(request);
        HttpResponse response = client.connect();
        Page page = new Page(
        		item,
                response.getCode(),
                response.getContentType(),
                response.getContent()
        );
        client.disconnect();
        if (page.code()==HttpURLConnection.HTTP_OK) {
	        visitor.visit(page, newDetectedItemList);
	        
	        if (autoParse && !regexRule.isEmpty()) {
	            parseLink(page, newDetectedItemList);
	        }
	        afterVisit(page, newDetectedItemList);
        }
    }

    public void afterVisit(Page page, CrawlerItemList newDetectedItemList) {
    	
    }
    
    protected void parseLink(Page page, CrawlerItemList newDetectedItemList) {
        String conteType = page.contentType();
        if (conteType != null && conteType.contains("text/html")) {
            Document doc = page.doc();
            if (doc != null) {
                Links links = new Links().addByRegex(doc, regexRule, conf.getAutoDetectImg());
                newDetectedItemList.add(links);
            }
        }
    }

    public void addRegex(String urlRegex) {
        regexRule.addRule(urlRegex);
    }

    public boolean isAutoParse() {
        return autoParse;
    }

    public void setAutoParse(boolean autoParse) {
        this.autoParse = autoParse;
    }

    public RegexRule getRegexRule() {
        return regexRule;
    }

    public void setRegexRule(RegexRule regexRule) {
        this.regexRule = regexRule;
    }

}
