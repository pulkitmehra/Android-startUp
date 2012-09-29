package com.myexample.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class ReadOperationTemplate extends DatabaseOperations implements
		AndroidDaoTemplate {

	private SQLiteOpenHelper sqlLiteOpenHelper;

	public ReadOperationTemplate(SQLiteOpenHelper sqlLiteOpenHelper) {
		super();
		this.sqlLiteOpenHelper = sqlLiteOpenHelper;
	}

	public Object execute() {
		SQLiteDatabase sqLiteDatabase = openReadable(sqlLiteOpenHelper);
		Object executeRead = executeRead(sqLiteDatabase);
		//close(sqlLiteOpenHelper);
		return executeRead;
	}

	public abstract Object executeRead(SQLiteDatabase sqLiteDatabase);

}
