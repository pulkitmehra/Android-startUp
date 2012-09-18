package com.myexample.nasa.modules;

import roboguice.RoboGuice;
import android.app.Application;

import com.google.inject.util.Modules;

public class ListViewApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		RoboGuice.setBaseApplicationInjector(
				this,
				RoboGuice.DEFAULT_STAGE,
				Modules.override(RoboGuice.newDefaultRoboModule(this)).with(
						new ListViewModule()));
	}

}
