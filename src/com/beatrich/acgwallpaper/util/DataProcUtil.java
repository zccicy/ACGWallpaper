package com.beatrich.acgwallpaper.util;


public class DataProcUtil {

 
	public static int getPercent(long currentSize, long appSize) {
		try {
			if (appSize == 0)
				return 0;
			long percent = Math.round((double) (currentSize * 100.0)
					/ (double) appSize);

			if (percent >= 100) {
				percent = 100;

			}

			if (percent < 0)
				percent = 0;

			int intPercent = (Integer.parseInt(percent + ""));
			return intPercent;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}
}
