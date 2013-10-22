package com.beatrich.acgwallpaper.model;

//The base URL is /tag/index.xml json.
public class PicTag {
private Integer type;
private Integer count;
private Boolean ambiguous;
private String name;
private Integer id;
public Integer getType() {
	return type;
}
public void setType(Integer type) {
	this.type = type;
}
public Integer getCount() {
	return count;
}
public void setCount(Integer count) {
	this.count = count;
}
public Boolean getAmbiguous() {
	return ambiguous;
}
public void setAmbiguous(Boolean ambiguous) {
	this.ambiguous = ambiguous;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}



}
