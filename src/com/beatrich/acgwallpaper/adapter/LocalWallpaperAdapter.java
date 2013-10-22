package com.beatrich.acgwallpaper.adapter;

import greendroid.widget.AsyncImageView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beatrich.acgwallpaper.R;
import com.beatrich.acgwallpaper.model.WallPaper;

public class LocalWallpaperAdapter extends BaseDataAdapter<WallPaper> {

	public static final int WALLPAPER_PAGE_SIZE = 16;
	private String mPath;

	public LocalWallpaperAdapter(Context context,String path) {
		super(context);
		mPath=path;
	}

	public String getmPath() {
		return mPath;
	}

	public void setmPath(String mPath) {
		this.mPath = mPath;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		WallPaper wallPaper = getDataList().get(position);
		WallPaperItemView wallPaperItemView;
		// AsyncTaskLoadPic taskLoadThumb;
		// ThumbPicLoadListener picLoadListener;
		if (convertView == null) {
			View v = LayoutInflater.from(mContext).inflate(R.layout.app_item,
					null);

			wallPaperItemView = new WallPaperItemView();
			wallPaperItemView.thumb = (AsyncImageView) v
					.findViewById(R.id.ivAppIcon);
			wallPaperItemView.resolution = (TextView) v
					.findViewById(R.id.tvAppName);

			v.setTag(wallPaperItemView);
			convertView = v;
		} else {
			wallPaperItemView = (WallPaperItemView) convertView.getTag();
		}
		// set the icon

		wallPaperItemView.thumb.setDefaultImageResource(R.drawable.loadpic);
		if (wallPaper.getOptions() != null)
			wallPaperItemView.thumb.setOptions(wallPaper.getOptions());
		if (!wallPaper.getPicUrl().startsWith("file:///"))
			wallPaperItemView.thumb.setUrl("file:///" + wallPaper.getPicUrl());
		System.out.println("url"+wallPaper.getPicUrl());
		return convertView;
	}

	/**
	 * 每个应用显示的内容，包括图标和名�? * @author Yao.GUET
	 * 
	 */
	class WallPaperItemView {
		AsyncImageView thumb;
		TextView resolution;
	}

}
