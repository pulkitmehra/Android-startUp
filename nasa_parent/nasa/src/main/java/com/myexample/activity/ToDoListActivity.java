package com.myexample.activity;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;
import android.os.Bundle;

import com.google.inject.Inject;
import com.myexample.R;
import com.myexample.fragments.TodoFragment;
import com.myexample.handler.FragmentHandler;

@ContentView(R.layout.todo_layout)
public class ToDoListActivity extends RoboFragmentActivity {

	@Inject
	protected FragmentHandler fragmentHandler;

	@Inject
	protected TodoFragment todoFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fragmentHandler.addFragment(todoFragment, R.id.todo_content, this);
	}

}
