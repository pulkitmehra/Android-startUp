package com.myexample.activity;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.myexample.R;

@ContentView(R.layout.todo_list)
public class ToDoListActivity extends RoboActivity implements OnKeyListener {

	@InjectView(R.id.todo_myListView)
	protected ListView listView;

	@InjectView(R.id.todo_myEditText)
	protected EditText editText;

	protected List<String> contentList;
	protected ArrayAdapter<String> listViewAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contentList = new ArrayList<String>();

		listViewAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, contentList);
		listView.setAdapter(listViewAdapter);
		editText.setOnKeyListener(this);

	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN)
			if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
					|| (keyCode == KeyEvent.KEYCODE_ENTER)) {
				contentList.add(0, editText.getText().toString());
				listViewAdapter.notifyDataSetChanged();
				editText.setText("");
				return true;
			}
		return false;
	}

}
