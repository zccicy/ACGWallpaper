package com.beatrich.acgwallpaper.util;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * Provides application storage paths
 * 
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public final class StorageUtils {

	private static final String INDIVIDUAL_DIR_NAME = "uil-images";

	private StorageUtils() {
	}

	/**
	 * Returns application cache directory. Cache directory will be created on SD card
	 * <i>("/Android/[app_package_name]/cache")</i> if card is mounted. Else - Android defines cache directory on
	 * device's file system.
	 * 
	 * @param context
	 *            Application context
	 * @return Cache {@link File directory}
	 */
	public static File getCacheDirectory(Context context) {
		File appCacheDir = null;
		if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			appCacheDir = getFavorDir(context);
		}
		if (appCacheDir == null) {
			appCacheDir = context.getCacheDir();
		}
		return appCacheDir;
	}
	//获取本地缓存地址
	public static File getLocalCacheDir(Context context)
	{
		return context.getCacheDir();
	}
	//获取收藏文件夹地址
	public static File getFavorDir(Context context)
	{
		File dataDir = new File(Environment.getExternalStorageDirectory(), "data");
		File appCacheDir = new File(new File(dataDir, context.getPackageName()), "pic");
		if (!appCacheDir.exists()) {
			try {
				new File(dataDir, ".nomedia").createNewFile();
			} catch (IOException e) {
				Log.w("ACG_WALLPaper", "Can't create \".nomedia\" file in application external favor directory", e);
			}
			if (!appCacheDir.mkdirs()) {
				Log.w("ACG_WALLPaper", "Unable to create external favor directory");
				return null;
			}
		}
		return appCacheDir;
	}
	/**
	 * Returns individual application cache directory (for only image caching from ImageLoader). Cache directory will be
	 * created on SD card <i>("/Android/data/[app_package_name]/cache/uil-images")</i> if card is mounted. Else -
	 * Android defines cache directory on device's file system.
	 * 
	 * @param context
	 *            Application context
	 * @return Cache {@link File directory}
	 */
	public static File getIndividualCacheDirectory(Context context) {
		File cacheDir = getCacheDirectory(context);
		File individualCacheDir = new File(cacheDir, INDIVIDUAL_DIR_NAME);
		if (!individualCacheDir.exists()) {
			if (!individualCacheDir.mkdir()) {
				individualCacheDir = cacheDir;
			}
		}
		return individualCacheDir;
	}

	public static File getExternalCacheDir(Context context) {
		File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
		File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
		if (!appCacheDir.exists()) {
			try {
				new File(dataDir, ".nomedia").createNewFile();
			} catch (IOException e) {
				Log.w("ACG_WALLPaper", "Can't create \".nomedia\" file in application external cache directory", e);
			}
			if (!appCacheDir.mkdirs()) {
				Log.w("ACG_WALLPaper", "Unable to create external cache directory");
				return null;
			}
		}
		return appCacheDir;
	}
}
