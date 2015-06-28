package com.lpf.mysuperdemo.downloadfile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import android.print.PrintAttributes;
import android.util.Log;

/**
 * �����̣߳����ݾ������ص�ַ�����ݱ��浽���ļ������ؿ�Ĵ�С���Ѿ����ص����ݴ�С����Ϣ��������
 * 
 * @author Administrator
 * 
 */
public class DownloadThread extends Thread
{
	//����TAG,�������ڵĴ�ӡ���
	private static final String TAG = "DownloadThread";
	//���ص����ݱ��浽���ļ�
	private File saveFile;
	//���ص�URL
	private URL downUrl;
	//ÿ���߳����صĴ�С
	private int block;
	//��ʼ���߳�ID����
	private int threadId=-1;
	//���߳��Ѿ����ص����ݳ���
	private int downloadedLength;
	//���߳��Ƿ�������صı�־
	private boolean finished=false;
	//�ļ�������
	private FileDownloader downloader;
	
	public DownloadThread(FileDownloader downloader,URL downUrl,
			File saveFile,int block,int downloadedLength,int threadId)
	{
		this.downUrl=downUrl;
		this.saveFile=saveFile;
		this.block=block;
		this.downloader=downloader;
		this.threadId=threadId;
		this.downloadedLength=downloadedLength;
	}

	@Override
	public void run()
	{
		// TODO �Զ����ɵķ������
		super.run();
		//δ�������
		if(downloadedLength<block)
		{			
			try
			{
				//����HttpsURLConnection����
				HttpURLConnection http=(HttpURLConnection) downUrl.openConnection();
				//�������ӳ�ʱ�¼�Ϊ5��
				http.setConnectTimeout(5*1000);
				//��������ķ���ΪGET
				http.setRequestMethod("GET");
				//���ÿͻ��˿��Խ��ܵ�ý������
				http.setRequestProperty("Accept", "image/gif,image/jpeg,image/pjpeg,file/zip"
						+ "application/x-shockwave-flash,application/xaml+xml,application/vnd.ms-xpsdocument,"
						+ "application/x-ms-xbap,application/x-ms-application,"
						+ "application/vnd.ms-excel,application/vnd.ms-powerpoint,"
						+ "application/vnd.msword,*/*");
				//���ÿͻ�������
				http.setRequestProperty("Accept-Language", "zh-CN");
				//�����������Դҳ�棬���ڷ���˽�����Դͳ��
				http.setRequestProperty("Referer", downUrl.toString());
				//���ÿͻ��˱���
				http.setRequestProperty("Charset", "UTF-8");
				//��ʼλ��
				int startPos=block*(threadId-1)+downloadedLength;
				//����λ��
				int endPos=block*threadId-1;
				//���û�ȡʵ�����ݵķ�Χ�����������ʵ�����ݵĴ�С���Զ�����ʵ�ʵ����ݴ�С
				http.setRequestProperty("Range", "bytes="+startPos+"-"+endPos);
				//�����û�����
				http.setRequestProperty("User-Agent", "Mozilla/4.0(compatible;"
						+ "MSIE 8.0;Windows NT 5.2;Trident/4.0;.NET CLR 1.1.4322;"
						+ ".NET CLR 2.0.50727;.NET CLR 3.0.04506.30;.NET CLR 3.0.4506.2152;"
						+ ".NET CLR 3.5.30729)");
				//����Connection�ķ�ʽ,ʹ�ó�����
				http.setRequestProperty("Connection", "Keep-Alive");
				//��ȡԶ�����ӵ�������
				InputStream inStream=http.getInputStream();
				//���ñ������ݻ���Ĵ�СΪ1MB
				byte[] buffer=new byte[1024];
				//����ÿ�ζ�ȡ��������
				int offset=0;
				//��ӡ���߳̿�ʼ���ص�λ��
				print("Thread"+this.threadId+"starts to download from position "+
				startPos);
				//����ļ������ڣ��򴴽��ļ�������ÿ�θ���ͬ�����ײ�洢������
				RandomAccessFile threadFile=new RandomAccessFile(this.saveFile, "rwd");
				//�ļ�ָ��ָ��ʼ���ص�λ��
				threadFile.seek(startPos);
				//���û�û��Ҫ��ֹͣ���أ�ͬʱû�е����������ݵ�ĩβʱ���һֱѭ����ȡ����
				//������������� len �������ֽڶ����ֽ����顣���Զ�ȡ��� len �ֽڣ������ܶ�ȡ������������������ʽ����ʵ�ʶ�ȡ���ֽ�����
				while(!downloader.getExited()&&(offset=inStream.read(buffer, 0, 1024))!=-1){
					//ֱ�Ӱ�����д���ļ���
					// ��bufferд�뵽���ļ���������У���buffer��byteOffset��ʼд��д�볤����byteCount��
					threadFile.write(buffer, 0, offset);
					//�������ص��Ѿ�д���ļ��е����ݼ��뵽���س�����
					downloadedLength+=offset;
					//�Ѹ��߳��Ѿ����ص����ݳ��ȸ��µ����ݿ���ڴ��ϣ����
					downloader.update(this.threadId,downloadedLength);
					//�������ص����ݳ��ȼ��뵽�Ѿ����ص������ܳ�����
					downloader.append(offset);
				}//���߳�����������ϻ������ر��û�ֹͣ
				//�رո���������ļ��������ͷź͸��ļ�����������Դ
				threadFile.close();
				//�����ʵ�������ڹر�ʱ�ͷ�������Դ
				inStream.close();
				if(downloader.getExited())
				{
					print("Thread"+this.threadId+" has been paused");
				}
				else {
					print("Thread"+this.threadId+" download finished");
				}
				//������ɱ�־Ϊtrue��������������ɻ����û������ж�����
				this.finished=true;
				
			} catch (Exception e)
			{
				// TODO �Զ����ɵ� catch ��
				//���ø��߳��Ѿ����صĳ���Ϊ-1
				this.downloadedLength=-1;
				//��ӡ���쳣��Ϣ
				print("Thread"+this.threadId+":"+e);
			}
		}
		
	}
	/**
	 * ��ӡ��Ϣ
	 * @param msg
	 */
	private static void print(String msg)
	{
		Log.i(TAG, msg);
	}
	/**
	 * �����Ƿ����
	 * @return
	 */
	public boolean isFinished()
	{
		return finished;
	}
	/**
	 * �Ѿ����ص����ݴ�С
	 * @return �������ֵΪ-1����������ʧ��
	 */
	public long getDownloadedLength()
	{
		return downloadedLength;
	}
	
}
