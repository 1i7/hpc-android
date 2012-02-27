package clock.screens;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class DBContentProvider extends ContentProvider {

	public static final String DATABASE_NAME = "GPSclocks.db";
	public static final String TABLE_NAME = "DB_locations";
	public static final String CLOC_NAME = "C_lname";
	public static final String CLNG = "C_lng";
	public static final String CLAT = "C_lat";

	public static final Uri CONTENT_URI = Uri
			.parse("content://ru.db.locationlist.contactprovider/contact");
	public static final int URI_CODE = 1;
	public static final int URI_CODE_ID = 2;

	private static final UriMatcher mUriMatcher;
	private static final int DATABASE_VERSION = 1;
	private static HashMap<String, String> notesProjectionMap;

	private DBSQLHelper dbHelper;

	static {
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		mUriMatcher.addURI("ru.db.locationlist.contactprovider", TABLE_NAME,
				URI_CODE);
		mUriMatcher.addURI("ru.db.locationlist.contactprovider", TABLE_NAME
				+ "/#", URI_CODE_ID);

		notesProjectionMap = new HashMap<String, String>();
		// notesProjectionMap.put(DBSQLHelper.,);
		notesProjectionMap.put(CLOC_NAME, CLOC_NAME);
		notesProjectionMap.put(CLNG, CLNG);
		notesProjectionMap.put(CLAT, CLAT);
	}

	private static class DBSQLHelper extends SQLiteOpenHelper {

		public DBSQLHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(" CREATE TABLE " + TABLE_NAME + "( "
					+ " ID INTEGER PRIMARY KEY AUTOINCREMENT, " + CLOC_NAME
					+ " VARCHAR(255), " + CLNG + " INT ZEROFILL, " + CLAT
					+ " INT ZEROFILL);");

			// Так ли?
			ContentValues values = new ContentValues();

			values.put(CLOC_NAME, "Home");
			db.insert(TABLE_NAME, DATABASE_NAME, values);

			values.put(CLOC_NAME, "Work");
			db.insert(TABLE_NAME, DATABASE_NAME, values);

			values.put(CLOC_NAME, "School");
			db.insert(TABLE_NAME, DATABASE_NAME, values);

			values.put(CLOC_NAME, "University");
			db.insert(TABLE_NAME, DATABASE_NAME, values);

			values.put(CLOC_NAME, "Gym");
			db.insert(TABLE_NAME, DATABASE_NAME, values);

			values.put(CLOC_NAME, "Friend's house");
			db.insert(TABLE_NAME, DATABASE_NAME, values);

			values.put(CLOC_NAME, "Other");
			db.insert(TABLE_NAME, DATABASE_NAME, values);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			// DATABASE_VERSION = DATABASE_VERSION + 1;
			onCreate(db);

		}

	}

	public String getDBName() {
		return (DATABASE_NAME);
	}

	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count = db.delete(TABLE_NAME, where, whereArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {// ?????????????????????????????????????????????????????
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues inValues) {
		ContentValues values;
		if (inValues != null) {
			values = new ContentValues(inValues);
		} else {
			values = new ContentValues();
		}

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long rowID = db.insert(TABLE_NAME, null, values);// Что вместо null
		if (rowID > 0) {
			Uri noteUri = ContentUris.withAppendedId(CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(noteUri, null);
			return noteUri;
		}
		throw new SQLException("Failed to insert row into " + uri);// надо
																	// ли????
	}

	@Override
	public boolean onCreate() {
		dbHelper = new DBSQLHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(notesProjectionMap);

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);

		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String where,
			String[] whereArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count = db.update(TABLE_NAME, values, where, whereArgs);

		return count;
	}

}
