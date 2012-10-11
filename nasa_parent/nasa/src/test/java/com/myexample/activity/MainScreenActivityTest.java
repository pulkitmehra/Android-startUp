package com.myexample.activity;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import org.junit.runner.RunWith;

import roboguice.RoboGuice;
import roboguice.test.RobolectricRoboTestRunner;
import android.content.Intent;
import android.view.View;
import android.widget.Adapter;
import android.widget.GridView;

import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import com.myexample.R;
import com.myexample.adapter.ImageViewAdapter;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowActivity;
import com.xtremelabs.robolectric.shadows.ShadowGridView;
import com.xtremelabs.robolectric.shadows.ShadowIntent;
import com.xtremelabs.robolectric.shadows.ShadowToast;

@RunWith(RobolectricRoboTestRunner.class)
public class MainScreenActivityTest {

	private MainScreenActivity mainScreenActivity;

	@Before
	public void setUp() {
		RoboGuice
				.setBaseApplicationInjector(
						Robolectric.application,
						RoboGuice.DEFAULT_STAGE,
						Modules.override(
								RoboGuice
										.newDefaultRoboModule(Robolectric.application))
								.with(new MainScreenActivityTestModule()));
		mainScreenActivity = new MainScreenActivity();
		mainScreenActivity.onCreate(null);
	}

	@Test
	public void should_inject_grid_view() {
		assertThat(mainScreenActivity.gridView,
				is(mainScreenActivity.findViewById(R.id.gridViewScreen)));
	}

	@Test
	public void should_see_toast_on_click() {
		ShadowGridView shadowGridView = shadowOf(mainScreenActivity.gridView);
		View view = shadowGridView
				.findItemContainingText(MainScreenActivity.MY_NOTES_APP);
		assertNotNull(view);
		shadowGridView.performItemClick(1);
		assertThat(ShadowToast.getTextOfLatestToast(),
				JUnitMatchers.containsString(ListViewActivity.class
						.getSimpleName()));

	}

	@Test
	public void should_start_activity_when_example_is_selected() {
		ShadowGridView shadowGridView = shadowOf(mainScreenActivity.gridView);
		View view = shadowGridView
				.findItemContainingText(MainScreenActivity.MY_NOTES_APP);
		assertNotNull(view);
		shadowGridView.performItemClick(1);

		ShadowActivity shadowActivity = shadowOf(mainScreenActivity);
		Intent startedIntent = shadowActivity.getNextStartedActivity();
		ShadowIntent shadowIntent = shadowOf(startedIntent);
		assertThat(shadowIntent.getComponent().getClassName(),
				equalTo(ListViewActivity.class.getName()));
	}

	@Test
	public void should_have_adapter_and_examples() {
		View contentView = shadowOf(mainScreenActivity).getContentView();
		assertThat(contentView, instanceOf(GridView.class));

		Adapter adapter = shadowOf(mainScreenActivity.gridView).getAdapter();
		assertThat(adapter, instanceOf(ImageViewAdapter.class));

		assertThat(adapter.getCount(),
				is(mainScreenActivity.exampleItems.size()));
	}

	@After
	public void tearDown() {
		RoboGuice.util.reset();
	}

	public static class MainScreenActivityTestModule extends AbstractModule {

		@Override
		protected void configure() {
			bind(ImageViewAdapter.class);
		}

	}
}
