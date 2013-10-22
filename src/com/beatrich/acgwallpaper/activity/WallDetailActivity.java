package com.beatrich.acgwallpaper.activity;

import greendroid.widget.AsyncImageView;

import java.io.IOException;
import java.net.URL;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beatrich.acgwallpaper.R;
import com.beatrich.acgwallpaper.asynctask.AsyncTaskLoadPic;
import com.beatrich.acgwallpaper.asynctask.BaseTaskListener;
import com.beatrich.acgwallpaper.model.WallPaper;

public class WallDetailActivity extends Activity {

	WallPaper wallPaper;
	private AsyncImageView ivDetail;
	private TextView tvProgress;
	private AsyncTaskLoadPic taskLoadPic;
	private BaseTaskListener<URL, Integer, Bitmap> listenerLoadPic;
	private Bitmap picBitmap;
	private ImageButton btnSetWallpaper;
	private Button btnReloadPic;
	private Boolean finished;
	private TaskSetWallPaper mTaskSetWallPaper;
	private Bitmap thumbBitmap;
	private LinearLayout topbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		initData();
		initViews();
		loadPic(false);

	}

	private void loadPic(boolean reload) {
		// TODO Auto-generated method stub
		if (taskLoadPic != null)
			taskLoadPic.cancel(true);
		taskLoadPic = new AsyncTaskLoadPic(listenerLoadPic, this,10,ivDetail.getWidth(),ivDetail.getHeight());

		try {
			URL url = new URL(wallPaper.getPicUrl());
			taskLoadPic.setReload(reload);
//			taskLoadPic.setmThreadCount(10);
			// ThreadLoadPic threadLoadPic=new ThreadLoadPic(this, threadPool,
			// url);
			// threadLoadPic.start();
			taskLoadPic.execute(url);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void initViews() {
		// TODO Auto-generated method stub
		setContentView(R.layout.layout_detail);
		topbar=(LinearLayout)findViewById(R.id.topbar_detail);
		topbar.getBackground().setAlpha((int)(255*0.6));
		ivDetail = (AsyncImageView) findViewById(R.id.iv_detail);
		// ivDetail.setImageResource(R.drawable.ic_launcher);
		tvProgress = (TextView) findViewById(R.id.tv_progress);
		tvProgress.setText("大图载入中\nsakamoto桑正直的凝视你中");
		btnSetWallpaper = (ImageButton) findViewById(R.id.btn_set_wallpaper);
		ivDetail.setDefaultImageResource(R.drawable.loadpic);
		ivDetail.setUrl(wallPaper.getThumbUrl());
		btnSetWallpaper.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (finished && picBitmap != null) {
					if (mTaskSetWallPaper != null) {
						mTaskSetWallPaper.cancel(true);
					}
					mTaskSetWallPaper = new TaskSetWallPaper();
					mTaskSetWallPaper.execute();
				} else {
					Toast.makeText(getApplicationContext(), "图片未下载完整，不可设置壁纸",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		btnReloadPic = (Button) findViewById(R.id.btn_detail_reload_pic);

		btnReloadPic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loadPic(true);

			}
		});

		// System.out.println("url"+wallPaper);
	}

	private void initData() {
		// TODO Auto-generated method stub
		wallPaper = (WallPaper) getIntent().getExtras().get("wallPaper");
		finished = false;
		
		if (wallPaper == null) {
			Toast.makeText(WallDetailActivity.this, "抱歉，暂未找到此图",
					Toast.LENGTH_SHORT).show();
			this.finish();

		}
		listenerLoadPic = new BaseTaskListener<URL, Integer, Bitmap>() {

			@Override
			public void onPostExecute(Bitmap result) {
				// TODO Auto-generated method stub
				picBitmap = result;
				// tvProgress.setVisibility(View.GONE);
				
				
				
				ivDetail.setImageBitmap(picBitmap);
				finished = true;
				ivDetail.setBackgroundDrawable(null);
				tvProgress.setText("加载完了~\\^o^/~");
				super.onPostExecute(result);
			}

			@Override
			public void onProgressUpdate(Integer... values) {
				// TODO Auto-generated method stub
				super.onProgressUpdate(values);
				tvProgress.setText("已下载:" + values[0] + "%\n" + values[1]
						/ 1000 + "KB/" + values[2] / 1000 + "KB");
				// picBitmap = BitmapFactory.decodeFile(taskLoadPic
				// .getDrawableFile().getAbsolutePath());
				// ivDetail.setImageBitmap(picBitmap);
			}

		};

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (picBitmap != null) {
			picBitmap.recycle();
		}
		if (taskLoadPic != null) {
			taskLoadPic.cancel(true);
		}

		super.onDestroy();
	}

	class TaskSetWallPaper extends AsyncTask<Void, Void, Integer> {
		private Dialog pbDialog;

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {
				getApplicationContext().setWallpaper(picBitmap);
				return 1;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (pbDialog != null)
				pbDialog.dismiss();

			pbDialog = new ProgressDialog(WallDetailActivity.this);
			pbDialog.setCancelable(true);
			pbDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
					cancel(true);
				}
			});

			pbDialog.setTitle("正在设置壁纸，请稍等");
			pbDialog.show();
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (pbDialog != null)
				pbDialog.dismiss();
			switch (result) {
			case 1:
				Toast.makeText(getApplicationContext(), "设置壁纸成功",
						Toast.LENGTH_SHORT).show();
				break;
			case -1:
				Toast.makeText(getApplicationContext(), "设置壁纸失败，请重试",
						Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
		}

	};

}
