package com.lpf.mysuperdemo.fragments;

import com.lpf.mysuperdemo.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment2 extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.fragment2, container,false);
		return mView;
	}
}
