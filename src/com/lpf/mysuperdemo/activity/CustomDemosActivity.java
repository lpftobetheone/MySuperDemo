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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lpf.mysuperdemo.R;

/**
 * @Title:
 * @Description:
 * @Author:liupf5
 * @Since:2015-6-12
 * @Version:1.1.0
 */
public class CustomDemosActivity extends Activity implements
		OnItemClickListener {

	private ListView mListView;
	private ArrayAdapter mAdapter;
	private String[] mListItem = new String[] {};

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_listdemos);

		initViews();

		initDatas();
	}

	/**
	 * 
	 * @Description:
	 */
	private void initViews() {
		// TODO Auto-generated method stub
		mListView = (ListView) this.findViewById(R.id.id_common_list_demo);

	}

	/**
	 * 
	 * @Description:
	 */
	private void initDatas() {
		// TODO Auto-generated method stub
		mListItem = getResources().getStringArray(R.array.custom_demos);
		mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
				mListItem);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (position) {
		// 自定义带删除按钮的输入框
		case 0:
			intent.setClass(CustomDemosActivity.this,
					CustomDeleteEditTextActivity.class);
			startActivity(intent);
			break;
		// 自定义带删除按钮的输入框
		case 1:
			intent.setClass(CustomDemosActivity.this,
					CustomDialogActivity.class);
			startActivity(intent);
			break;
		}

	}
}