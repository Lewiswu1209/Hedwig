package cn.lewis.spider.plugin.ram;

import cn.lewis.spider.crawldb.Generator;
import cn.lewis.spider.model.CrawlerItem;
import java.util.Iterator;
import java.util.Map.Entry;

public class RamGenerator extends Generator {

    RamDB ramDB;

    Iterator<Entry<String, CrawlerItem>> iterator;

	public RamGenerator(RamDB ramDB) {
        this.ramDB = ramDB;
        iterator = ramDB.crawlDB.entrySet().iterator();
    }

    @Override
    protected CrawlerItem getNext() throws Exception {
    	CrawlerItem next = null;
    	while(iterator.hasNext()) {
    		CrawlerItem item = iterator.next().getValue();
    		if (item.getStatus()!=CrawlerItem.STATUS_SUCCESS) {
    			next = item;
    			break;
    		}
    	}
        return next;
    }

    @Override
    public void close() throws Exception {

    }


}
