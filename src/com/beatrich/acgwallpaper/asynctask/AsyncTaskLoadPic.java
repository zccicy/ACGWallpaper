package com.beatrich.acgwallpaper.asynctask;

import greendroid.util.GDUtils;
import greendroid.util.Md5Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Process;
import android.widget.SlidingDrawer;

import com.beatrich.acgwallpaper.asynctask.FileDownloadThread.FetchListener;
import com.beatrich.acgwallpaper.util.DataProcUtil;
import com.beatrich.acgwallpaper.util.HttpUtil;
import com.beatrich.acgwallpaper.util.ImageUtil;
import com.beatrich.acgwallpaper.util.StorageUtils;

public class AsyncTaskLoadPic extends BaseAsyncTask<URL, Integer, Bitmap> {

	private File drawableFile;
	private File tmpFile;
	private Context mContext;
	private int mThreadCount = 10;
	private List<FileDownloadThread> mThreadList;
	private FetchListener mFetchListener;
	private int downloadedSize;
	private int totalSize;
	private ExecutorService mThreadPool;
	private boolean reload;
	private int mWidth;
	private int mHeight;

	// private FileDownloadThread

	public AsyncTaskLoadPic(TaskListener<URL, Integer, Bitmap> mTaskListener,
			Context context, int threadCount,int width,int height) {
		super(mTaskListener);
		mContext = context;
		mWidth=width;
		mHeight=height;
		mThreadList = new ArrayList<FileDownloadThread>();
		mThreadPool = Executors.newFixedThreadPool(mThreadCount + 5);
		reload = false;
		mFetchListener = new FetchListener() {

			@Override
			public void listenProgress(int increaseSize) {
				// TODO Auto-generated method stub
				downloadedSize += increaseSize;
				AsyncTaskLoadPic.this.publishProgress(
						DataProcUtil.getPercent(downloadedSize, totalSize),
						downloadedSize, totalSize);
			}

			@Override
			public void downloadFailedListener() {
				// TODO Auto-generated method stub

			}
		};
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Bitmap doInBackground(URL... urls) {
		// TODO Auto-generated method stub
		drawableFile = new File(StorageUtils.getCacheDirectory(mContext)
				.getAbsoluteFile()
				+ "/"
				+ urls[0].getFile().substring(
						urls[0].getFile().lastIndexOf('/') + 1));
		tmpFile=new File(StorageUtils.getCacheDirectory(mContext)
				.getAbsoluteFile()
				+ "/"
				+ urls[0].getFile().substring(
						urls[0].getFile().lastIndexOf('/') + 1,urls[0].getFile().lastIndexOf('.'))+".tmp");
		// if (totalSize <= 0)
		// return null;
		try {
			if (!tmpFile.exists())
				tmpFile.createNewFile();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		
		do {
			totalSize = HttpUtil.getFileSize(urls[0].toString());
		} while (totalSize<=0);
		if (drawableFile.exists()&&drawableFile.length() == totalSize && !reload)
			return BitmapFactory.decodeFile(drawableFile.getAbsolutePath());
		int blockSize = totalSize / mThreadCount;
		// System.out.println("blocksize"+blockSize);
		
		for (int i = 0; i < mThreadCount; i++) {
			FileDownloadThread fileDownloadThread;
			// System.out.println();
			if (i < (mThreadCount - 1)) {
				fileDownloadThread = new FileDownloadThread(urls[0],
						drawableFile, i * blockSize, (i + 1) * blockSize - 1);
			} else {
				fileDownloadThread = new FileDownloadThread(urls[0],
						drawableFile, i * blockSize, totalSize);
			}
			fileDownloadThread.setPriority(Thread.MAX_PRIORITY);
			mThreadList.add(fileDownloadThread);
			fileDownloadThread.setmFetchListener(mFetchListener);
			try {
				// fileDownloadThread.start();
				mThreadPool.execute(fileDownloadThread);
				// mThreadPool.submit(task)
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
		}

		// for (FileDownloadThread thread : mThreadList) {
		// if (thread != null) {
		// synchronized (thread) {
		// while (!thread.isFinished()) {
		// try {
		// thread.wait();
		// System.out.println("loadDownloadsize" + thread.getId()
		// + "--" + thread.getDownloadSize());
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// }
		//
		// }
		// }
		mThreadPool.shutdown();
		while (!mThreadPool.isTerminated())
			;
		drawableFile.renameTo(new File(StorageUtils.getCacheDirectory(mContext)
				.getAbsoluteFile()
				+ "/"
				+ urls[0].getFile().substring(
						urls[0].getFile().lastIndexOf('/') + 1)));
		System.out
				.println("--------------------loadcomplete------------------"+mWidth+"-"+mHeight);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds=true;
		Bitmap measure = BitmapFactory.decodeFile(drawableFile.getAbsolutePath(),options);
		 
		options.inSampleSize = ImageUtil.computeSampleSize(
				options,
				mHeight,mWidth*mHeight);
		options.inJustDecodeBounds = false;
		try {
			Bitmap bmp = BitmapFactory.decodeFile(drawableFile.getAbsolutePath(),
					options);
			return bmp;
//			GDUtils.getImageCache(mContext).put(drawableFile.getAbsolutePath(), bmp);
			 
		} catch (OutOfMemoryError err) {
			return null;
		}
		

	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub

		System.out.println("loadcanceltrue------------------");
		mThreadPool.shutdownNow();
		super.onCancelled();
	}

	public File getDrawableFile() {
		return drawableFile;
	}

	public void setDrawableFile(File drawableFile) {
		this.drawableFile = drawableFile;
	}

	public boolean isReload() {
		return reload;
	}

	public void setReload(boolean reload) {
		this.reload = reload;
	}

	public int getmWidth() {
		return mWidth;
	}

	public void setmWidth(int mWidth) {
		this.mWidth = mWidth;
	}

	public int getmHeight() {
		return mHeight;
	}

	public void setmHeight(int mHeight) {
		this.mHeight = mHeight;
	}
	

}
