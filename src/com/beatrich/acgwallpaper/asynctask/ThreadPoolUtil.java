package com.beatrich.acgwallpaper.asynctask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolUtil {
	private static ExecutorService threadPool ;
	 
	public static ExecutorService getThreadPool() {
		if (threadPool==null)
		{
			threadPool=Executors.newCachedThreadPool(); 
		}
		return threadPool;
	}


	public static void setThreadPool(ExecutorService threadPool) {
		ThreadPoolUtil.threadPool = threadPool;
	}


 
 
	
	 
}
