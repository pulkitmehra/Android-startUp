package com.myexample.database;

import roboguice.inject.ContextSingleton;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.inject.Inject;


@ContextSingleton
public class TimeTrackerDatabase extends SQLiteOpenHelper {

	public static final String DEFAULT_DATABASE_NAME = "tracker.db";
	public static final int DATABASE_VERSION = 2;

	public static final String TIME_TABLE = "time_table";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TIME = "time";
	public static final String COLUMN_NOTES = "notes";


	private String dataBaseName;
	public static final String TABLE_NOTES_CREATE = "create table "
			+ TIME_TABLE + "(" + COLUMN_ID
			+ " integer primary key autoincrement," + COLUMN_TIME
			+ " text not null," + COLUMN_NOTES + " text not null );";

	public static final String[] ALL_COLUMNS = {
			TimeTrackerDatabase.COLUMN_NOTES,
			TimeTrackerDatabase.COLUMN_ID, 
			TimeTrackerDatabase.COLUMN_TIME};
	
	
	

	public TimeTrackerDatabase(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Inject
	public TimeTrackerDatabase(Context context) {
		super(context, DEFAULT_DATABASE_NAME, null, DATABASE_VERSION);
		dataBaseName = DEFAULT_DATABASE_NAME;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_NOTES_CREATE);
		Log.w("Creating database", dataBaseName);
		
	}
 

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TimeTrackerDatabase.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		    db.execSQL("DROP TABLE IF EXISTS " + TIME_TABLE);
		    onCreate(db);
	}

}
