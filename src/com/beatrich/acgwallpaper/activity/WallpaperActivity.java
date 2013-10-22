package com.beatrich.acgwallpaper.activity;

import greendroid.widget.PageIndicator;
import greendroid.widget.PagedView;
import greendroid.widget.PagedView.OnPagedViewChangeListener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.beatrich.acgwallpaper.R;
import com.beatrich.acgwallpaper.adapter.PagerAdapter;
import com.beatrich.acgwallpaper.adapter.WallpaperAdapter;
import com.beatrich.acgwallpaper.asynctask.AsyncTaskLoadWallPaper;
import com.beatrich.acgwallpaper.asynctask.BaseTaskListener;
import com.beatrich.acgwallpaper.model.WallPaper;
import com.beatrich.acgwallpaper.model.WallPaperParam;
import com.beatrich.acgwallpaper.service.KonachanService;
import com.beatrich.acgwallpaper.ui.SettingDialogView;
import com.beatrich.acgwallpaper.ui.WallpaperPage;

/**
 * GridView分页显示安装的应用程�? * @author Yao.GUET blog: http://blog.csdn.net/Yao_GUET
 * date: 2011-05-05
 */

public class WallpaperActivity extends Activity {

	private PagedView mScrollLayout;
	private List<WallpaperPage> pages;
	private List<AsyncTaskLoadWallPaper> mLoadPicTasks;
	private int pageCount = 3;
	private int LIMIT = 10;

	private PagerAdapter adapter;
	private TextView tvPageNum;
	private View settingView;
	private ImageButton btnSearch;

	private Dialog exitDialog;

	// private Dialog detailShow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initData();
		initViews();

	}

	private void initData() {
		// TODO Auto-generated method stub
		pages = new ArrayList<WallpaperPage>();
		mLoadPicTasks = new ArrayList<AsyncTaskLoadWallPaper>();

	}

	class PageLoadListener extends
			BaseTaskListener<Integer, Integer, List<WallPaper>> {
		WallpaperPage mWallpaperPage;

		public PageLoadListener(WallpaperPage wallpaperPage) {
			// TODO Auto-generated constructor stub
			mWallpaperPage = wallpaperPage;

		}

		@Override
		public void onPreExecute() {
			// TODO Auto-generated method stub
			mWallpaperPage.showPb(WallpaperActivity.this);

			super.onPreExecute();
		}

		@Override
		public void onPostExecute(List<WallPaper> result) {
			// TODO Auto-generated method stub
			if (result != null && result.size() > 0) {
				mWallpaperPage.cleanPb(WallpaperActivity.this);
				mWallpaperPage.ShowGv(WallpaperActivity.this, result);

				// WallpaperActivity.this.getWindowManager().updateViewLayout(
				// mWallpaperPage.getGvPage(WallpaperActivity.this),
				// new
				// WindowManager.LayoutParams(WindowManager.LayoutParams.FILL_PARENT,
				// WindowManager.LayoutParams.FILL_PARENT));
				super.onPostExecute(result);
			}
		}

	};

	/**
	 * 获取系统�?��的应用程序，并根据APP_PAGE_SIZE生成相应的GridView页面
	 */
	public void initViews() {

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_main);
		btnSearch = (ImageButton) findViewById(R.id.btn_search);
		btnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(WallpaperActivity.this,
						SearchActivity.class));
			}
		});
		mScrollLayout = (PagedView) findViewById(R.id.pv_main);
		mScrollLayout.setOnPageChangeListener(mOnPagedViewChangedListener);
		tvPageNum = (TextView) findViewById(R.id.tv_page_num);

		adapter = new PagerAdapter(WallpaperActivity.this);
		adapter.setDataList(pages);
		mScrollLayout.setAdapter(adapter);

		initPicDownloadPage();

	}

	private void initPicDownloadPage() {
		// TODO Auto-generated method stub

		if (mScrollLayout.getChildCount() <= 0) {
			for (int i = 0; i < pageCount; i++) {
				increasePage(i);
			}

		}

	}

	private void increasePage(int i) {
		// TODO Auto-generated method stub
		WallpaperPage wallpaperPage = new WallpaperPage(this);
		pages.add(wallpaperPage);

		adapter.notifyDataSetChanged();
		wallpaperPage.getGvPage(WallpaperActivity.this).setOnItemClickListener(
				new gridViewListener());
		try {
			PageLoadListener listener = new PageLoadListener(wallpaperPage);
			Method mtMethod = KonachanService.class.getMethod(
					"getWallPaperInfoByPageNum", WallPaperParam.class);
			WallPaperParam wallPaperParam = new WallPaperParam();
			wallPaperParam.setLimit(LIMIT);
			wallPaperParam.setPage(i + 1);
			wallPaperParam.setTags("");
			AsyncTaskLoadWallPaper loadPicTask = new AsyncTaskLoadWallPaper(
					listener, mtMethod, wallPaperParam);
			if (mLoadPicTasks.size() > i && mLoadPicTasks.get(i) != null) {
				mLoadPicTasks.get(i).cancel(true);
				mLoadPicTasks.set(i, loadPicTask);
			} else {
				mLoadPicTasks.add(loadPicTask);
			}
			loadPicTask.execute(i + 1);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * gridView 的onItemLick响应事件
	 */
	class gridViewListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			Intent intent = new Intent(WallpaperActivity.this,
					WallDetailActivity.class);
			GridView gv = (GridView) parent;
			WallpaperAdapter adapter = (WallpaperAdapter) gv.getAdapter();
			intent.putExtra("wallPaper", adapter.getDataList().get(position));

			startActivity(intent);
		}

	};

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

			if (newPage + 2 > pageCount) {
				increasePage(pageCount);
				pageCount++;
			}
		}
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		// android.os.Process.killProcess(android.os.Process.myPid());
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (exitDialog != null)
				exitDialog.dismiss();
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("退出");
			builder.setPositiveButton("确定", new Dialog.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			builder.setNegativeButton("取消", null);

			exitDialog = builder.create();
			exitDialog.show();

			// finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
