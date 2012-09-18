package com.myexample.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class WriteOperationTemplate extends DatabaseOperations
		implements AndroidDaoTemplate {

	private SQLiteOpenHelper sqlLiteOpenHelper;

	public WriteOperationTemplate(SQLiteOpenHelper sqlLiteOpenHelper) {
		super();
		this.sqlLiteOpenHelper = sqlLiteOpenHelper;
	}

	public Object execute() {
		SQLiteDatabase sqLiteDatabase = openWritable(sqlLiteOpenHelper);
		Object executeWrite = executeWrite(sqLiteDatabase);
		close(sqlLiteOpenHelper);
		return executeWrite;
	}

	public abstract Object executeWrite(SQLiteDatabase sqLiteDatabase);

}
