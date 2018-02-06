package cn.lewis.spider.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class CrawlerItem implements Serializable {

	private static final long serialVersionUID = -2280147084375670327L;
	
	public final static int STATUS_UNEXECUTED = 0;
    public final static int STATUS_FAILED = 1;
    public final static int STATUS_SUCCESS = 5;

    private String url = null;
    private long executeTime = System.currentTimeMillis();

    private int status = STATUS_UNEXECUTED;
    private int executeCount = 0;

    private String key = null;

    private JsonObject metaData = new JsonObject();

    public CrawlerItem() {
    }

    public CrawlerItem(String url) {
        this.url = url;
    }

    public boolean matchUrl(String urlRegex) {
        return Pattern.matches(urlRegex, getUrl());
    }


    public CrawlerItem(String url, String[] metas) throws Exception {
        this(url);
        if (metas.length % 2 != 0) {
            throw new Exception("length of metas must be even");
        } else {
            for (int i = 0; i < metas.length; i += 2) {
                meta(metas[i * 2], metas[i * 2 + 1]);
            }
        }
    }

    public int incrExecuteCount(int count) {
        executeCount+=count;
        return executeCount;
    }

    public String getUrl() {
    	return url;
    }

    public CrawlerItem setUrl(String url) {
    	this.url = url;
        return this;
    }

    public long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(long fetchTime) {
        this.executeTime = fetchTime;
    }

    public int getExecuteCount() {
        return executeCount;
    }

    public void setExecuteCount(int executeCount) {
        this.executeCount = executeCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public JsonObject getMeta() {
        return metaData;
    }

    public String getMeta(String key){
        JsonElement value = metaData.get(key);
        return (value==null || (value instanceof JsonNull))?null:value.getAsString();
    }

    public int getMetaAsInt(String key){
        return metaData.get(key).getAsInt();
    }

    public boolean getMetaAsBoolean(String key) {
        return metaData.get(key).getAsBoolean();
    }

    public double getMetaAsDouble(String key) {
        return metaData.get(key).getAsDouble();
    }

    public long getMetaAsLong(String key) {
        return metaData.get(key).getAsLong();
    }

    public String getKey() {
        if (key == null) {
            return url;
        } else {
            return key;
        }
    }
     
    public CrawlerItem setKey(String key) {
        this.key = key;
        return this;
    }

    public String toString() {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
        StringBuilder sb = new StringBuilder();
        sb.append("\nKEY: ").append(this.getKey())
                .append("\nURL: ").append(this.getUrl())
                .append("\nSTATUS: ");

        switch (this.getStatus()) {
            case CrawlerItem.STATUS_SUCCESS:
                sb.append("success");
                break;
            case CrawlerItem.STATUS_FAILED:
                sb.append("failed");
                break;
            case CrawlerItem.STATUS_UNEXECUTED:
                sb.append("unexecuted");
                break;
        }

        sb.append("\nExecuteTime:").append(sdf.format(new Date(this.getExecuteTime())))
                .append("\nExecuteCount:").append(this.getExecuteCount());

        int metaIndex = 0;

        for(Entry<String, JsonElement> entry: this.getMeta().entrySet()){
            sb.append("\nMETA").append("[").append(metaIndex++).append("]:(")
                    .append(entry.getKey()).append(",").append(entry.getValue()).append(")");
        }

        sb.append("\n");
        return sb.toString();
    }

    public CrawlerItem meta(JsonObject metaData) {
        this.metaData = metaData;
        return this;
    }

    public CrawlerItem meta(String key, String value) {
        metaData.addProperty(key, value);
        return this;
    }

    public CrawlerItem meta(String key, int value) {
        metaData.addProperty(key, value);
        return this;
    }

    public CrawlerItem meta(String key, boolean value) {
        metaData.addProperty(key, value);
        return this;
    }

    public CrawlerItem meta(String key, double value) {
        metaData.addProperty(key, value);
        return this;
    }

    public CrawlerItem meta(String key, long value) {
        metaData.addProperty(key, value);
        return this;
    }

}
