package com.lpf.mysuperdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;

import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.views.SearchFlowLayout;

public class SearchResultActivity extends Activity implements OnClickListener{

	private ImageButton mBtnSearch;
	private ImageButton mBtnSearchBack;

	private EditText mEtSearch;
	private SearchFlowLayout mSearchFlowLayout;// 热门搜索标签
	private String[] mValues = null; // 热门搜索标签内容
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search_result);
		
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		mBtnSearch = (ImageButton) findViewById(R.id.btn_search);
		mBtnSearchBack = (ImageButton) findViewById(R.id.btn_search_back);
		mEtSearch = (EditText) findViewById(R.id.et_search);
		//mSearchFlowLayout = (SearchFlowLayout) findViewById(R.id.search_hot_search);

		mBtnSearch.setOnClickListener(this);
		mBtnSearchBack.setOnClickListener(this);
		
	}
	

	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_search:
			break;
		case R.id.btn_search_back:
			this.finish();
			break;
		}
	}
}
