package com.myexample.activity;

import android.app.Activity;
import android.os.Bundle;

import com.myexample.R;



public class HelloAndroidActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
}
