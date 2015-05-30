package com.lpf.mysuperdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lpf.mysuperdemo.adapter.SwingRightInAnimationAdapter;

public class MainActivity extends Activity {

	private ListView mListView;
	private ArrayAdapter mAdapter;
	private String[] mListItems;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initViews();
		
		initDatas();
	}


	private void initViews() {
		// TODO Auto-generated method stub
		mListView = (ListView)findViewById(R.id.id_list_demos);
	}
	
	private void initDatas() {
		// TODO Auto-generated method stub
		mListItems = getResources().getStringArray(R.array.demonames);
		
		mAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,mListItems);
		
		SwingRightInAnimationAdapter swingRightInAnimationAdapter = new SwingRightInAnimationAdapter(mAdapter);
		swingRightInAnimationAdapter.setListView(mListView);

		mListView.setAdapter(swingRightInAnimationAdapter);
	}

	
}
