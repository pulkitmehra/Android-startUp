package com.myexample.database.dao;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import roboguice.RoboGuice;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.test.RobolectricRoboTestRunner;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.util.Modules;
import com.myexample.R;
import com.myexample.database.TimeTrackerDatabase;
import com.myexample.time.list.TimeRecord;
import com.xtremelabs.robolectric.Robolectric;

@RunWith(RobolectricRoboTestRunner.class)
public class TimeRecordDaoImplTest {

	protected MockActivity mockActivity;

	@Before
	public void setUp() {

		RoboGuice
				.setBaseApplicationInjector(
						Robolectric.application,
						RoboGuice.DEFAULT_STAGE,
						Modules.override(
								RoboGuice
										.newDefaultRoboModule(Robolectric.application))
								.with(new TimeRecordDaoModule()));

		mockActivity = new MockActivity();
		mockActivity.onCreate(null);

	}

	@After
	public void tearDown() {
		RoboGuice.util.reset();
	}

	@Test
	public void should_inject_dao_and_database() {
		assertNotNull(mockActivity.timeTrackerDao);
		TimeTrackerDaoImpl timeTrackerDaoImpl = (TimeTrackerDaoImpl) mockActivity.timeTrackerDao;
		assertNotNull(timeTrackerDaoImpl.database);
	}

	@Test
	public void should_insert_in_database() {
		TimeRecord timeRecord = new TimeRecord();
		timeRecord.setTime("11:00");
		timeRecord.setNotes("DummyTime");
		TimeRecord addRecord = mockActivity.timeTrackerDao
				.addRecord(timeRecord);

		assertNotNull(addRecord.getId());
		assertThat(addRecord.getTime(), is(timeRecord.getTime()));
		assertThat(addRecord.getNotes(), is(timeRecord.getNotes()));
	}

	@Test
	public void should_query_database() {
		Long _id = addToDatabase();

		TimeRecord record = mockActivity.timeTrackerDao.getRecord(_id);
		assertThat("Time", is(record.getTime()));
		assertThat("Notes", is(record.getNotes()));

	}

	@Test
	@Ignore
	public void should_query_all_from_database() {
		Long _id = addToDatabase();
		assertNotNull(_id);

		List<TimeRecord> allRecords = mockActivity.timeTrackerDao
				.getAllRecords();
		assertNotNull(allRecords);
		assertThat(1, is(allRecords.size()));
	}

	private Long addToDatabase() {
		TimeTrackerDaoImpl timeTrackerDaoImpl = (TimeTrackerDaoImpl) mockActivity.timeTrackerDao;
		TimeTrackerDatabase database = timeTrackerDaoImpl.database;
		assertNotNull(database);

		SQLiteDatabase writableDatabase = database.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TimeTrackerDatabase.COLUMN_TIME, "Time");
		values.put(TimeTrackerDatabase.COLUMN_NOTES, "Notes");

		Long _id = writableDatabase.insert(TimeTrackerDatabase.TIME_TABLE,
				null, values);

		assertNotNull(_id);
		return _id;
	}

	public class TimeRecordDaoModule extends AbstractModule {

		@Override
		protected void configure() {
			bind(TimeTrackerDatabase.class);
			bind(TimeTrackerDao.class).to(TimeTrackerDaoImpl.class);
		}
	}

	@ContentView(R.layout.time_list)
	public static class MockActivity extends RoboActivity {

		@Inject
		TimeTrackerDao timeTrackerDao;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

		}
	}

}
