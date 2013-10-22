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

public class WallpaperAdapter extends BaseAdapter {
 
	private Context mContext;
	public static final int WALLPAPER_PAGE_SIZE = 16;
	private List<WallPaper> dataList;
	public WallpaperAdapter(Context context) {
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
		WallPaperItemView wallPaperItemView;
//		AsyncTaskLoadPic taskLoadThumb;
//		ThumbPicLoadListener picLoadListener;
		if (convertView == null) {
			View v = LayoutInflater.from(mContext).inflate(R.layout.app_item, null);
			
			wallPaperItemView = new WallPaperItemView();
			wallPaperItemView.thumb = (AsyncImageView)v.findViewById(R.id.ivAppIcon);
			wallPaperItemView.resolution= (TextView)v.findViewById(R.id.tvAppName);
			
			v.setTag(wallPaperItemView);
			convertView = v;
		} else {
			wallPaperItemView = (WallPaperItemView)convertView.getTag();
		}
		// set the icon
		wallPaperItemView.thumb.setDefaultImageResource(R.drawable.loadpic);
		wallPaperItemView.thumb.setUrl(wallPaper.getThumbUrl());
		// set the app name
		wallPaperItemView.resolution.setText(wallPaper.getWh());
		//sdcard/Photo/photos
		return convertView;
	}

	/**
	 * 每个应用显示的内容，包括图标和名�?	 * @author Yao.GUET
	 *
	 */
	class WallPaperItemView {
		AsyncImageView thumb;
		TextView resolution;
	}

	public List<WallPaper> getDataList() {
		if (dataList==null)
			dataList=new ArrayList<WallPaper>();
		return dataList;
	}
	public void setDataList(List<WallPaper> dataList) {
		this.dataList = dataList;
	}
//	public class ThumbPicLoadListener extends BaseTaskListener<URL, Integer, Bitmap>
//	{
//		private WallPaper mWallPaper;
//		private ImageView mImageView;
//
//		
//		
//		public ThumbPicLoadListener(WallPaper mWallPaper, ImageView mImageView) {
//			super();
//			this.mWallPaper = mWallPaper;
//			this.mImageView = mImageView;
//		}
//
//		@Override
//		public void onCancelled() {
//			// TODO Auto-generated method stub
//			super.onCancelled();
//		}
//
//		@Override
//		public void onPostExecute(Bitmap result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//		}
//
//		@Override
//		public void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//		}
//
//		@Override
//		public void onProgressUpdate(Integer... values) {
//			// TODO Auto-generated method stub
//			super.onProgressUpdate(values);
//		}
//		
//	};
	
}
