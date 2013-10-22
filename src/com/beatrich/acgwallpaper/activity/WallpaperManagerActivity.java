package com.beatrich.acgwallpaper.activity;

import greendroid.widget.PageIndicator;
import greendroid.widget.PagedView;
import greendroid.widget.PagedView.OnPagedViewChangeListener;
import greendroid.widget.QuickAction;
import greendroid.widget.QuickActionGrid;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.beatrich.acgwallpaper.R;
import com.beatrich.acgwallpaper.adapter.LocalWallpaperAdapter;
import com.beatrich.acgwallpaper.adapter.PagerAdapter;
import com.beatrich.acgwallpaper.asynctask.AsyncTaskLoadLocalPic;
import com.beatrich.acgwallpaper.asynctask.BaseTaskListener;
import com.beatrich.acgwallpaper.model.WallPaper;
import com.beatrich.acgwallpaper.ui.WallpaperPage;
import com.beatrich.acgwallpaper.util.StorageUtils;

public class WallpaperManagerActivity extends Activity {

	private PagedView mScrollLayout;

	private AsyncTaskLoadLocalPic taskLoadPic;
	private List<WallpaperPage> pages;
	private TextView tvPageNum;
	private int pageNum;
	private int NUMBER_PER_PAGE = 12;
	private PagerAdapter pageAdapter;

	private List<File> searchDirs;
	private static int pageCount = 1;
	// private static int PAGE_MAX_INDEX = 0;

	private QuickActionGrid quickActionGrid;
	private Dialog deleteDialog;
 

	private PageIndicator mPageIndicatorNext;
	private PageIndicator mPageIndicatorPrev;
	private PageIndicator mPageIndicatorOther;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_wallpaper_manage);
		initData();
		initViews();

	}

	private void initData() {
		// TODO Auto-generated method stub
		pages = new ArrayList<WallpaperPage>();

	}

	private List<File> getSearchFiles() {
		// TODO Auto-generated method stub
		if (searchDirs == null || searchDirs.size() <= 0) {

			searchDirs = new ArrayList<File>();
			
			searchDirs.add(StorageUtils.getLocalCacheDir(this));
//			searchDirs.add(StorageUtils.getExternalCacheDir(this));
			searchDirs.add(StorageUtils.getFavorDir(this));
		}
		return searchDirs;
	}

	class PageLoadListener extends
			BaseTaskListener<File, Integer, List<WallPaper>> {
		WallpaperPage mWallpaperPage;

		public PageLoadListener(WallpaperPage wallpaperPage) {
			// TODO Auto-generated constructor stub
			mWallpaperPage = wallpaperPage;

		}

		@Override
		public void onPreExecute() {
			// TODO Auto-generated method stub
			mWallpaperPage.showPb(WallpaperManagerActivity.this);
			super.onPreExecute();
		}

		@Override
		public void onPostExecute(List<WallPaper> result) {
			// TODO Auto-generated method stub
			if (result != null && result.size() > 0) {

				// mWallpaperPage.getAdapter(WallpaperManagerActivity.this)
				// .setDataList(result);
				mWallpaperPage.cleanPb(WallpaperManagerActivity.this);
				mWallpaperPage.ShowGv(WallpaperManagerActivity.this, result);
				mWallpaperPage.getGvPage(WallpaperManagerActivity.this)
						.setOnItemClickListener(new gridViewListener());
				tvPageNum.setText("保存目录"+((LocalWallpaperAdapter)mWallpaperPage.getAdapter(WallpaperManagerActivity.this)).getmPath());
				// mWallpaperPage.getAdapter(WallpaperManagerActivity.this).notifyDataSetChanged();
				super.onPostExecute(result);
			}
		}

	};

	public void initViews() {
		mScrollLayout = (PagedView) findViewById(R.id.pv_manage);
		mScrollLayout.setOnPageChangeListener(mOnPagedViewChangedListener);
		tvPageNum = (TextView) findViewById(R.id.tv_page_num);

		pageAdapter = new PagerAdapter(WallpaperManagerActivity.this);

		pageAdapter.setDataList(pages);
		mScrollLayout.setAdapter(pageAdapter);
		initPicDownloadPage();

		mPageIndicatorNext = (PageIndicator) findViewById(R.id.page_indicator_next);
		mPageIndicatorNext.setDotCount(pageCount);
		mPageIndicatorNext.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mScrollLayout.smoothScrollToNext();
			}
		});

		mPageIndicatorPrev = (PageIndicator) findViewById(R.id.page_indicator_prev);
		mPageIndicatorPrev.setDotCount(pageCount);
		mPageIndicatorPrev.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mScrollLayout.smoothScrollToPrevious();
			}
		});

		mPageIndicatorOther = (PageIndicator) findViewById(R.id.page_indicator_other);
		mPageIndicatorOther.setDotCount(pageCount);

		setActivePage(mScrollLayout.getCurrentPage());

	}

	private void setActivePage(int page) {
		mPageIndicatorOther.setActiveDot(page);
		mPageIndicatorNext.setActiveDot(pageCount - page);
		mPageIndicatorPrev.setActiveDot(page);
	}

	private void initPicDownloadPage() {
		// TODO Auto-generated method stub

		if (mScrollLayout.getChildCount() <= 0) {
			for (File dir : getSearchFiles()) {
				if (dir == null || !dir.exists())
					continue;
				if (dir.list().length <= 0
						&& dir != StorageUtils.getFavorDir(this))
					continue;
				WallpaperPage wallpaperPage = new WallpaperPage(
						getApplicationContext());
				pages.add(wallpaperPage);
				wallpaperPage.setAdapter(new LocalWallpaperAdapter(this,dir.getAbsolutePath()));
				AsyncTaskLoadLocalPic loader = new AsyncTaskLoadLocalPic(
						new PageLoadListener(wallpaperPage), this);
				loader.execute(dir);
			}

			pageCount = pages.size();
			// PAGE_MAX_INDEX = pages.size() - 1;
			pageAdapter.notifyDataSetChanged();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	/**
	 * gridView 的onItemLick响应事件
	 */
	class gridViewListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> parent, View view,
				final int position, long id) {
			if (quickActionGrid != null)
				quickActionGrid.dismiss();

			quickActionGrid = new QuickActionGrid(WallpaperManagerActivity.this);
			quickActionGrid.addQuickAction(new MyQuickAction(
					WallpaperManagerActivity.this,
					R.drawable.gd_action_bar_compose, R.string.open));

			quickActionGrid.addQuickAction(new MyQuickAction(
					WallpaperManagerActivity.this,
					R.drawable.gd_action_bar_compose, R.string.delete));
			GridView gv = (GridView) parent;
			final LocalWallpaperAdapter adapter = (LocalWallpaperAdapter) gv
					.getAdapter();
			quickActionGrid
					.setOnQuickActionClickListener(new OnQuickActionClickListener() {

						@Override
						public void onQuickActionClicked(
								QuickActionWidget widget, int mPosition) {
							// TODO Auto-generated method stub
							switch (mPosition) {
							case 0:

								OpenImageFile(adapter.getDataList()
										.get(position).getPicUrl());
								break;

							case 1:
								DeleteFile(adapter.getDataList().get(position)
										.getPicUrl(), adapter, position);
								break;
							default:
								break;
							}
						}

					});
			quickActionGrid.show(view);

		}

	}

	protected void OpenImageFile(String path) {
		// TODO Auto-generated method stub
		System.out.println("path"+path);
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(path));
		intent.setDataAndType(uri, "image/*");
		startActivity(intent);

	}

	private void DeleteFile(final String picUrl,
			final LocalWallpaperAdapter adapter, final int position) {
		// TODO Auto-generated method stub
		if (deleteDialog != null)
			deleteDialog.dismiss();
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("确定♂删除"
				+ picUrl.substring(picUrl.lastIndexOf('/') + 1) + "么");
		builder.setPositiveButton("请务必温柔的删除", new Dialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				File file = new File(picUrl);
				if (file.exists())
					file.delete();
				Toast.makeText(WallpaperManagerActivity.this, "删。。删掉了，狗修金sama",
						Toast.LENGTH_SHORT).show();
				adapter.getDataList().remove(position);
				adapter.notifyDataSetChanged();

			}
		});
		builder.setNegativeButton("谁说过要删掉你啊杂种", null);
		deleteDialog = builder.create();
		deleteDialog.setCancelable(true);
		deleteDialog.show();
	}

	private static class MyQuickAction extends QuickAction {

		private static final ColorFilter BLACK_CF = new LightingColorFilter(
				Color.BLACK, Color.BLACK);

		public MyQuickAction(Context ctx, int drawableId, int titleId) {
			super(ctx, buildDrawable(ctx, drawableId), titleId);
		}

		private static Drawable buildDrawable(Context ctx, int drawableId) {
			Drawable d = ctx.getResources().getDrawable(drawableId);
			d.setColorFilter(BLACK_CF);
			return d;
		}

	}

	private OnPagedViewChangeListener mOnPagedViewChangedListener = new OnPagedViewChangeListener() {

		@Override
		public void onStopTracking(PagedView pagedView) {
		}

		@Override
		public void onStartTracking(PagedView pagedView) {
		}

		@Override
		public void onPageChanged(PagedView pagedView, int previousPage,
				int newPage) {
			setActivePage(newPage);
			// if (newPage + 2 > pageCount) {
			// increasePage(pageCount);
			// pageCount++;
			// }
		}
	};

}
