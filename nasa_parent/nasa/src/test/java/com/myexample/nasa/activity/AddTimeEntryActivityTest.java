package com.myexample.nasa.activity;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import roboguice.RoboGuice;
import roboguice.test.RobolectricRoboTestRunner;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import com.myexample.R;
import com.xtremelabs.robolectric.Robolectric;

@RunWith(RobolectricRoboTestRunner.class)
public class AddTimeEntryActivityTest {

	protected AddTimeEntryActivity addTimeEntryActivity;

	@Before
	public void setUp() {
		RoboGuice
				.setBaseApplicationInjector(
						Robolectric.application,
						RoboGuice.DEFAULT_STAGE,
						Modules.override(
								RoboGuice
										.newDefaultRoboModule(Robolectric.application))
								.with(new AddTimeEntryActivityTestModule()));
		addTimeEntryActivity = new AddTimeEntryActivity();
		addTimeEntryActivity.onCreate(null);
	}

	@Test
	public void should_inject_time_and_notes_input(){
		EditText timeInput = (EditText) addTimeEntryActivity.findViewById(R.id.time_input);
		assertThat(addTimeEntryActivity.timeInput.getId(),is(timeInput.getId()));
		
		EditText notesInput = (EditText) addTimeEntryActivity.findViewById(R.id.notes_input);
		assertThat(addTimeEntryActivity.notesInput.getId(),is(notesInput.getId()));
		
	}
	
	@Test
	public void should_kill_activity_on_cancel() {
		Button cancelButon= (Button) addTimeEntryActivity.findViewById(R.id.add_cancel);
		addTimeEntryActivity.onCancel(cancelButon);
		
		assertTrue(addTimeEntryActivity.isFinishing());
	}
	
	@Test
	public void should_have_extras_and_activity_be_finish_when_on_save_is_clicked(){
		
		Intent dummyIntent=new Intent();
		addTimeEntryActivity.setIntent(dummyIntent);
		addTimeEntryActivity.onSave(null);
		
		Intent intent = shadowOf(addTimeEntryActivity).getIntent();
		
		
		assertTrue(intent.hasExtra(AddTimeEntryActivity.TIME_EXTRAS));
		assertTrue(intent.hasExtra(AddTimeEntryActivity.NOTES_EXTRAS));
		assertTrue(addTimeEntryActivity.isFinishing());
	}
	

	@After
	public void tearDown() {
		RoboGuice.util.reset();
	}

	public static class AddTimeEntryActivityTestModule extends AbstractModule {

		@Override
		protected void configure() {
		}

	}

}
