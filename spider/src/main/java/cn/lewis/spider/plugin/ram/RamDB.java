package cn.lewis.spider.plugin.ram;

import cn.lewis.spider.model.CrawlerItem;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class RamDB {
    
    protected HashMap<String, CrawlerItem> crawlDB = new LinkedHashMap<String, CrawlerItem>();
    protected HashMap<String, CrawlerItem> fetchDB = new LinkedHashMap<String, CrawlerItem>();
    protected HashMap<String, CrawlerItem> linkDB = new LinkedHashMap<String, CrawlerItem>();
}
