package com.beatrich.acgwallpaper.model;

import java.io.Serializable;

import android.graphics.BitmapFactory.Options;

//The base URL is /post/index.xml.
public class WallPaper implements Serializable{
	private String thumbUrl;
	private String picUrl;
	private String wh;
	private String info;
	private Integer width;
	private Integer height;
	private Options options;
	private String tags;
	
	public WallPaper() {
		super();
		thumbUrl="";
		picUrl="";
		 
		info="";
		// TODO Auto-generated constructor stub
	}
	public String getThumbUrl() {
		return thumbUrl;
		
	}
	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	 
	public String getWh() {
		return wh;
	}
	public void setWh(String wh) {
		this.wh = wh;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Options getOptions() {
		return options;
	}
	public void setOptions(Options options) {
		this.options = options;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	  
	
	
	
}
