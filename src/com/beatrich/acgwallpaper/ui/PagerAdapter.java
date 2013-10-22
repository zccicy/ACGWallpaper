package com.beatrich.acgwallpaper.ui;

import greendroid.widget.PagedAdapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class PagerAdapter extends PagedAdapter {

	private Context mContext;
	private List<WallpaperPage> dataList;

	public PagerAdapter(Context context) {
		super();
		this.mContext = context;
	}

	public List<WallpaperPage> getDataList() {
		if (dataList == null)
			dataList = new ArrayList<WallpaperPage>();
		return dataList;
	}

	public void setDataList(List<WallpaperPage> dataList) {
		this.dataList = dataList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return getDataList().size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (position < getDataList().size())
			return getDataList().get(position).getOutLayout(mContext);
		else
			return convertView;
	}

}
