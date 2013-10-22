package com.beatrich.acgwallpaper.adapter;

import java.util.ArrayList;
import java.util.List;

import com.beatrich.acgwallpaper.model.WallPaper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class BaseDataAdapter<T> extends BaseAdapter {

	protected Context mContext;
	private List<T> dataList;

	public List<T> getDataList() {
		if (dataList == null)
			dataList = new ArrayList<T>();
		return dataList;
	}

	public BaseDataAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	public void setDataList(List<T> dataList) {
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
		return getDataList().get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
