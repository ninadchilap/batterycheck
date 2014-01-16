package com.iitb.aakash.batterycheck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class SQLiteAdapter {

	public static final String MYDATABASE_NAME = "BATTERY_DATA";
	public static final String MYDATABASE_TABLE = "MY_TABLE";
	public static final int MYDATABASE_VERSION = 1;
	public static final String KEY_ID = "_id";
	public static final String TIME_IN = "Start_Time";
	public static final String TIME_OUT = "End_time";
	public static final String START_PER = "Start_Per";
	public static final String END_PER = "End_Per";
	public static final String START_DATE = "Start_Date";
	public static final String END_DATE = "End_Date";
	// create table MY_DATABASE (ID integer primary key, Content text not null);
	private static final String SCRIPT_CREATE_DATABASE = "create table "
			+ MYDATABASE_TABLE + " (" + KEY_ID
			+ " integer primary key autoincrement," + TIME_IN
			+ " text not null," + TIME_OUT + " text not null,"
			+ START_PER + " text not null,"+ END_PER
			+ " text not null," + START_DATE + " text not null,"
			+ END_DATE + " text not null);";

	private SQLiteHelper sqLiteHelper;
	private SQLiteDatabase sqLiteDatabase;

	private Context context;

	public SQLiteAdapter(Context c) {
		context = c;
	}

	public SQLiteAdapter openToRead() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null,
				MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getReadableDatabase();
		return this;
	}

	public SQLiteAdapter openToWrite() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null,
				MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		sqLiteHelper.close();
	}

	public long insert(String content1, String content2, String content3,String content4, String content5, String content6) {

		ContentValues contentValues = new ContentValues();
		contentValues.put(TIME_IN, content1);
		contentValues.put(TIME_OUT, content2);
		contentValues.put(START_PER, content3);
		contentValues.put(END_PER, content4);
		contentValues.put(START_DATE, content5);
		contentValues.put(END_DATE, content6);
		return sqLiteDatabase.insert(MYDATABASE_TABLE, null, contentValues);
	}

	public int deleteAll() {
		return sqLiteDatabase.delete(MYDATABASE_TABLE, null, null);
	}

	public void delete_byID(int id) {
		sqLiteDatabase.delete(MYDATABASE_TABLE, KEY_ID + "=" + id, null);
	}

	public void update_byID(int id, String v1, String v2, String v3, String v4, String v5, String v6) {
		ContentValues values = new ContentValues();
		values.put(TIME_IN, v1);
		values.put(TIME_OUT, v2);
		values.put(START_PER, v3);
		values.put(END_PER, v4);
		values.put(START_DATE, v5);
		values.put(END_DATE, v6);
		sqLiteDatabase
				.update(MYDATABASE_TABLE, values, KEY_ID + "=" + id, null);
	}

	public Cursor queueAll() {
		String[] columns = new String[] { KEY_ID, TIME_IN, TIME_OUT,
				START_PER,END_PER,START_DATE,END_DATE };
		Cursor cursor = sqLiteDatabase.query(MYDATABASE_TABLE, columns, null,
				null, null, null, null);
		return cursor;
	}

	public class SQLiteHelper extends SQLiteOpenHelper {

		public SQLiteHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(SCRIPT_CREATE_DATABASE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}

	}

}
