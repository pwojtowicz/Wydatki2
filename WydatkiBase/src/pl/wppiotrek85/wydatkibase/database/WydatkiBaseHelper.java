package pl.wppiotrek85.wydatkibase.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WydatkiBaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "wydatki.db";
	private static final int DATABASE_VERSION = 2;

	public static final String TABLE_ACCOUNTS = "Account";
	public static final String TABLE_CATERORIES = "Category";

	public static final String TABLE_CATERORY_PARAMETERS = "CategoryParameter";

	public static final String TABLE_PARAMETERS = "Parameter";
	public static final String TABLE_PROJECTS = "Project";

	public static final String TABLE_TRANSACTION = "Transactions";
	public static final String TABLE_TRANSACTION_PARAMETERS = "TransactionsParameter";

	private static final String CREATE_ACCOUNTS = "create table "
			+ TABLE_ACCOUNTS
			+ "(ID integer primary key autoincrement, Name text not null, Balance REAL,LastActionDate NUMERIC, IsActive NUMERIC, IsSumInGlobalBalance NUMERIC, ImageIndex INTEGER);";

	private static final String CREATE_CATEGOIRES = "create table "
			+ TABLE_CATERORIES
			+ "(ID integer primary key autoincrement, Name text not null, IsActive NUMERIC, isPositive NUMERIC, ParentId INTEGER);";

	private static final String CREATE_PARAMETERS = "create table "
			+ TABLE_PARAMETERS
			+ "(ID integer primary key autoincrement, Name text not null, TypeId INTEGER, DefaultValue TEXT, IsActive NUMERIC, DataSource TEXT);";

	private static final String CREATE_PROJECTS = "create table "
			+ TABLE_PROJECTS
			+ "(ID integer primary key autoincrement, Name text not null, IsActive NUMERIC);";

	private static final String CREATE_CATERORY_PARAMETERS = "create table "
			+ TABLE_CATERORY_PARAMETERS + "(catId INTEGER, parId INTEGER);";

	private static final String CREATE_TRANSACTION = "create table "
			+ TABLE_TRANSACTION
			+ "(ID INTEGER primary key autoincrement, AccPlus INTEGER, AccMinus INTEGER, Value REAL, ActionDate NUMERIC, Note TEXT, CategoryId INTEGER, ProjectId INTEGER);";

	private static final String CREATE_TRANSACTION_PARAMETERS = "create table "
			+ TABLE_TRANSACTION_PARAMETERS
			+ "(TransactionId INTEGER, ParameterId INTEGER, Value TEXT);";

	public WydatkiBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		SQLiteDatabase db = getWritableDatabase();
		db.needUpgrade(DATABASE_VERSION);
		db.close();
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_ACCOUNTS);
		database.execSQL(CREATE_CATEGOIRES);
		database.execSQL(CREATE_PARAMETERS);
		database.execSQL(CREATE_PROJECTS);
		database.execSQL(CREATE_CATERORY_PARAMETERS);

		database.execSQL(CREATE_TRANSACTION_PARAMETERS);
		database.execSQL(CREATE_TRANSACTION);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATERORY_PARAMETERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATERORIES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARAMETERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION_PARAMETERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
		onCreate(db);
	}
}
