package org.hedwig.core.context;

public class ResultBundle<T> {

    protected int count;
    
    protected int maxPageCount;
    
    protected int curPageCount;

    private T result;

	public ResultBundle(int count, int maxPageCount, int curPageCount, T result) {
		super();
		this.count = count;
		this.maxPageCount = maxPageCount;
		this.curPageCount = curPageCount;
		this.result = result;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getMaxPageCount() {
		return maxPageCount;
	}

	public void setMaxPageCount(int maxPageCount) {
		this.maxPageCount = maxPageCount;
	}

	public int getCurPageCount() {
		return curPageCount;
	}

	public void setCurPageCount(int curPageCount) {
		this.curPageCount = curPageCount;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

}
