package com.lpf.mysuperdemo.downloadfile;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLite��������ʵ�ִ������ݿ�ͱ����汾�仯ʱʵ�ֶԱ�����ݿ��Ĳ���
 *@author Administrator
 */

public class DBOpenHelper extends SQLiteOpenHelper
{
	private static final String DBName="eric.db";
	private static final int VERSION=1;
	
	/**
	 * ͨ�����췽��
	 * @param contextӦ�ó���������Ķ���
	 */
	public DBOpenHelper(Context context)
	{
		super(context, DBName, null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// TODO �Զ����ɵķ������ 
		db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog (id integer primary key autoincrement,"
				+ "downpath varchar(100),threadid INTEGER,downlength INTEGER)");
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// TODO �Զ����ɵķ������
		db.execSQL("DROP TABLE IF EXISTS filedownlog");
		onCreate(db);
	}
	
	
}
