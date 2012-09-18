package com.myexample.test.nasa.activity;

import android.annotation.TargetApi;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.myexample.nasa.activity.ListViewActivity;

@TargetApi(16)
public class ListActivityRoboIntegrationTest extends
		ActivityInstrumentationTestCase2<ListViewActivity> {

	private Solo solo;

	public ListActivityRoboIntegrationTest() {
		super(ListViewActivity.class);

	}

	@Override
	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void listViewActivityTest() {
		solo.clickOnMenuItem("Add");
	}

}
