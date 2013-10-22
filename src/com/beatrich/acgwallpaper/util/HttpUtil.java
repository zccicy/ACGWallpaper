package com.beatrich.acgwallpaper.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.graphics.drawable.Drawable;

import com.beatrich.acgwallpaper.common.BRConstants;

//�趨Ϊ����ģʽ
public class HttpUtil {
	public static HttpGet getHttpGet(String url) {
		HttpGet request = new HttpGet(url);
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params,
				BRConstants.CONNECTION_TIME_OUT_VALUE);
		request.setParams(params);
		return request;

	}

	public static HttpPost getHttpPost(String url) {
		HttpPost request = new HttpPost(url);
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params,
				BRConstants.CONNECTION_TIME_OUT_VALUE);
		request.setParams(params);
		return request;

	}

	public static HttpResponse getHttpResponse(HttpGet request)
			throws ClientProtocolException, IOException {
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;

	}

	public static HttpResponse getHttpResponse(HttpPost request)
			throws ClientProtocolException, IOException {
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}

	public static String getHttpTextContentFromResponse(HttpResponse response)
			throws Exception {

		if (response.getStatusLine().getStatusCode() == 200) {
			String result = null;
			try {
				result = EntityUtils.toString(response.getEntity());

			} catch (Exception e) {
				e.printStackTrace();
			}

			return result;

		}

		else {
			return null;
		}

	}

	public static boolean getFileFromUrl(String localFile, String remoteUrl) {
		// StringBuffer sb = new StringBuffer();
		try {
			File file = new File(localFile);
			URL url = new URL(remoteUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStream is = conn.getInputStream();

			FileOutputStream fos = new FileOutputStream(file);
			byte[] buf = new byte[65535];
			if (conn.getResponseCode() >= 400) {
				return false;
			} else {
				while (true) {
					if (is != null) {
						int numRead = is.read(buf);
						if (numRead <= 0) {
							break;
						} else {
							fos.write(buf, 0, numRead);
						}
					} else {
						break;
					}
				}
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

	}

	private static final int BUFFER_IO_SIZE = 8000;

	// public static Drawable getDrawableFromWeb(final String url) {
	// try {
	// // Addresses bug in SDK :
	// //
	// http://groups.google.com/group/android-developers/browse_thread/thread/4ed17d7e48899b26/
	// File file = new File(BRConstants.PATH_PIC + StringUtil.Md5(url)
	// + ".jpg");
	//
	// if (file.exists() && file.length() > 0) {
	// try {
	//
	// BitmapDrawable bitmapDrawable = new BitmapDrawable(
	// BitmapFactory.decodeFile(file.getAbsolutePath()));
	// return bitmapDrawable;
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	// }
	// BufferedInputStream bis = new BufferedInputStream(
	// new URL(url).openStream(), BUFFER_IO_SIZE);
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// BufferedOutputStream bos = new BufferedOutputStream(baos,
	// BUFFER_IO_SIZE);
	// copy(bis, bos);
	// bos.flush();
	//
	// Bitmap bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(),
	// 0, baos.size());
	// WriteFileThread writeFileThread = new WriteFileThread(baos, file);
	// writeFileThread.start();
	//
	// return new BitmapDrawable(bitmap);
	// } catch (Exception e) {
	// // handle it properly
	// e.printStackTrace();
	// // try {
	// // SessionFactory.utilServiceMessenger
	// // .send(MessageHelper
	// //
	// .obtainCustomMessage(GlobalConstants.MSG_SERVICE_UTIL_PIC_DOWNLOAD_EXCEPTION));
	// // } catch (RemoteException e1) {
	// // // TODO Auto-generated catch block
	// // e1.printStackTrace();
	// // }
	// return null;
	// }
	// }

	private static void copy(final InputStream bis, final OutputStream baos)
			throws IOException {
		byte[] buf = new byte[256];
		int l;
		while ((l = bis.read(buf)) >= 0)
			baos.write(buf, 0, l);
	}

	public static Drawable loadImageFromUrl(String url) {
		URL m;
		InputStream is = null;
		try {
			m = new URL(url);
			is = (InputStream) m.getContent();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		Drawable d = Drawable.createFromStream(is, "src");
		return d;
	}

	// public static List<String> getLocalDataPath(String package_name, int
	// appId,
	// int categoryId) {
	// List<String> list = new ArrayList<String>();
	// if (FileUtil.isSDCardAvailable()) {
	// list.add(FileUtil.getSDCardPath()
	// + package_name.substring(package_name.lastIndexOf(".") + 1)
	// + "." + appId + "." + categoryId + ".apk");
	// } else {
	// list.add(FileUtil.getLocalPath() + package_name + "." + appId + "."
	// + categoryId + ".apk");
	// }
	// return list;
	// }
	//
	// public static List<String> getTmpPath(String package_name, int appId,
	// int categoryId) {
	// List<String> list = new ArrayList<String>();
	// if (FileUtil.isSDCardAvailable()) {
	// list.add(FileUtil.getSDCardPath()
	// + package_name.substring(package_name.lastIndexOf(".") + 1)
	// + "." + appId + "." + categoryId + ".tmp");
	// } else {
	// list.add(FileUtil.getLocalPath() + package_name + "." + appId + "."
	// + categoryId + ".tmp");
	// }
	// return list;
	//
	// }

	// public static void setTaskFileSize(TaskList taskList) {
	// if (taskList.getApp_size() == 0) {
	// int size = getFileSize(taskList.getDownload_path());
	// if (size != 0) {
	// taskList.setApp_size(size);
	// }
	//
	// }
	// }

	public static int getFileSize(String urlPath) {
		URL url;
		int fileSize = 0;
		do {
			try {
				url = new URL(urlPath);
				URLConnection conn = url.openConnection();
				conn.setConnectTimeout(BRConstants.CONNECTION_TIME_OUT_VALUE);
				fileSize = conn.getContentLength();
				return fileSize;
			} catch (Exception e) {
				// TODO: handle exception
			}
		} while (true);
		

	}

	public static String getResultFromUrlByPost(String url,
			List<NameValuePair> params) throws Exception {

		HttpPost request = HttpUtil.getHttpPost(url);
		request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		HttpResponse hr = HttpUtil.getHttpResponse(request);
		String result = EntityUtils.toString(hr.getEntity());
		return result;

	}

	public static String getResultFromUrlByGet(String url) throws Exception

	{
		try {
			HttpGet request = getHttpGet(url);
			HttpResponse response = getHttpResponse(request);
			String result = EntityUtils.toString(response.getEntity());
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;

		}

	}

}
