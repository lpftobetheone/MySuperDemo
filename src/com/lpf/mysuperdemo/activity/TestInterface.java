/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.interfaces.BaseActivityInterface;


/**
 *@Title:
 *@Description:
 *@Author:liupf5
 *@Since:2015-6-4
 *@Version:1.1.0
 */
public class TestInterface extends Activity implements BaseActivityInterface , OnItemClickListener{
	
	private ListView mListView;
	private ArrayAdapter mAdapter;
	private String[] mListItem = new String[]{};

	/* (non-Javadoc)
	 * @see com.lpf.mysuperdemo.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_listdemos);
		initViews();
		initDatas();
	}

	

	/* (non-Javadoc)
	 * @see com.lpf.mysuperdemo.interfaces.BaseActivityInterface#initViews()
	 */
	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		mListView = (ListView)this.findViewById(R.id.id_common_list_demo);
	}

	/* (non-Javadoc)
	 * @see com.lpf.mysuperdemo.interfaces.BaseActivityInterface#initDatas()
	 */
	@Override
	public void initDatas() {
		// TODO Auto-generated method stub
		mListItem = getResources().getStringArray(R.array.custom_demos);
		mAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,mListItem);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
	}

	/* (non-Javadoc)
	 * @see com.lpf.mysuperdemo.activity.BaseActivity#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent();
		switch (position) {
		//数据传递
		case 0:
			intent.setClass(TestInterface.this, RequestForDataActivity.class);
			startActivity(intent);
			break;
		}
	}
}
