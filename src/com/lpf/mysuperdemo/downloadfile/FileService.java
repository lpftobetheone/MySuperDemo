package com.lpf.mysuperdemo.downloadfile;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FileService
{
	private DBOpenHelper openHelper;
	
	public FileService(Context context)
	{
		openHelper=new DBOpenHelper(context);
		
	}
	
	/**
	 * ��ȡ�ض�URI��ÿ���߳��Ѿ����ص��ļ�����
	 * threadid�������̵߳�id
	 * downlength:�����߳����ص����λ��
	 * downpath:����ǰ�߳����ص���Դ
	 * @param path
	 * @return
	 */
	public Map<Integer, Integer> getData(String path)
	{
		//��ȡ�ɶ�ȡ�����ݿ�����һ��������ڸò������ڲ�ʵ���У��䷵�ص���ʵ�ǿ�д�����ݿ���
		SQLiteDatabase db=openHelper.getReadableDatabase();
		//��������·����ѯ�����߳��������ݣ����ص�Cursorָ���һ����¼֮ǰ
		Cursor cursor=db.rawQuery("select threadid,downlength from "
				+ "filedownlog where downpath=?",new String[]{path} );
		//����һ����ϣ�����ڴ��ÿ���̵߳��Ѿ����ص��ļ�����		
		Map<Integer, Integer> data=new HashMap<Integer, Integer>();
		//�ӵ�һ����¼��ʼ��ʼ����Cursor����
		while(cursor.moveToNext())
		{
			//���߳�ID�͸��߳������صĳ������ý�data��ϣ����
			data.put(cursor.getInt(0), cursor.getInt(1));
			data.put(cursor.getInt(cursor.getColumnIndexOrThrow("threadid")), 
					cursor.getInt(cursor.getColumnIndexOrThrow("downlength")));			
		}
		cursor.close();
		db.close();
		return data;		
	}
	/**
	 * ����ÿ���߳��Ѿ����ص��ļ�����
	 * @param path ���ص�·��
	 * @param map ���ڵ�ID���Ѿ����صĳ��ȵļ���
	 */
	public void save(String path,Map<Integer, Integer> map){
		//��ȡ��д�����ݿ���
		SQLiteDatabase db=openHelper.getWritableDatabase();
		//��ʼʳ���Ϊ�˴�Ҫ�����������
		db.beginTransaction();
		try{
			for(Map.Entry<Integer, Integer> entry:map.entrySet())
			{
				//����for-each�ķ�ʽ�������ݼ���
				//�����ض�����·�����ض��߳�ID,�Ѿ����ص�����
				db.execSQL("insert into filedownlog(downpath,threadid,downlength) values(?,?,?)",
						new Object[]{path,entry.getKey(),entry.getValue()});
			}
			db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
		db.close();
		
		
	}
	
	/**
	 * ʵʱ����ÿ���߳��Ѿ����ص��ļ�����
	 * @param path
	 * @param threadId
	 * @param pos
	 */
	public void update(String path,int threadId,int pos){
		SQLiteDatabase db=openHelper.getWritableDatabase();
		//�����ض�����·�����ض��̣߳��Ѿ����ص��ļ�����
		db.execSQL("update filedownlog set downlength=? where downpath=? "
				+ "and threadid=?",new Object[]{pos,path,threadId});
		db.close();
	}
	
	/**
	 * ���ļ�������ɺ�ɾ����Ӧ�����ؼ�¼
	 * @param path
	 */
	public void delete(String path)
	{
		SQLiteDatabase db=openHelper.getWritableDatabase();
		db.execSQL("delete from filedownlog where downpath=?",
				new Object[]{path});
		db.close();
	}
}
