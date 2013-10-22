package com.beatrich.acgwallpaper.asynctask;

import java.lang.reflect.Method;
import java.util.List;

import com.beatrich.acgwallpaper.model.PicTag;
import com.beatrich.acgwallpaper.model.TagsParam;
import com.beatrich.acgwallpaper.model.WallPaper;

public class AsyncTaskLoadTags extends
		BaseAsyncTask<Integer, Integer, List<PicTag>> {

	private Method mtGetData;
	private TagsParam mTagsParam;

	public AsyncTaskLoadTags(
			TaskListener<Integer, Integer, List<PicTag>> mTaskListener,
			Method mtGetData,TagsParam tagsParam) {
		super(mTaskListener);
		this.mtGetData = mtGetData;
		mTagsParam=tagsParam;
	 
	}

	@Override
	protected List<PicTag> doInBackground(Integer... page) {
		// TODO Auto-generated method stub
		List<PicTag> list = null;
		do {
			try {
				list = (List<PicTag>) mtGetData.invoke(null, mTagsParam);
				if (list != null && list.size() > 0) {
					return list;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (true);

		// return super.doInBackground(params);
	}
}
