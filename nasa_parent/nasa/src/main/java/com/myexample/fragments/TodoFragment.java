package com.myexample.fragments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import roboguice.fragment.RoboFragment;
import roboguice.inject.ContextSingleton;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.myexample.R;
import com.myexample.adapter.TodoListAdapter;
import com.myexample.dto.Todo;
import com.myexample.handler.CommonHelper;
import com.myexample.handler.CommonHelper.DateFormatExp;

@ContextSingleton
public class TodoFragment extends RoboFragment implements OnKeyListener {

	private View layoutView;
	protected ListView listView;
	protected EditText editText;

	protected ArrayAdapter<Todo> listViewAdapter;
	protected List<Todo> todoList;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		todoList =new ArrayList<Todo>();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, contentList);
		listViewAdapter = new TodoListAdapter(getActivity(), R.layout.todo_list_item, todoList);
		listView.setAdapter(listViewAdapter);
		editText.setOnKeyListener(this);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		layoutView = inflater.inflate(R.layout.todo_list, container, false);
		listView = (ListView) layoutView.findViewById(R.id.todo_myListView);
		editText = (EditText) layoutView.findViewById(R.id.todo_myEditText);
		return layoutView;
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN)
			if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) || (keyCode == KeyEvent.KEYCODE_ENTER)) {
				String msg = editText.getText().toString();
				String dateToString = CommonHelper.dateToString(new Date(), DateFormatExp.DEFAULT_DATE_TO_STRING_FORMAT);
				String time=CommonHelper.dateChangeFormatFromDefault(dateToString,DateFormatExp.MMM_DD_COMMA_YYYY);
				Todo item=new Todo(time, msg);
				todoList.add(0, item);
				listViewAdapter.notifyDataSetChanged();
				editText.setText("");
				return true;
			}
		return false;
	}
}
