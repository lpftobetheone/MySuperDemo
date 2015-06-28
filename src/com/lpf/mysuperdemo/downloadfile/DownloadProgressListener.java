package com.lpf.mysuperdemo.downloadfile;

public interface DownloadProgressListener
{
	/**
	 * 下载进度监听方法，获取和处理下载点数据的大小
	 * @param size
	 */
	public void onDownloadSize(int size);

}
