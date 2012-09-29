package com.myexample.activity;

import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.inject.Inject;
import com.myexample.R;
import com.myexample.database.dao.TimeTrackerDao;
import com.myexample.time.list.TimeRecord;
import com.myexample.time.list.TimeTrackerAdapter;

@ContentView(R.layout.time_list)
public class ListViewActivity extends RoboActivity {

	protected static final int ADD_TIME_ENTRY_REQUEST_CODE = 1;

	private static final String TAG = "ListViewActivity";

	@InjectView(R.id.time_list_view)
	protected ListView listView;

	@Inject
	protected TimeTrackerAdapter timeTrackerAdapter;
	
	@Inject
	protected TimeTrackerDao timeTrackerDao;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "time tracker instance is " + timeTrackerAdapter);
		listView.setAdapter(timeTrackerAdapter);
		List<TimeRecord> allRecords = timeTrackerDao.getAllRecords();
		for (TimeRecord timeRecord : allRecords) {
			timeTrackerAdapter.addTimeRecord(timeRecord);
		}
		timeTrackerAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == R.id.add_entry_menu_add) {
			Intent intent = new Intent(this, AddTimeEntryActivity.class);
			startActivityForResult(intent, ADD_TIME_ENTRY_REQUEST_CODE);
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.add_entry_menu, menu);
		return true;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ADD_TIME_ENTRY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				String time = data
						.getStringExtra(AddTimeEntryActivity.TIME_EXTRAS);
				String notes = data
						.getStringExtra(AddTimeEntryActivity.NOTES_EXTRAS);
				TimeRecord timeRecord = new TimeRecord(time, notes);
				timeRecord = timeTrackerDao.addRecord(timeRecord);
				boolean addTimeRecordSuccess = timeTrackerAdapter.addTimeRecord(timeRecord);
				if(addTimeRecordSuccess){
					timeTrackerAdapter.notifyDataSetChanged();
				}
			}
		}
	}

}
