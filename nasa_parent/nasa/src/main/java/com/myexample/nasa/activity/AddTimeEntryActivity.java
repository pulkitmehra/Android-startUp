package com.myexample.nasa.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.myexample.R;

@ContentView(R.layout.add_entry)
public class AddTimeEntryActivity extends RoboActivity {

	protected static final String NOTES_EXTRAS = "notes";

	protected static final String TIME_EXTRAS = "time";

	@InjectView(R.id.time_input)
	protected EditText timeInput;

	@InjectView(R.id.notes_input)
	protected EditText notesInput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void onCancel(View view) {
		finish();
	}

	public void onSave(View view) {
		Intent intent = getIntent();
		intent.putExtra(TIME_EXTRAS, timeInput.getText().toString());
		intent.putExtra(NOTES_EXTRAS, notesInput.getText().toString());
		
		this.setResult(RESULT_OK, intent);
		finish();
	}

}
