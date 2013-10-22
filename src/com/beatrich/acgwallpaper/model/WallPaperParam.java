package com.beatrich.acgwallpaper.model;

public class WallPaperParam {
	public static String url = "http://konachan.com/post/index.json?";
	private Integer limit;
	private Integer page;
	private String tags;

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

}
