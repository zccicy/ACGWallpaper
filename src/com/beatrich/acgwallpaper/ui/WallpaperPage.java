package com.beatrich.acgwallpaper.ui;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.beatrich.acgwallpaper.R;
import com.beatrich.acgwallpaper.activity.WallpaperActivity;
import com.beatrich.acgwallpaper.adapter.BaseDataAdapter;
import com.beatrich.acgwallpaper.adapter.WallpaperAdapter;
import com.beatrich.acgwallpaper.model.WallPaper;

public class WallpaperPage {
	private GridView gvPage;
	private LinearLayout outLayout;
	private LinearLayout lyLoadPage;
	private BaseDataAdapter<WallPaper> adapter;
	private LayoutInflater inflater;

	public WallpaperPage(Context context) {
		initViews(context);

	}

	/**
	 * @param context
	 */
	private void initViews(Context context) {
		outLayout = (LinearLayout) getInflater(context).inflate(
				R.layout.layout_wallpaper_page, null);
		outLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		
		outLayout.setGravity(Gravity.CENTER);

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
		getAdapter(context).setDataList(dataList);
		getGvPage(context).setAdapter(getAdapter(context));

		// getGvPage(context).requestFocus();

		// appAdapter.notifyDataSetInvalidated();

		getOutLayout(context).addView(getGvPage(context));
		getAdapter(context).notifyDataSetChanged();

		// getGvPage(context).requestLayout();
		// getGvPage(context).invalidate();
		// getGvPage(context).invalidateViews();
		// getAppAdapter(context).notifyDataSetChanged();

		// getOutLayout(context).getParent().requestLayout();
		//
		// getOutLayout(context).requestFocus();
		// getOutLayout(context).invalidate();
		//
		// getOutLayout(context).requestLayout();

	}

	public void cleanGv(Context context)

	{
		outLayout.removeView(gvPage);
	}

	public GridView getGvPage(Context context) {
		if (gvPage == null) {
			gvPage = new GridView(context);
			// gvPage.setLayoutParams(new LinearLayout.LayoutParams(
			// LinearLayout.LayoutParams.FILL_PARENT,
			// LinearLayout.LayoutParams.FILL_PARENT));

			gvPage.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT,
					LinearLayout.LayoutParams.FILL_PARENT));
//			gvPage.setPadding(0, 150, 0, 0);
			gvPage.setNumColumns(2);
		}
		return gvPage;
	}

	public void setGvPage(GridView gvPage) {
		this.gvPage = gvPage;
	}

	public BaseDataAdapter<WallPaper> getAdapter(Context context) {
		if (adapter == null)
			adapter = new WallpaperAdapter(context);
		return adapter;
	}

	public void setAdapter(BaseDataAdapter<WallPaper> adapter) {
		this.adapter = adapter;
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
			initViews(context);
		}
		return outLayout;
	}

	public void setOutLayout(LinearLayout outLayout) {
		this.outLayout = outLayout;
	}

}
