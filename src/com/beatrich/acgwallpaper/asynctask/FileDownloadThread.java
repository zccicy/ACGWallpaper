package com.beatrich.acgwallpaper.asynctask;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;

import android.database.DatabaseUtils;

import com.beatrich.acgwallpaper.common.BRConstants;
import com.beatrich.acgwallpaper.util.DataProcUtil;

public class FileDownloadThread extends Thread {
	private static final int BUFFER_SIZE = 5120;
	private URL url;
	private File file;
	private int startPosition;
	private int endPosition;
	private int curPosition;

	public boolean isstop = false;
	private FetchListener mFetchListener;
	// 用于标识当前线程是否下载完成
	private boolean finished = false;
	private int downloadSize = 0;
	private int lastDownloadSize = 0;
	public boolean iswait = false;
	private int retryCount = 0;

	public FileDownloadThread(URL url, File file, int startPosition,
			int endPosition) {
		this.url = url;
		this.file = file;
		this.startPosition = startPosition;
		this.curPosition = startPosition;
		this.endPosition = endPosition;
		retryCount = 0;
	}

	@Override
	public void run() {
		System.out
		.println("-------------------Thread-----"+Thread.currentThread().getId()+"started");
		BufferedInputStream bis = null;
		RandomAccessFile fos = null;
		byte[] buf = new byte[BUFFER_SIZE];
		URLConnection con = null;
		try {
			String command = "chmod 755 " + file.getAbsolutePath();
			Runtime runtime = Runtime.getRuntime();
			runtime.exec(command);
			url = new URL(url.toString());
		} catch (Exception e) {
			// TODO: handle exception
			return;
		}

		do {
			try {
				// System.setProperty("http.keepAlive", "false");
				con = url.openConnection();
				con.setConnectTimeout(5000);
				con.setAllowUserInteraction(true);
				// 设置当前线程下载的起点，终点
				con.setRequestProperty("Range", "bytes=" + startPosition + "-"
						+ endPosition);
				// 使用java中的RandomAccessFile 对文件进行随机读写操作
				fos = new RandomAccessFile(file, "rw");
				// 设置开始写文件的位置
				fos.seek(startPosition);
				bis = new BufferedInputStream(con.getInputStream());

				// 开始循环以流的形式读写文件
				while (curPosition < endPosition) {
					int len = bis.read(buf, 0, BUFFER_SIZE);
					if (len < 0) {
						throw new Exception();
					}
					fos.write(buf, 0, len);
					curPosition = curPosition + len;
					if (curPosition >= endPosition) {
						downloadSize = endPosition - startPosition;
					} else {
						downloadSize += len;
					}

					if (downloadSize - lastDownloadSize >= 51200
							|| curPosition >= endPosition) {
						mFetchListener.listenProgress(downloadSize
								- lastDownloadSize);
						System.out.println(Thread.currentThread().getId()
								+ "size"
								+ downloadSize
								+ "-"
								+ (endPosition - startPosition)
								+ "percent"
								+ DataProcUtil.getPercent(downloadSize,
										(endPosition - startPosition)));
						lastDownloadSize = downloadSize;
					}
					if (curPosition >= endPosition) {
						System.out
								.println("-------------------filethreadloadcomplete-----");

						bis.close();
						fos.close();
						finished = true;
						break;
					}
					if (iswait) {
						try {
							synchronized (this) {
								iswait = false;
								wait();
							}

						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (isstop) {
						try {
							synchronized (this) {

								wait();
								interrupt();
							}

						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

				// 下载完成设为true

			} catch (Exception e) {
				
				
				e.printStackTrace();

			}

			try {
				bis.close();
				fos.close();
				sleep(1000);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			retryCount++;
			if (retryCount > BRConstants.MAX_RETRY_COUNT) {
				System.out.println("replycount"+retryCount);
				// break;
			}
			if (finished) {
//				synchronized (this) {
//					notifyAll();
//				}
				System.out.println("----load---finished");
				break;
			}
		} while (true);

	}

	public boolean isFinished() {
		return finished;
	}

	public int getDownloadSize() {
		return downloadSize;
	}

	public void setDownloadSize(int downloadSize) {
		this.downloadSize = downloadSize;
	}

	public FetchListener getmFetchListener() {
		return mFetchListener;
	}

	public void setmFetchListener(FetchListener mFetchListener) {
		this.mFetchListener = mFetchListener;
	}

	public interface FetchListener {
		void downloadFailedListener();

		void listenProgress(int currentSize);
	}

}