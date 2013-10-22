package com.beatrich.acgwallpaper.asynctask;

public interface TaskListener<T, P, R> {
	 

	public void onCancelled();

	public void onPostExecute(R result);

	public void onPreExecute();

	public void onProgressUpdate(P... values);
}
