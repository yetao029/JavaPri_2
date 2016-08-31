package com.yt.util;

public class Page {
	private int everyPage;
	private int currentPage;
	private int totalPage;
	private int totalCount;
	private int beginIndex;
	private boolean hasPrePage;
	private boolean hasNextPage;

	public Page() {
	}

	public Page(int everyPage, int currentPage, int totalPage, int totalCount, int beginIndex, boolean hasPrePage,
			boolean hasNextPage) {
		super();
		this.everyPage = everyPage;
		this.currentPage = currentPage;
		this.totalPage = totalPage;
		this.totalCount = totalCount;
		this.beginIndex = beginIndex;
		this.hasPrePage = hasPrePage;
		this.hasNextPage = hasNextPage;
	}

	public int getEveryPage() {
		return everyPage;
	}

	public void setEveryPage(int everyPage) {
		this.everyPage = everyPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	public boolean isHasPrePage() {
		return hasPrePage;
	}

	public void setHasPrePage(boolean hasPrePage) {
		this.hasPrePage = hasPrePage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
	
	

}
