package com.myexample.database.dao;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.ContextSingleton;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.inject.Inject;
import com.myexample.database.ReadOperationTemplate;
import com.myexample.database.TimeTrackerDatabase;
import com.myexample.database.WriteOperationTemplate;
import com.myexample.time.list.TimeRecord;

@ContextSingleton
public class TimeTrackerDaoImpl implements TimeTrackerDao {

	@Inject
	protected TimeTrackerDatabase database;

	public TimeRecord addRecord(final TimeRecord timeRecord) {
		WriteOperationTemplate writeOperationTemplate = new WriteOperationTemplate(
				database) {

			@Override
			public Object executeWrite(SQLiteDatabase sqLiteDatabase) {
				ContentValues values = new ContentValues();
				values.put(TimeTrackerDatabase.COLUMN_TIME,
						timeRecord.getTime());
				values.put(TimeTrackerDatabase.COLUMN_NOTES,
						timeRecord.getNotes());
				Long _id = sqLiteDatabase.insert(
						TimeTrackerDatabase.TIME_TABLE, null, values);

				return getRecord(_id);
			}
		};
		return (TimeRecord) writeOperationTemplate.execute();
	}

	public TimeRecord getRecord(final Long timeRecordId) {

		return (TimeRecord) new ReadOperationTemplate(database) {

			@Override
			public Object executeRead(SQLiteDatabase sqLiteDatabase) {
				Cursor cursor = sqLiteDatabase.query(
						TimeTrackerDatabase.TIME_TABLE,
						TimeTrackerDatabase.ALL_COLUMNS, "_rowid_ = "
								+ timeRecordId, null, null, null, null);
				cursor.moveToFirst();
				TimeRecord timeRecord = convertToTimeRecord(cursor);
				return timeRecord;
			}
		}.execute();
	}

	private TimeRecord convertToTimeRecord(Cursor cursor) {

		Integer id = cursor.getInt(cursor
				.getColumnIndex(TimeTrackerDatabase.COLUMN_ID));
		String time = cursor.getString(cursor
				.getColumnIndex(TimeTrackerDatabase.COLUMN_TIME));
		String notes = cursor.getString(cursor
				.getColumnIndex(TimeTrackerDatabase.COLUMN_NOTES));

		TimeRecord record = new TimeRecord();
		record.setId(id);
		record.setTime(time);
		record.setNotes(notes);
		return record;
	}

	@SuppressWarnings("unchecked")
	public List<TimeRecord> getAllRecords() {
		return (List<TimeRecord>) new ReadOperationTemplate(database) {

			@TargetApi(16)
			@Override
			public Object executeRead(SQLiteDatabase sqLiteDatabase) {
				List<TimeRecord> timeRecords = new ArrayList<TimeRecord>();
				Cursor cursor = sqLiteDatabase.query(true,
						TimeTrackerDatabase.TIME_TABLE,
						TimeTrackerDatabase.ALL_COLUMNS, null, null, null,
						null, null, null, null);
				cursor.moveToFirst();
				while (!cursor.isAfterLast()) {
					timeRecords.add(convertToTimeRecord(cursor));
					cursor.moveToNext();
				}
				return timeRecords;
			}
		}.execute();
	}

}
