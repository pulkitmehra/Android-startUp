package com.myexample.modules;

import com.google.inject.AbstractModule;
import com.myexample.adapter.ImageViewAdapter;
import com.myexample.adapter.TimeTrackerAdapter;
import com.myexample.database.TimeTrackerDatabase;
import com.myexample.database.dao.TimeTrackerDao;
import com.myexample.database.dao.TimeTrackerDaoImpl;

public class MainViewModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(TimeTrackerAdapter.class);
		bind(TimeTrackerDatabase.class);
		bind(TimeTrackerDao.class).to(TimeTrackerDaoImpl.class);
		bind(ImageViewAdapter.class);
	}

}
