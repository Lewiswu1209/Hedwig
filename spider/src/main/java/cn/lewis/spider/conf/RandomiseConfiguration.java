package cn.lewis.spider.conf;

import java.util.HashMap;

import cn.edu.hfut.dmic.webcollector.conf.Configuration;

public class RandomiseConfiguration extends Configuration {
	
	private Configuration conf = null;

	public RandomiseConfiguration(Configuration conf) {
		this.conf = conf;
		this.setDefaultUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:62.0) Gecko/20100101 Firefox/62.0");
		this.setExecuteInterval(-7000);
	}

	@Override
	public Configuration set(String key, Object value) {
		return conf.set(key, value);
	}

	@Override
	public String getString(String key) {
		return conf.getString(key);
	}

	@Override
	public Boolean getBoolean(String key) {
		return conf.getBoolean(key);
	}

	@Override
	public Integer getInteger(String key) {
		return conf.getInteger(key);
	}

	@Override
	public Long getLong(String key) {
		return conf.getLong(key);
	}

	@Override
	public Double getDouble(String key) {
		return conf.getDouble(key);
	}

	@Override
	public <T> T get(String key) {
		return conf.get(key);
	}

	@Override
	public <T> T getOrDefault(String key, T defaultValue) {
		return conf.getOrDefault(key, defaultValue);
	}

	@Override
	public HashMap<String, Object> getData() {
		return conf.getData();
	}

	@Override
	public void setData(HashMap<String, Object> data) {
		conf.setData(data);
	}

	@Override
	public Integer getTopN() {
		return conf.getTopN();
	}

	@Override
	public Configuration setTopN(Integer topN) {
		return conf.setTopN(topN);
	}

	@Override
	public Configuration setMaxExecuteCount(Integer maxExecuteCount) {
		return conf.setMaxExecuteCount(maxExecuteCount);
	}

	@Override
	public Integer getMaxExecuteCount() {
		return conf.getMaxExecuteCount();
	}

	@Override
	public Configuration setConnectTimeout(Integer connectTimeOut) {
		return conf.setConnectTimeout(connectTimeOut);
	}

	@Override
	public Integer getConnectTimeout() {
		return conf.getConnectTimeout();
	}

	@Override
	public Configuration setReadTimeout(Integer connectTimeOut) {
		return conf.setReadTimeout(connectTimeOut);
	}

	@Override
	public Integer getReadTimeout() {
		return conf.getReadTimeout();
	}

	@Override
	public Configuration setExecuteInterval(Integer executeInterval) {
		return conf.setExecuteInterval(executeInterval);
	}

	@Override
	public Integer getThreadKiller() {
		return conf.getThreadKiller();
	}

	@Override
	public Configuration setThreadKiller(Integer threadKiller) {
		return conf.setThreadKiller(threadKiller);
	}

	@Override
	public Integer getWaitThreadEndTime() {
		return conf.getWaitThreadEndTime();
	}

	@Override
	public Configuration setWaitThreadEndTime(Integer waitThreadEndTime) {
		return conf.setWaitThreadEndTime(waitThreadEndTime);
	}

	@Override
	public Integer getMaxRedirect() {
		return conf.getMaxRedirect();
	}

	@Override
	public Configuration setMaxRedirect(Integer maxRedirect) {
		return conf.setMaxRedirect(maxRedirect);
	}

	@Override
	public Integer getMaxReceiveSize() {
		return conf.getMaxReceiveSize();
	}

	@Override
	public Configuration setMaxReceiveSize(int maxReceiveSize) {
		return conf.setMaxReceiveSize(maxReceiveSize);
	}

	@Override
	public String getDefaultUserAgent() {
		return conf.getDefaultUserAgent();
	}

	@Override
	public Configuration setDefaultUserAgent(String defaultUserAgent) {
		return conf.setDefaultUserAgent(defaultUserAgent);
	}

	@Override
	public String getDefaultCookie() {
		return conf.getDefaultCookie();
	}

	@Override
	public Configuration setDefaultCookie(String defaultCookie) {
		return conf.setDefaultCookie(defaultCookie);
	}

	@Override
	public Boolean getAutoDetectImg() {
		return conf.getAutoDetectImg();
	}

	@Override
	public Configuration setAutoDetectImg(Boolean autoDetectImg) {
		return conf.setAutoDetectImg(autoDetectImg);
	}

	@Override
	public String toString() {
		return conf.toString();
	}

	@Override
	public Integer getExecuteInterval() {
		Integer interval =  super.getExecuteInterval();
		if (interval < 0) {
			interval = (int)(Math.random()*Math.abs(interval));
		}
		return interval;
	}

}
