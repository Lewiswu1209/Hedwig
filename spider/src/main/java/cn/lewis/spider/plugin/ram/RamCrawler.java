package cn.lewis.spider.plugin.ram;

import cn.lewis.spider.crawler.Crawler;

public abstract class RamCrawler extends Crawler {
    
    public RamCrawler(){
        this(true);
    }

    public RamCrawler(boolean autoParse) {
        super(autoParse);
        RamDB ramDB = new RamDB();
        setDBManager(new RamDBManager(ramDB));
    }
    
    public void start() throws Exception{
        start(Integer.MAX_VALUE);
    }

}
