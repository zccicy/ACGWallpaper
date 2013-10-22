package com.beatrich.acgwallpaper.asynctask;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.webkit.MimeTypeMap;

import com.beatrich.acgwallpaper.model.WallPaper;
import com.beatrich.acgwallpaper.util.ImageUtil;

public class AsyncTaskLoadLocalPic extends
		BaseAsyncTask<File, Integer, List<WallPaper>> implements
		FilenameFilter {
	private String mFilter = "jpg#png#bmp#png";
 
	private Context mContext;
	private int mWidth = 400;
	private int mHeight = 200;

	public AsyncTaskLoadLocalPic(
			TaskListener<File, Integer, List<WallPaper>> mTaskListener, Context context) {
		super(mTaskListener);
	 
		mContext = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected List<WallPaper> doInBackground(File... params) {
		// TODO Auto-generated method stub
		return FetchPicInFolder(params[0]);
	}

	private List<WallPaper> FetchPicInFolder(File path) {
		// TODO Auto-generated method stub
		if (path == null) {
			return null;
		}
		File[] files = path.listFiles(this);
		if (files.length <= 0)
			return null;

		List<WallPaper> list = new ArrayList<WallPaper>();
		for (int i = 0; i < files.length; i++) {
			if (files[i] == null)
				continue;
			WallPaper wallPaper = new WallPaper();
//			wallPaper.setInfo("file");
			wallPaper.setPicUrl(files[i].getAbsolutePath());
			BitmapFactory.Options option = new BitmapFactory.Options();
			option.inJustDecodeBounds = true;
			Bitmap measure = BitmapFactory.decodeFile(
					files[i].getAbsolutePath(), option);
			option.inSampleSize = ImageUtil.computeSampleSize(option, mHeight,
					mWidth * mHeight);
			option.inJustDecodeBounds = false;
			wallPaper.setOptions(option);
			list.add(wallPaper);
		}
		return list;

	}

	@Override
	public boolean accept(File dir, String filename) {
		// TODO Auto-generated method stub
		if (filename.startsWith("."))
			return false;
		File file = new File(String.format("%s/%s", dir.getAbsolutePath(),
				filename));
		if (file.isHidden())
			return false;
		if (file.isDirectory())
			return false;
		String name = filename.toLowerCase();
		int dot = name.lastIndexOf('.');
		if (dot < 0 || dot == name.length() - 1)
			return false;
		String ext = name.substring(dot + 1).toLowerCase();
		String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);
		if (mFilter.indexOf(ext) >= 0
				|| (mime != null && mime.startsWith("video")))
			return true;
		return false;
	};

}
