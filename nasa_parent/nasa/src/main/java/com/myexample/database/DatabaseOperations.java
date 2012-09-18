package com.myexample.database;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class DatabaseOperations {

	public SQLiteDatabase openWritable(SQLiteOpenHelper sqLiteOpenHelper)
			throws SQLException {
		SQLiteDatabase database = sqLiteOpenHelper.getWritableDatabase();
		return database;
	}

	public SQLiteDatabase openReadable(SQLiteOpenHelper sqLiteOpenHelper)
			throws SQLException {
		SQLiteDatabase database = sqLiteOpenHelper.getReadableDatabase();
		return database;
	}

	public void close(SQLiteOpenHelper sqLiteOpenHelper) {
		sqLiteOpenHelper.close();
	}

}
