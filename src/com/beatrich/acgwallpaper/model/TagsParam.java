package com.beatrich.acgwallpaper.model;

 
public class TagsParam {
	public static String url = "http://konachan.com/tag/index.json?";
	private Integer limit; //How many tags to retrieve. Setting this to 0 will return every tag.
	private Integer page; //The page number.
	private String order;//Can be date, count, or name.
	private Integer id; //The id number of the tag.
	private Integer after_id; //Return all tags that have an id number greater than this.
	private String name; //The exact name of the tag.
	private String name_pattern; //Search for any tag that has this parameter in its name.
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
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAfter_id() {
		return after_id;
	}
	public void setAfter_id(Integer after_id) {
		this.after_id = after_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName_pattern() {
		return name_pattern;
	}
	public void setName_pattern(String name_pattern) {
		this.name_pattern = name_pattern;
	}
	
}
