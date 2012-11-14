package com.myexample.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myexample.R;
import com.myexample.dto.Todo;

public class TodoListAdapter extends ArrayAdapter<Todo> {

	private Context context;
	private int layoutResourceId;

	public TodoListAdapter(Context context, int layoutResourceId, List<Todo> todoList) {
		super(context, layoutResourceId, todoList);
		this.layoutResourceId = layoutResourceId;
		this.context = context;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout todoView;
		todoView = getInflatedView(convertView);

		Todo item = getItem(position);
		TextView dateView = (TextView) todoView.findViewById(R.id.todo_rowDate);
		TextView taskView = (TextView) todoView.findViewById(R.id.todo_rowName);

		String time = item.time;
		String msg = item.message;

		dateView.setText(time);
		taskView.setText(msg);

		return todoView;

	}

	private LinearLayout getInflatedView(View convertView) {
		LinearLayout todoView;
		if (convertView == null) {
			todoView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater li;
			li = (LayoutInflater) getContext().getSystemService(inflater);
			li.inflate(layoutResourceId, todoView, true);
		} else {
			todoView = (LinearLayout) convertView;
		}
		return todoView;
	}

}
