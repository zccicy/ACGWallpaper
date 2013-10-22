package com.beatrich.acgwallpaper.ui;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.beatrich.acgwallpaper.R;
import com.beatrich.acgwallpaper.adapter.LocalWallpaperAdapter;
import com.beatrich.acgwallpaper.model.WallPaper;

public class LocalWallpaperPage {
	private GridView gvPage;
	private LinearLayout outLayout;
	private LinearLayout lyLoadPage;
	private LocalWallpaperAdapter localWallpaperAdapter;
	private LayoutInflater inflater;
	private TextView tvPath;
	

	public LocalWallpaperPage(Context context, String path) {
		initViews(context, path);

	}

	/**
	 * @param context
	 */
	private void initViews(Context context, String path) {
		outLayout = (LinearLayout) getInflater(context).inflate(
				R.layout.layout_wallpaper_page, null);
		outLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		outLayout.setGravity(Gravity.CENTER);
		tvPath = new TextView(context);
		tvPath.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		tvPath.setGravity(Gravity.TOP | Gravity.CENTER_VERTICAL);
		tvPath.setBackgroundColor(0xff0000);
		tvPath.setTextColor(0xff000000);
		tvPath.setText(path);

	}

	public void showPb(Context context) {
		lyLoadPage = (LinearLayout) getInflater(context).inflate(
				R.layout.progress_bar, null);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		getOutLayout(context).addView(lyLoadPage, layoutParams);
	}

	public void cleanPb(Context context) {
		outLayout.removeView(lyLoadPage);
	}

	public void ShowGv(Context context, List<WallPaper> dataList) {
		getOutLayout(context).removeAllViews();
		getLocalWallpaperAdapter(context).setDataList(dataList);
		getGvPage(context).setAdapter(getLocalWallpaperAdapter(context));
		getOutLayout(context).addView(getGvPage(context));
		getLocalWallpaperAdapter(context).notifyDataSetChanged();

	}

	public void cleanGv(Context context)

	{
		outLayout.removeView(gvPage);
	}

	public GridView getGvPage(Context context) {
		if (gvPage == null) {
			gvPage = new GridView(context);

			gvPage.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT,
					LinearLayout.LayoutParams.FILL_PARENT));

			gvPage.setNumColumns(2);
		}
		return gvPage;
	}

	public void setGvPage(GridView gvPage) {
		this.gvPage = gvPage;
	}

	public LocalWallpaperAdapter getLocalWallpaperAdapter(Context context) {
		if (localWallpaperAdapter == null) {
			Display display=((Activity)context).getWindowManager().getDefaultDisplay();
			localWallpaperAdapter = new LocalWallpaperAdapter(context,"");
		}

		return localWallpaperAdapter;
	}

	public void setLocalWallpaperAdapter(
			LocalWallpaperAdapter localWallpaperAdapter) {
		this.localWallpaperAdapter = localWallpaperAdapter;
	}

	public LayoutInflater getInflater(Context context) {
		if (inflater == null)
			inflater = LayoutInflater.from(context);
		return inflater;
	}

	public void setInflater(LayoutInflater inflater) {
		this.inflater = inflater;
	}

	public LinearLayout getOutLayout(Context context) {
		if (outLayout == null) {
			initViews(context, "");
		}
		return outLayout;
	}

}
