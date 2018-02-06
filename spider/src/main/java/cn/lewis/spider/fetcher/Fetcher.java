package cn.lewis.spider.fetcher;

import cn.lewis.spider.conf.Configuration;
import cn.lewis.spider.crawldb.DBManager;
import cn.lewis.spider.crawldb.Generator;
import cn.lewis.spider.model.CrawlerItem;
import cn.lewis.spider.model.CrawlerItemList;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Fetcher {
	
	private Configuration conf;
	
	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	private static Log logger = LogFactory.getLog(Fetcher.class);

    public DBManager dbManager;
    public Executor executor;

    private AtomicInteger activeThreads;
    private AtomicInteger startedThreads;
    private AtomicInteger spinWaiting;
    private AtomicLong lastRequestStart;

    private QueueFeeder feeder = null;
    private FetchQueue fetchQueue = null;

    public static final int FETCH_SUCCESS = 1;
    public static final int FETCH_FAILED = 2;

    private int threads = 50;

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public static class FetchQueue {

        public AtomicInteger totalSize = new AtomicInteger(0);

        public final List<CrawlerItem> queue = Collections.synchronizedList(new LinkedList<CrawlerItem>());

        public void clear() {
            queue.clear();
        }

        public int getSize() {
            return queue.size();
        }

        public synchronized void addFetchItem(CrawlerItem item) {
            if (item == null) {
                return;
            }
            queue.add(item);
            totalSize.incrementAndGet();
        }

        public synchronized CrawlerItem getFetchItem() {
            if (queue.isEmpty()) {
                return null;
            }
            return queue.remove(0);
        }
    }

    public static class QueueFeeder extends Thread {

        public FetchQueue queue;

        public DBManager dbManager;
        public Generator generator = null;
        public int size;

        public QueueFeeder(FetchQueue queue, DBManager dbManager, int size) {
            this.queue = queue;
            this.dbManager = dbManager;
            this.size = size;
        }

        public void stopFeeder(){
            running = false;
            while (this.isAlive()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
            }
        }

        public void closeGenerator() throws Exception {
            if(generator!=null) {
                generator.close();
            }
        }

        public volatile boolean running = true;

        @Override
        public void run(){

            generator = dbManager.createGenerator();

            boolean hasMore = true;
            running = true;
            while (hasMore && running) {

                int feed = size - queue.getSize();
                if (feed <= 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                    continue;
                }
                while (feed > 0 && hasMore && running) {

                    CrawlerItem item = generator.next();
                    hasMore = (item != null);

                    if (hasMore) {
                        queue.addFetchItem(item);
                        feed--;
                    }

                }

            }

        }

    }

    private class FetcherThread extends Thread {

        @Override
        public void run() {
            startedThreads.incrementAndGet();
            activeThreads.incrementAndGet();
            CrawlerItem item = null;
            try {

                while (running) {
                    try {
                        item = fetchQueue.getFetchItem();
                        if (item == null) {
                            if (feeder.isAlive() || fetchQueue.getSize() > 0) {
                                spinWaiting.incrementAndGet();

                                try {
                                    Thread.sleep(500);
                                } catch (Exception ex) {
                                }

                                spinWaiting.decrementAndGet();
                                continue;
                            } else {
                                return;
                            }
                        }

                        lastRequestStart.set(System.currentTimeMillis());

                        CrawlerItemList detectedItemList = new CrawlerItemList();
                        try {
                            executor.execute(item, detectedItemList);
                            item.setStatus(CrawlerItem.STATUS_SUCCESS);
                            logger.info(" INFO: accomplished: " + item.getUrl());
                        } catch (Exception ex) {
                        	item.setStatus(CrawlerItem.STATUS_FAILED);
                            logger.warn(" WARN: failed:" + item.getUrl(), ex);
                        }

                        item.incrExecuteCount(1);
                        item.setExecuteTime(System.currentTimeMillis());
                        try {
                            dbManager.update(item);
                            if (item.getStatus() == CrawlerItem.STATUS_SUCCESS && !detectedItemList.isEmpty()) {
                                dbManager.update(detectedItemList);
                            }
                        } catch (Exception ex) {
                        }
                        long executeInterval = conf.getExecuteInterval();
                        if (executeInterval > 0) {
                            try {
                                Thread.sleep(executeInterval);
                            } catch (Exception sleepEx) {
                            }
                        }

                    } catch (Exception ex) {
                    }
                }

            } catch (Exception ex) {
            } finally {
                activeThreads.decrementAndGet();
            }

        }

    }

	@SuppressWarnings("deprecation")
	public int fetchAll() throws Exception {
        if (executor == null) {
            return 0;
        }

        dbManager.merge();

        try {
            dbManager.initUpdater();
            running = true;
            lastRequestStart = new AtomicLong(System.currentTimeMillis());

            activeThreads = new AtomicInteger(0);
            startedThreads = new AtomicInteger(0);
            spinWaiting = new AtomicInteger(0);
            fetchQueue = new FetchQueue();
            feeder = new QueueFeeder(fetchQueue, dbManager, 1000);
            feeder.start();

            FetcherThread[] fetcherThreads = new FetcherThread[threads];
            for (int i = 0; i < threads; i++) {
                fetcherThreads[i] = new FetcherThread();
                fetcherThreads[i].start();
            }

            do {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }

                if ((System.currentTimeMillis() - lastRequestStart.get()) > conf.getThreadKiller()) {
                    break;
                }

            } while (running && (startedThreads.get() != threads || activeThreads.get() > 0));
            running = false;
            long waitThreadEndStartTime = System.currentTimeMillis();
            while (activeThreads.get() > 0) {
                try {
                    Thread.sleep(500);
                } catch (Exception ex) {
                }
                if (System.currentTimeMillis() - waitThreadEndStartTime > conf.getWaitThreadEndTime()) {
                    for (int i = 0; i < fetcherThreads.length; i++) {
                        if (fetcherThreads[i].isAlive()) {
                            try {
                                fetcherThreads[i].stop();
                            } catch (Exception ex) {
                            }
                        }
                    }
                    break;
                }
            }
            feeder.stopFeeder();
            fetchQueue.clear();
        } finally {
            if(feeder!=null) {
                feeder.closeGenerator();
            }
            dbManager.closeUpdater();
        }
        return feeder.generator.getProcessedCount();
    }

    volatile boolean running;

    public void stop() {
        running = false;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public DBManager getDBManager() {
        return dbManager;
    }

    public void setDBManager(DBManager dbManager) {
        this.dbManager = dbManager;
    }

}
