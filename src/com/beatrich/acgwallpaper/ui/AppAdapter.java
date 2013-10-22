package com.beatrich.acgwallpaper.ui;

import greendroid.widget.AsyncImageView;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beatrich.acgwallpaper.R;
import com.beatrich.acgwallpaper.model.WallPaper;

public class AppAdapter extends BaseAdapter {
 
	private Context mContext;
	public static final int WALLPAPER_PAGE_SIZE = 16;
	private List<WallPaper> dataList;
	public AppAdapter(Context context) {
		mContext = context;
		 
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return getDataList().size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return getDataList().get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		WallPaper wallPaper = getDataList().get(position);
		AppItem appItem;
		if (convertView == null) {
			View v = LayoutInflater.from(mContext).inflate(R.layout.app_item, null);
			
			appItem = new AppItem();
			appItem.mAppIcon = (AsyncImageView)v.findViewById(R.id.ivAppIcon);
			appItem.mAppName = (TextView)v.findViewById(R.id.tvAppName);
			
			v.setTag(appItem);
			convertView = v;
		} else {
			appItem = (AppItem)convertView.getTag();
		}
		// set the icon
		appItem.mAppIcon.setDefaultImageResource(R.drawable.loadpic);
		appItem.mAppIcon.setUrl(wallPaper.getThumbUrl());
		// set the app name
		appItem.mAppName.setText(wallPaper.getWh());
		//sdcard/Photo/photos
		return convertView;
	}

	/**
	 * 每个应用显示的内容，包括图标和名�?	 * @author Yao.GUET
	 *
	 */
	class AppItem {
		AsyncImageView mAppIcon;
		TextView mAppName;
	}

	public List<WallPaper> getDataList() {
		if (dataList==null)
			dataList=new ArrayList<WallPaper>();
		return dataList;
	}
	public void setDataList(List<WallPaper> dataList) {
		this.dataList = dataList;
	}
	
	
}
