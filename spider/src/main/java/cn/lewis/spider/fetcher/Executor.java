package cn.lewis.spider.fetcher;

import cn.lewis.spider.model.CrawlerItem;
import cn.lewis.spider.model.CrawlerItemList;

public interface Executor{
    public void execute(CrawlerItem datum,CrawlerItemList newDetectedItemList) throws Exception;
}
