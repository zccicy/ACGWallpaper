package com.beatrich.acgwallpaper.ui;

import com.beatrich.acgwallpaper.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;


public class DetailDialogView {

	private LayoutInflater mInflater;
	private ImageView ivDetail;

	
	public DetailDialogView(Context context) {
		super();
		mInflater=LayoutInflater.from(context);
		
		// TODO Auto-generated constructor stub
	}
	public View getDetailDialogView(Drawable drawable)
	{
		View view=mInflater.inflate(R.layout.layout_detail, null);
		ivDetail=(ImageView)view.findViewById(R.id.iv_detail);
		
		ivDetail.setBackgroundDrawable(drawable);
		
		return view;
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
