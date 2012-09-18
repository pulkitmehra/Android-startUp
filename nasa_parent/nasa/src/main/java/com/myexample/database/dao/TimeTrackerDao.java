package com.myexample.database.dao;

import java.util.List;

import com.myexample.time.list.TimeRecord;

public interface TimeTrackerDao {

	List<TimeRecord> getAllRecords();

	TimeRecord getRecord(Long timeRecordId);

	TimeRecord addRecord(TimeRecord timeRecord);

}
