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

import com.beatrich.acgwallpaper.model.PicTag;
import com.beatrich.acgwallpaper.model.TagsParam;

public class TagService {
	public static List<PicTag> getWallPaperInfoByPageNum(
			TagsParam tagsParam) {
		List<PicTag> tagsList = new ArrayList<PicTag>();
		if (tagsParam == null)
			return tagsList;
		String url ="";
		url+= TagsParam.url;
		if (tagsParam.getAfter_id() != null) {
			url += "after_id=" + tagsParam.getAfter_id() + "&";
		}
		if (tagsParam.getId() != null) {
			url += "id=" + tagsParam.getId() + "&";
		}
		if (tagsParam.getLimit() != null) {
			url += "limit=" + tagsParam.getLimit() + "&";
		}
		if (tagsParam.getName() != null) {
			url += "name=" + tagsParam.getName() + "&";
		}
		if (tagsParam.getName_pattern() != null) {
			url += "name_pattern=" + tagsParam.getName_pattern() + "&";
		}
		if (tagsParam.getOrder() != null) {
			url += "order=" + tagsParam.getOrder() + "&";
		}
		if (tagsParam.getPage() != null) {
			url += "page=" + tagsParam.getPage() + "&";
		}
		System.out.println("tag-url" + url);

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
					PicTag tag = new PicTag();
					tag.setType(jsonObject.getInt("type"));
					tag.setCount(jsonObject.getInt("count"));
					tag.setAmbiguous(jsonObject.getBoolean("ambiguous"));
					tag.setName(jsonObject.getString("name"));
					tag.setId(jsonObject.getInt("id"));

					tagsList.add(tag);
				}
				//

				return tagsList;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} while (true);

	}
}
