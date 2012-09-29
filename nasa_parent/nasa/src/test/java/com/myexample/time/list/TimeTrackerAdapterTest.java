package com.myexample.time.list;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import roboguice.RoboGuice;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import roboguice.test.RobolectricRoboTestRunner;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import com.myexample.R;
import com.xtremelabs.robolectric.Robolectric;

@RunWith(RobolectricRoboTestRunner.class)
public class TimeTrackerAdapterTest {

	protected TimeTrackerAdapter timeTrackerAdapter;
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
								.with(new TimeTrackerAdapterModule()));

		mockActivity = new MockActivity();
		mockActivity.onCreate(null);
		timeTrackerAdapter = RoboGuice.getInjector(mockActivity).getInstance(
				TimeTrackerAdapter.class);

	}

	@After
	public void tearDown() {
		RoboGuice.util.reset();
	}

	@Test
	public void should_inject_list_view_in_mock() {
		assertNotNull(mockActivity.listView);

	}

	@Test
	public void should_inject_adapter() {
		assertNotNull(timeTrackerAdapter);
	}

	@Test
	public void should_inflate_row_view_and_get_first_row_value_when_1_and_parentView_is_given() {
		timeTrackerAdapter.addTimeRecord(new TimeRecord("11:00", "xyz"));
		timeTrackerAdapter.addTimeRecord(new TimeRecord("12:00",
				"Tired. Needed more caffeine"));
		View rowView = timeTrackerAdapter.getView(1, null,
				mockActivity.listView);
		assertNotNull(rowView);
		TextView noteText = (TextView) rowView.findViewById(R.id.notes_text);
		assertNotNull(noteText);
		assertThat("Tired. Needed more caffeine", is(noteText.getText()));

	}

	public class TimeTrackerAdapterModule extends AbstractModule {
		@Override
		protected void configure() {
		}
	}

	@ContentView(R.layout.time_list)
	public static class MockActivity extends RoboActivity {

		@InjectView(R.id.time_list_view)
		protected ListView listView;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

		}
	}
}
