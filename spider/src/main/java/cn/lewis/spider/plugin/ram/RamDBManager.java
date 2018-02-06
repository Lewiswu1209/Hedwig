package cn.lewis.spider.plugin.ram;

import cn.lewis.spider.crawldb.DBManager;
import cn.lewis.spider.crawldb.Generator;
import cn.lewis.spider.model.CrawlerItem;
import cn.lewis.spider.model.CrawlerItemList;
import java.util.Map.Entry;

public class RamDBManager extends DBManager {

    public RamDB ramDB;
    public RamGenerator generator=null;

    public RamDBManager(RamDB ramDB) {
        this.ramDB = ramDB;
        this.generator=new RamGenerator(ramDB);
    }

    @Override
    public boolean isExists() {
        return true;
    }

    @Override
    public void clear() throws Exception {
        ramDB.crawlDB.clear();
        ramDB.fetchDB.clear();
        ramDB.linkDB.clear();
    }

    @Override
    public Generator createGenerator() {
        return new RamGenerator(ramDB);
    }

    @Override
    public void open() throws Exception {
    }

    @Override
    public void close() throws Exception {
    }

    @Override
    public void insert(CrawlerItem datum, boolean force) throws Exception {
        String key = datum.getKey();
        if (!force) {
            if (ramDB.crawlDB.containsKey(key)) {
                return;
            }
        }
        ramDB.crawlDB.put(key, datum);
    }
    
    @Override
    public void insert(CrawlerItemList datums, boolean force) throws Exception {
        for(CrawlerItem datum:datums){
            insert(datum,force);
        }
    }

    @Override
    public void merge() throws Exception {

        for (Entry<String, CrawlerItem> fetchEntry : ramDB.fetchDB.entrySet()) {
            ramDB.crawlDB.put(fetchEntry.getKey(), fetchEntry.getValue());
        }
        ramDB.fetchDB.clear();

        for (String key : ramDB.linkDB.keySet()) {
            if (!ramDB.crawlDB.containsKey(key)) {
                ramDB.crawlDB.put(key, ramDB.linkDB.get(key));
            }
        }
        ramDB.linkDB.clear();
    }

    public void initUpdater() throws Exception {
    }

    public synchronized void update(CrawlerItem fetchDatum) throws Exception {
        ramDB.fetchDB.put(fetchDatum.getKey(), fetchDatum);
    }

    public synchronized void update(CrawlerItemList parseDatums) throws Exception {
        for (CrawlerItem datum : parseDatums) {
            ramDB.linkDB.put(datum.getKey(), datum);
        }
    }

    public void closeUpdater() throws Exception {
    }
 
}
