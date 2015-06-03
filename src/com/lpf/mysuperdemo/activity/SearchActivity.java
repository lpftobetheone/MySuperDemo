package com.lpf.mysuperdemo.activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.views.SearchFlowLayout;

public class SearchActivity extends Activity implements OnClickListener {

	private ImageButton mBtnSearch;
	private ImageButton mBtnSearchBack;

	private EditText mEtSearch;
	private SearchFlowLayout mSearchFlowLayout;// 热门搜索标签
	private String[] mValues = null; // 热门搜索标签内容
	private ListView mListView;

	private List<String> mListData = new ArrayList<String>();
	private ArrayAdapter<String> mListAdapter;
	
	private TextView mHistorySearch;
	private Button mListClear;

	Set<String> set = new HashSet<String>();

	private static final String TAG = "SearchActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search);

		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		mBtnSearch = (ImageButton) findViewById(R.id.btn_search);
		mBtnSearchBack = (ImageButton) findViewById(R.id.btn_search_back);
		mEtSearch = (EditText) findViewById(R.id.et_search);
		mSearchFlowLayout = (SearchFlowLayout) findViewById(R.id.search_hot_search);
		mListView = (ListView) findViewById(R.id.search_historylist);
		
		mHistorySearch = (TextView)findViewById(R.id.search_history_search);
		mListClear =(Button)findViewById(R.id.search_listClear);

		mBtnSearch.setOnClickListener(this);
		mBtnSearchBack.setOnClickListener(this);

		getFromSharedPreference();

		initHotSearch();

		initHistorySearch();

	}

	/**
	 * 历史搜索列表
	 */
	@SuppressLint({ "InlinedApi", "NewApi" })
	private void initHistorySearch() {
		// TODO Auto-generated method stub
		mListAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mListData);

		
		/*TextView mListHead = new TextView(this);
		mListHead.setTextAppearance(this, android.R.attr.textAppearanceMedium);
		mListHead.setText("历史记录");
		mListHead.setTextSize(20);
		mListView.addHeaderView(mListHead);*/
		
		/*Button mListClear = new Button(this);
		mListClear.setText("清空记录");
		mListClear.setBackgroundColor(color.red);
		mListClear.setTextSize(22);
		mListClear.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
		mListView.addFooterView(mListClear);*/
		mListClear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//清空SharedPreferences
				clearSharedPreference();

				// 对搜索列表进行清除
				mListData.clear();
				mListAdapter.notifyDataSetChanged();
				// 是否显示历史搜索列表
				showList();
			}
		});
		mListView.setAdapter(mListAdapter);
		
		mListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				addToSharedPreference((String)mListView.getItemAtPosition(position));
				goToSearchResultActivity();
			}
		});
		// 是否显示历史搜索列表
		showList();
	}

	/**
	 * 热门搜索标签
	 */
	private void initHotSearch() {
		// TODO Auto-generated method stub
		mValues = new String[] { "T系列", "ThinkPad鼠标", "乐檬手机", "Yoga Pro", "小新系列"};
		LayoutInflater mInflater = LayoutInflater.from(this);
		for (int i = 0; i < mValues.length; i++) {
			final TextView mHotText = (TextView) mInflater.inflate(
					R.layout.search_textview, mSearchFlowLayout, false);
			mHotText.setText(mValues[i]);
			mSearchFlowLayout.addView(mHotText);
			mHotText.setOnClickListener(new View.OnClickListener() {
				// 点击热门标签进行搜索
				@Override
				public void onClick(View v) {
					addToSharedPreference(mHotText.getText().toString());
					//mListData.add(0, mHotText.getText().toString());
					//mListAdapter.notifyDataSetChanged();
					showList();
					goToSearchResultActivity();
				}
			});
		}

	}

	/**
	 * 是否显示历史搜索列表
	 */
	private void showList() {
		// TODO Auto-generated method stub
		// 对当前ListView数据的个数进行判断
		if (mListData.size() > 0 && !mListData.isEmpty()) {
			mListView.setVisibility(View.VISIBLE);
			mHistorySearch.setVisibility(View.VISIBLE);
			mListClear.setVisibility(View.VISIBLE);
		} else {
			mListView.setVisibility(View.GONE);
			mHistorySearch.setVisibility(View.GONE);
			mListClear.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_search:
			if (TextUtils.isEmpty(mEtSearch.getText())) {
				Toast.makeText(SearchActivity.this, R.string.search_is_null,
						Toast.LENGTH_SHORT).show();
			} else {
				//搜索内容不为空，将其添加到SharedPreferences中，同时更新ListView列表
				addToSharedPreference(mEtSearch.getText().toString());
				showList();
				goToSearchResultActivity();
			}
			break;
		case R.id.btn_search_back:
			this.finish();
			break;
		}
	}

	/**
	 * 将搜索关键词保存到sharedPreference中
	 * 
	 * @param keyValue
	 *            搜索词
	 */
	private void addToSharedPreference(String keyValue) {

		SharedPreferences getSp = getSharedPreferences("searchKeys",
				Activity.MODE_PRIVATE);;
		SharedPreferences.Editor editor = getSp.edit();

		/*if(set.contains(keyValue)){
			set.remove(keyValue);
		}
		set.add(keyValue);
		editor.putStringSet("searchKey", set);*/
		
		//判断搜索记录中是否已经存在该搜索内容
		String searchStr = getSp.getString("history", "");
		String[] searchArrays = searchStr.split(",");
		int startPosition = 0;
		int endPosition = 0;
		for(int i =0; i<searchArrays.length; i++){
			if(searchArrays[i].equals(keyValue)){
				//得到重复字符串的终点位置
				endPosition = startPosition + keyValue.length()+1; 	
				searchStr = searchStr.substring(0,startPosition)+searchStr.substring(endPosition);
				//从搜索历史列表中移除重复搜索关键字
				mListData.remove(keyValue);
				break;
			}
			startPosition += searchArrays[i].length()+1;
		}
		
		/*if(searchStr.length() > 0){
			if(searchStr.contains(keyValue)){
				searchStr.replace(keyValue+",", "");
				mListData.remove(keyValue);
			}
		}*/
		StringBuilder sb = new StringBuilder(searchStr);
		sb.append(keyValue+",");
		mListData.add(0, keyValue);
		mListAdapter.notifyDataSetChanged();
		editor.putString("history", sb.toString());
		editor.commit();
	}

	/**
	 * 从SharedPreferences从获取搜索记录
	 */
	private void getFromSharedPreference() {

		SharedPreferences getSp = getSharedPreferences("searchKeys",
				Activity.MODE_PRIVATE);

		if (getSp != null) {
			/*Set<String> keyValues = getSp.getStringSet("searchKey", null);
			// 如果存在搜索记录
			if (keyValues != null) {
				Iterator<String> it = keyValues.iterator();
				while (it.hasNext()) {
					String keyValue = it.next();
					set.add(keyValue);
					mListData.add(0, keyValue);
				}
			}*/
			
			String searchStr = getSp.getString("history", "");
			String[] searchArrays = searchStr.split(",");
			mListData.clear();
			
			for(int i =0; i<searchArrays.length;i++)
			{
				//如果不为空，则加入到List中
				if(!searchArrays[i].equals("")){
					mListData.add(0,searchArrays[i]);
				}
			}
		}
	}

	/**
	 * 情况SharedPreference
	 */
	private void clearSharedPreference() {
		SharedPreferences getSp = getSharedPreferences("searchKeys",
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = getSp.edit();
		if (getSp != null) {
			editor.clear();
			editor.commit();
		}
	}
	
	private void goToSearchResultActivity(){
		Intent intent = new Intent(SearchActivity.this,SearchResultActivity.class);
		startActivity(intent);
	}
}
