package cn.lewis.spider.crawldb;

import cn.lewis.spider.model.CrawlerItem;
import cn.lewis.spider.model.CrawlerItemList;

public abstract class DBManager {

    public abstract boolean isExists();

    public abstract void clear() throws Exception;

    public abstract Generator createGenerator();

    public abstract void open() throws Exception;

    public abstract void close() throws Exception;

    public abstract void insert(CrawlerItem item, boolean force) throws Exception;

    public abstract void insert(CrawlerItemList itemList, boolean force) throws Exception;

    public abstract void merge() throws Exception;

    public void insert(CrawlerItem item) throws Exception {
        insert(item, false);
    }

    public void insert(CrawlerItemList itemList) throws Exception {
        insert(itemList, false);
    }

    public void insert(Iterable<String> links, boolean force) throws Exception {
        CrawlerItemList itemList = new CrawlerItemList(links);
        insert(itemList, force);
    }

    public void insert(Iterable<String> links) throws Exception {
        insert(links, false);
    }

    public void insert(String url, boolean force) throws Exception {
        CrawlerItem item = new CrawlerItem(url);
        insert(item, force);
    }

    public void insert(String url) throws Exception {
        CrawlerItem item = new CrawlerItem(url);
        insert(item);
    }

	public abstract void update(CrawlerItem item)  throws Exception;

	public abstract void update(CrawlerItemList next)  throws Exception;

	public abstract void initUpdater() throws Exception;

	public abstract void closeUpdater() throws Exception;

}
