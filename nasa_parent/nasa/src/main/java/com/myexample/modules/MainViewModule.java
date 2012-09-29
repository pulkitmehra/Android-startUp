package com.myexample.modules;

import com.google.inject.AbstractModule;
import com.myexample.database.TimeTrackerDatabase;
import com.myexample.database.dao.TimeTrackerDao;
import com.myexample.database.dao.TimeTrackerDaoImpl;
import com.myexample.time.list.TimeTrackerAdapter;

public class MainViewModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(TimeTrackerAdapter.class);
		bind(TimeTrackerDatabase.class);
		bind(TimeTrackerDao.class).to(TimeTrackerDaoImpl.class);
	}

}
