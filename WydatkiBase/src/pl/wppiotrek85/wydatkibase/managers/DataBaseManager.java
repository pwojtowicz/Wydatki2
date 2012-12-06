package pl.wppiotrek85.wydatkibase.managers;

import pl.wppiotrek85.wydatkibase.database.WydatkiBaseHelper;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseManager {
	private int openConnections;
	private WydatkiBaseHelper dbHelper;
	private SQLiteDatabase db;
	private Context context;

	private static volatile DataBaseManager instance = null;

	public static DataBaseManager getInstance() {
		return instance;
	}

	public static DataBaseManager inicjalizeInstance(Context context) {
		if (instance == null) {
			synchronized (DataBaseManager.class) {
				if (instance == null) {
					instance = new DataBaseManager(context);
				}
			}
		}
		return instance;
	}

	public DataBaseManager(Context context) {
		this.context = context;
		dbHelper = new WydatkiBaseHelper(this.context);

	}

	public SQLiteDatabase getDataBase() {
		return db;
	}

	public void checkIsOpen() {
		if (this.db == null)
			this.db = dbHelper.getWritableDatabase();

		if (!this.db.isOpen())
			this.db = dbHelper.getWritableDatabase();
		openConnections++;
	}

	public void close() {
		if (openConnections == 1) {
			this.db.close();
		}
		openConnections--;
	}

	public int getLastIncremetValue() {
		try {
			Cursor c = db.rawQuery("select last_insert_rowid();", null);
			c.moveToFirst();
			return c.getInt(0);
		} catch (Exception e) {
			return -1;
		}

	}
}
