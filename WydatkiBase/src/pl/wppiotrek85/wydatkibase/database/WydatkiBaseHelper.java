package pl.wppiotrek85.wydatkibase.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WydatkiBaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "wydatki.db";
	private static final int DATABASE_VERSION = 1;

	public static final String TABLE_ACCOUNTS = "Account";

	private static final String CREATE_ACCOUNTS = "create table "
			+ TABLE_ACCOUNTS
			+ "(ID integer primary key autoincrement, Name text not null);";

	public WydatkiBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_ACCOUNTS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
		onCreate(db);
	}
}
