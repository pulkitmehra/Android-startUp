package com.myexample.adapter;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.ContextSingleton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.inject.Inject;
import com.myexample.R;
import com.myexample.dto.TimeRecord;


@ContextSingleton
public class TimeTrackerAdapter extends BaseAdapter {

	@Inject
	protected LayoutInflater layoutInflater;

	private List<TimeRecord> timeRecordList = new ArrayList<TimeRecord>();

	public TimeTrackerAdapter() {}

	public int getCount() {
		return timeRecordList.size();
	}

	public Object getItem(int position) {
		return timeRecordList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View rowView, ViewGroup parent) {
		if (rowView == null) {
			rowView = layoutInflater.inflate(R.layout.time_item, parent, false);
		}

		TimeRecord timeRecord = timeRecordList.get(position);
		TextView timeTextView = (TextView) rowView.findViewById(R.id.time_text);
		timeTextView.setText(timeRecord.getTime());

		TextView notesTextView = (TextView) rowView
				.findViewById(R.id.notes_text);
		notesTextView.setText(timeRecord.getNotes());

		return rowView;
	}
	
	public boolean addTimeRecord(TimeRecord timeRecord){
		if(timeRecordList==null){
			timeRecordList=new ArrayList<TimeRecord>();
		}
		if(timeRecord!=null){
			timeRecordList.add(timeRecord);
			return true;
		}
		return false;
	}
	

}
