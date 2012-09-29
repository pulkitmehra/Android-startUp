package com.myexample.activity;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import roboguice.RoboGuice;
import roboguice.test.RobolectricRoboTestRunner;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import com.myexample.R;
import com.myexample.activity.AddTimeEntryActivity;
import com.myexample.activity.ListViewActivity;
import com.myexample.database.dao.TimeTrackerDao;
import com.myexample.time.list.TimeRecord;
import com.myexample.time.list.TimeTrackerAdapter;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowActivity;
import com.xtremelabs.robolectric.shadows.ShadowIntent;
import com.xtremelabs.robolectric.tester.android.view.TestMenu;
import com.xtremelabs.robolectric.tester.android.view.TestMenuItem;

@TargetApi(16)
@RunWith(RobolectricRoboTestRunner.class)
public class ListViewActivityTest {

	private ListViewActivity listViewActivity;
	private TimeTrackerDao timeTrackerDaoMock = Mockito
			.mock(TimeTrackerDao.class);;

	@Before
	public void setUp() {
		RoboGuice
				.setBaseApplicationInjector(
						Robolectric.application,
						RoboGuice.DEFAULT_STAGE,
						Modules.override(
								RoboGuice
										.newDefaultRoboModule(Robolectric.application))
								.with(new ListViewActivityModule()));

		listViewActivity = new ListViewActivity();
		Mockito.reset(timeTrackerDaoMock);
		listViewActivity.timeTrackerDao = timeTrackerDaoMock;
		List<TimeRecord> records = createRecords();
		when(timeTrackerDaoMock.getAllRecords()).thenReturn(records);
		listViewActivity.onCreate(null);
	}

	private static List<TimeRecord> createRecords() {
		List<TimeRecord> recordList = new ArrayList<TimeRecord>();

		TimeRecord record_A = new TimeRecord();
		record_A.setTime("11:00");
		record_A.setNotes("Record A Test");

		TimeRecord record_B = new TimeRecord();
		record_B.setTime("12:00");
		record_B.setNotes("Record B Test");

		recordList.add(record_A);
		recordList.add(record_B);

		return recordList;
	}

	@After
	public void teardown() {
		RoboGuice.util.reset();
	}

	@Test
	public void should_inject_list_view_in_activity() {
		assertThat(listViewActivity.listView,
				is(listViewActivity.findViewById(R.id.time_list_view)));
	}

	@Test
	public void should_inject_adapter() {
		assertNotNull((listViewActivity.timeTrackerAdapter));
	}

	@Test
	public void should_create_menu_in_activity() {
		Menu menu = createMenuInListActivity();
		assertEquals(1, menu.size());
		MenuItem item = menu.getItem(0);
		assertThat(R.id.add_entry_menu_add, is(item.getItemId()));
	}

	@Test
	public void should_start_add_time_entry_activity_when_menu_add_is_selected() {

		Menu createMenuInListActivity = createMenuInListActivity();
		int addEntryMenuAdd = R.id.add_entry_menu_add;
		listViewActivity.onMenuItemSelected(addEntryMenuAdd,
				createMenuInListActivity.getItem(0));

		ShadowActivity shadowActivity = shadowOf(listViewActivity);
		Intent startedIntent = shadowActivity.getNextStartedActivity();
		ShadowIntent shadowIntent = shadowOf(startedIntent);

		assertThat(shadowIntent.getComponent().getClassName(),
				CoreMatchers.equalTo(AddTimeEntryActivity.class.getName()));
	}

	@Test
	public void should_not_start_activity_when_other_menu_is_selected() {
		MenuItem menuItem = new TestMenuItem();
		// bad id
		int addEntryMenuAdd = 00000;
		listViewActivity.onMenuItemSelected(addEntryMenuAdd, menuItem);
		ShadowActivity shadowActivity = shadowOf(listViewActivity);
		Intent startedIntent = shadowActivity.getNextStartedActivity();
		assertNull(startedIntent);
	}

	@Test
	public void should_add_in_time_entry_when_new_entry_is_added() {

		Menu createMenuInListActivity = createMenuInListActivity();
		int addEntryMenuAdd = R.id.add_entry_menu_add;
		listViewActivity.onMenuItemSelected(addEntryMenuAdd,
				createMenuInListActivity.getItem(0));

		int adapterCount = listViewActivity.timeTrackerAdapter.getCount();

		Intent intent = new Intent(listViewActivity, AddTimeEntryActivity.class);

		Intent dummyIntent = new Intent();
		dummyIntent.putExtra(AddTimeEntryActivity.NOTES_EXTRAS, "notes");
		dummyIntent.putExtra(AddTimeEntryActivity.TIME_EXTRAS, "time");

		when(
				listViewActivity.timeTrackerDao.addRecord((TimeRecord) Mockito
						.anyObject())).thenReturn(new TimeRecord());

		shadowOf(listViewActivity).receiveResult(intent, Activity.RESULT_OK,
				dummyIntent);
		
		assertThat((adapterCount + 1),
				is(listViewActivity.timeTrackerAdapter.getCount()));
	}

	private Menu createMenuInListActivity() {
		Menu menu = new TestMenu();
		listViewActivity.onCreateOptionsMenu(menu);
		return menu;
	}

	public class ListViewActivityModule extends AbstractModule {
		@Override
		protected void configure() {
			bind(TimeTrackerAdapter.class);
			bind(TimeTrackerDao.class).toInstance(timeTrackerDaoMock);
		}
	}
}
