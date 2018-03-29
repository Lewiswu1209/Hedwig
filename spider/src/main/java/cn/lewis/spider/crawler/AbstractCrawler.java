package cn.lewis.spider.crawler;

import cn.lewis.spider.fetcher.Executor;
import cn.lewis.spider.fetcher.Fetcher;
import cn.lewis.spider.conf.Configuration;
import cn.lewis.spider.crawldb.DBManager;
import cn.lewis.spider.model.CrawlerItem;
import cn.lewis.spider.model.CrawlerItemList;

public abstract class AbstractCrawler {
	
    private final static int STATUS_RUNNING = 1;
    private final static int STATUS_STOPED = 2;
    
    protected int status;
    protected boolean resumable = false;
    protected int threads = 50;

    protected CrawlerItemList seeds = new CrawlerItemList();
    protected CrawlerItemList forcedSeeds = new CrawlerItemList();
    
    protected DBManager dbManager;
    protected Fetcher fetcher;
    protected Executor executor = null;
    
    protected Configuration conf = Configuration.getDefault();

    public Configuration getConf() {
		return conf;
	}

	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	public AbstractCrawler() {

    }

    public AbstractCrawler(DBManager dbManager, Executor executor) {
        this.dbManager = dbManager;
        this.executor = executor;
    }

    protected void inject() throws Exception {
        dbManager.insert(seeds);
    }

    protected void injectForcedSeeds() throws Exception {
        dbManager.insert(forcedSeeds, true);
    }

    public void start(int depth) throws Exception {
        if (!resumable) {
            if (dbManager.isExists()) {
                dbManager.clear();
            }

            if (seeds.isEmpty() && forcedSeeds.isEmpty()) {
                return;
            }
        }
        dbManager.open();

        if (!seeds.isEmpty()) {
            inject();
        }

        if (!forcedSeeds.isEmpty()) {
            injectForcedSeeds();
        }

        status = STATUS_RUNNING;

		for (int i = 0; i < depth; i++) {
            if (status == STATUS_STOPED) {
                break;
            }
            fetcher = new Fetcher();
            fetcher.setDBManager(dbManager);
            fetcher.setExecutor(executor);
            fetcher.setThreads(threads);
            fetcher.setConf(conf);
            int totalGenerate = fetcher.fetchAll();

            if (totalGenerate == 0) {
                break;
            }

        }
        dbManager.close();
        afterStop();
    }
    
    public void afterStop(){

    }

    public void stop() {
        status = STATUS_STOPED;
        fetcher.stop();
    }

    public CrawlerItem addSeed(CrawlerItem item, boolean force) {
        if (force) {
            forcedSeeds.add(item);
        } else {
            seeds.add(item);
        }
        return item;
    }

    public CrawlerItem addSeed(CrawlerItem item) {
        return addSeed(item,false);
    }

    public CrawlerItem addSeed(String url, boolean force) {
        CrawlerItem item = new CrawlerItem(url);
        return addSeed(item,force);
    }

    public CrawlerItem addSeed(String url) {
        return addSeed(url,false);
    }

    public CrawlerItemList addSeeds(Iterable<String> links, boolean force) {
        CrawlerItemList itemList = new CrawlerItemList(links);
        return addSeeds(itemList,force);
    }

    public CrawlerItemList addSeeds(Iterable<String> links) {
        return addSeeds(links,false);
    }

    public CrawlerItemList addSeeds(CrawlerItemList itemList, boolean force) {
        if (force) {
            forcedSeeds.add(itemList);
        } else {
            seeds.add(itemList);
        }
        return itemList;
    }

    public CrawlerItemList addSeeds(CrawlerItemList itemList) {
        return addSeeds(itemList,false);
    }

    public boolean isResumable() {
        return resumable;
    }

    public void setResumable(boolean resumable) {
        this.resumable = resumable;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public DBManager getDBManager() {
        return dbManager;
    }

    public void setDBManager(DBManager dbManager) {
        this.dbManager = dbManager;
    }

}
