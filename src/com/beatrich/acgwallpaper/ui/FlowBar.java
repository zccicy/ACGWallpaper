package com.beatrich.acgwallpaper.ui;

 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.beatrich.acgwallpaper.R;

public class FlowBar {

	
	private Context context;
	private LayoutInflater mInflater;
	public FlowBar(Context context) {
		super();
		this.context = context;
		mInflater=LayoutInflater.from(context);
	}
	
	public View getFlowBar()
	{
		LinearLayout flowBar=(LinearLayout)mInflater.inflate(R.layout.layout_flow_bar, null);
		return flowBar;
	}
	public LayoutInflater getmInflater(Context context) {
		if (mInflater==null)
			mInflater=LayoutInflater.from(context);
		return mInflater;
	}
	public void setmInflater(LayoutInflater mInflater) {
		this.mInflater = mInflater;
	}
	
	
	
	
	
}
