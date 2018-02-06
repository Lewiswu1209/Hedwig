package cn.lewis.spider.crawldb;

import cn.lewis.spider.model.CrawlerItem;

public abstract class Generator {

    protected int processedCount;

    public Generator() {
        this.processedCount = 0;
    }

    public CrawlerItem next(){
        CrawlerItem item;
        while (true) {
            try {
            	item = getNext();
                if (item == null) {
                    return item;
                }
                processedCount += 1;
                return item;
            } catch (Exception e) {
                return null;
            }
        }
    }

    protected abstract CrawlerItem getNext() throws Exception;

    public int getProcessedCount(){
        return processedCount;
    }

    public abstract void close() throws Exception;
}
