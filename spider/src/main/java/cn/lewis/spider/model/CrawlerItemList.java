package cn.lewis.spider.model;

import com.google.gson.JsonObject;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class CrawlerItemList implements Iterable<CrawlerItem> {

    protected LinkedList<CrawlerItem> linkedItemList = new LinkedList<CrawlerItem>();

    public CrawlerItemList() {
    }

    public CrawlerItemList(Iterable<String> links) {
        add(links);
    }

    public CrawlerItemList(CrawlerItemList itemList) {
        add(itemList);
    }

    public CrawlerItemList(Collection<CrawlerItem> items) {
        for (CrawlerItem item : items) {
            this.add(item);
        }
    }

    public CrawlerItemList add(CrawlerItem items) {
        linkedItemList.add(items);
        return this;
    }

    public CrawlerItemList add(String url) {
        CrawlerItem item = new CrawlerItem(url);
        return add(item);
    }

    public CrawlerItemList add(CrawlerItemList itemList) {
        linkedItemList.addAll(itemList.linkedItemList);
        return this;
    }

    public CrawlerItemList add(Iterable<String> links) {
        for (String link : links) {
            add(link);
        }
        return this;
    }

    public CrawlerItem addAndReturn(String url){
        CrawlerItem item = new CrawlerItem(url);
        add(item);
        return item;
    }

    public CrawlerItemList addAndReturn(Iterable<String> links){
        CrawlerItemList itemList = new CrawlerItemList(links);
        add(itemList);
        return itemList;
    }

    public CrawlerItemList addAndReturn(CrawlerItemList itemList){
        add(itemList);
        return itemList;
    }

    public CrawlerItemList meta(JsonObject metaData) {
        for(CrawlerItem datum:linkedItemList){
            datum.meta(metaData);
        }
        return this;
    }

    public CrawlerItemList meta(String key, String value) {
        for (CrawlerItem item : linkedItemList) {
        	item.meta(key, value);
        }
        return this;
    }

    public CrawlerItemList meta(String key, int value) {
        for (CrawlerItem item : linkedItemList) {
        	item.meta(key, value);
        }
        return this;
    }

    public CrawlerItemList meta(String key, boolean value) {
        for (CrawlerItem item : linkedItemList) {
        	item.meta(key, value);
        }
        return this;
    }

    public CrawlerItemList meta(String key, double value) {
        for (CrawlerItem item : linkedItemList) {
        	item.meta(key, value);
        }
        return this;
    }

    public CrawlerItemList meta(String key, long value) {
        for (CrawlerItem item : linkedItemList) {
        	item.meta(key, value);
        }
        return this;
    }


    public Iterator<CrawlerItem> iterator() {
        return linkedItemList.iterator();
    }

    public CrawlerItem get(int index) {
        return linkedItemList.get(index);
    }

    public int size() {
        return linkedItemList.size();
    }

    public CrawlerItem remove(int index) {
        return linkedItemList.remove(index);
    }

    public boolean remove(CrawlerItem datum) {
        return linkedItemList.remove(datum);
    }

    public void clear() {
        linkedItemList.clear();
    }

    public boolean isEmpty() {

        return linkedItemList.isEmpty();
    }

    public int indexOf(CrawlerItem datum) {
        return linkedItemList.indexOf(datum);
    }

    @Override
    public String toString() {
        return linkedItemList.toString();
    }

}
