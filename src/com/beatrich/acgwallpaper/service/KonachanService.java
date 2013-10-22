package com.beatrich.acgwallpaper.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.beatrich.acgwallpaper.model.WallPaper;
import com.beatrich.acgwallpaper.model.WallPaperParam;

public class KonachanService {

	public static List<WallPaper> getWallPaperInfoByPageNum(
			WallPaperParam wallPaperParam) {
		List<WallPaper> wallPaperList = new ArrayList<WallPaper>();
		String url = "";
		url += WallPaperParam.url;
		if (wallPaperParam.getPage() != null) {
			url += "page=" + wallPaperParam.getPage() + "&";
		}
		if (wallPaperParam.getLimit() != null) {
			url += "limit=" + wallPaperParam.getLimit() + "&";
		}
		if (wallPaperParam.getTags() != null) {
			url += "tags=" + wallPaperParam.getTags() + "&";
		}
//		System.out.println("url" + url);

		HttpGet httpGet = new HttpGet(url);
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		// The default value is zero, that means the timeout is not used.
		int timeoutConnection = 3000;
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT)
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 5000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
		do {
			try {

				HttpResponse response = httpClient.execute(httpGet);

				JSONArray jsonArray = new JSONArray(
						EntityUtils.toString(response.getEntity()));

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					WallPaper wallPaper = new WallPaper();
					wallPaper.setThumbUrl(jsonObject.getString("preview_url"));
					wallPaper.setPicUrl(jsonObject.getString("file_url"));

					wallPaper.setWidth(jsonObject.getInt("jpeg_width"));
					wallPaper.setHeight(jsonObject.getInt("jpeg_height"));
					wallPaper.setTags(jsonObject.getString("tags"));
					// System.out.println("wh"+wh.html());
					// wallPaper.setWidth(Integer.parseInt(wh.html().split("X")[0]
					// .trim()));
					// wallPaper.setHeight(Integer
					// .parseInt(wh.html().split("X")[1].trim()));
					wallPaperList.add(wallPaper);
				}
				//

				return wallPaperList;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} while (true);

	}
}
