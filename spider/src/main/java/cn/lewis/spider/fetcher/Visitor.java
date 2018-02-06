package cn.lewis.spider.fetcher;

import cn.lewis.spider.model.CrawlerItemList;
import cn.lewis.spider.model.Page;

public interface Visitor {

    public abstract void visit(Page page, CrawlerItemList newDetectedItemList);
    public void afterVisit(Page page, CrawlerItemList newDetectedItemList);
}
