package com.beatrich.acgwallpaper.asynctask;

import android.os.AsyncTask;

public class BaseAsyncTask<T,P,R> extends AsyncTask<T,P,R> {
	private TaskListener<T, P, R> mTaskListener;
	
	
	public BaseAsyncTask(TaskListener<T, P, R> mTaskListener) {
		super();
		this.mTaskListener = mTaskListener;
	}

	@Override
	protected R doInBackground(T... params) {
		// TODO Auto-generated method stub
		 return null;
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		mTaskListener.onCancelled();
//		super.onCancelled();
	}

	@Override
	protected void onPostExecute(R result) {
		// TODO Auto-generated method stub
		mTaskListener.onPostExecute(result);
//		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		mTaskListener.onPreExecute();
//		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(P... values) {
		// TODO Auto-generated method stub
		mTaskListener.onProgressUpdate(values);
//		super.onProgressUpdate(values);
	}

	 

}
