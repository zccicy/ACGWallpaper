package com.beatrich.acgwallpaper.asynctask;

import java.lang.reflect.Method;
import java.util.List;

import com.beatrich.acgwallpaper.model.WallPaper;
import com.beatrich.acgwallpaper.model.WallPaperParam;

public class AsyncTaskLoadWallPaper extends
		BaseAsyncTask<Integer, Integer, List<WallPaper>> {

	private Method mtGetData;
	private WallPaperParam mWallPaperParam;

	public AsyncTaskLoadWallPaper(
			TaskListener<Integer, Integer, List<WallPaper>> mTaskListener,
			Method mtGetData, WallPaperParam wallPaperParam) {
		super(mTaskListener);
		this.mtGetData = mtGetData;
		mWallPaperParam = wallPaperParam;

	}

	@Override
	protected List<WallPaper> doInBackground(Integer... page) {
		// TODO Auto-generated method stub
		List<WallPaper> list = null;
		do {
			try {
				list = (List<WallPaper>) mtGetData.invoke(null,mWallPaperParam);
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
