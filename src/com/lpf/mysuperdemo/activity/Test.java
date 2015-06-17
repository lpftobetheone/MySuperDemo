/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.lpf.mysuperdemo.R;


/**
 *@Title:
 *@Description:
 *@Author:liupf5
 *@Since:2015-6-8
 *@Version:1.1.0
 */
public class Test extends Activity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		
		getFenBianLv();
	}

	/**
	 * 
	 * @Description:
	 */
	private void getFenBianLv() {
		// TODO Auto-generated method stub
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		int widthPixels = metrics.widthPixels;
		int heightPixels = metrics.heightPixels;
		
		TextView mTextView = (TextView)this.findViewById(R.id.id_tv_fenbianlv);
		mTextView.setText(widthPixels+":"+heightPixels);
		
//		TextView mTextView = new TextView(Test.this);
//		mTextView.setText("widthPixels:"+widthPixels +">>> heightPixels:"+ heightPixels);
//		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//		mTextView.setLayoutParams(params);
		
		
	}
}
