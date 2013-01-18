package pl.wppiotrek85.wydatkibase.repositories;

import java.util.Calendar;
import java.util.Date;

import pl.wppiotrek85.wydatkibase.database.WydatkiBaseHelper;
import pl.wppiotrek85.wydatkibase.entities.CacheInfo;
import pl.wppiotrek85.wydatkibase.managers.DataBaseManager;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;

public class CacheRepository {

	DataBaseManager dbm = DataBaseManager.getInstance();

	private static final String INSERT_TO_CACHE = "INSERT INTO "
			+ WydatkiBaseHelper.TABLE_CACHE
			+ "(userLogin, uri, postTAG, eTAG, response, timestamp)  Values(?,?,?,?,?,?)";

	public int create(CacheInfo item) {
		if (item != null) {
			dbm.checkIsOpen();

			SQLiteStatement insertStmt = dbm.getDataBase().compileStatement(
					INSERT_TO_CACHE);
			insertStmt.bindString(1, item.userLogin);
			insertStmt.bindString(2, item.uri);
			insertStmt.bindString(3, item.postTAG);
			insertStmt.bindString(4, item.eTAG);
			insertStmt.bindString(5, item.response);
			insertStmt.bindLong(6, item.timestamp.getTime());

			insertStmt.executeInsert();

			int id = dbm.getLastIncremetValue();
			dbm.close();
			return id;
		}
		return -1;
	}

	public CacheInfo update(CacheInfo item) {
		dbm.checkIsOpen();
		try {
			ContentValues values = new ContentValues();
			values.put("postTAG", item.postTAG);
			values.put("eTAG", item.eTAG);
			values.put("response", item.response);
			long result = this.dbm.getDataBase().update(
					WydatkiBaseHelper.TABLE_CACHE, values,
					"userLogin=? AND uri=?",
					new String[] { item.userLogin, item.uri });
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbm.close();
		return item;

	}

	public CacheInfo read(String uri, String userLogin, String postTAG) {
		dbm.checkIsOpen();
		CacheInfo ci = null;
		try {
			Cursor cursor = this.dbm
					.getDataBase()
					.query(WydatkiBaseHelper.TABLE_CACHE,
							new String[] { "userLogin, uri, postTAG, eTAG, response, timestamp" },
							"userLogin=? AND uri=?",
							new String[] { userLogin, uri }, null, null, null);
			if (cursor.moveToFirst()) {
				do {
					String pTag = cursor.getString(2);
					if (postTAG == null || postTAG.equals(pTag)) {
						ci = new CacheInfo();
						ci.userLogin = cursor.getString(0);
						ci.uri = cursor.getString(1);
						ci.postTAG = cursor.getString(2);
						ci.eTAG = cursor.getString(3);
						ci.response = cursor.getString(4);
						ci.timestamp = new Date(cursor.getLong(5));
						break;
					}

				} while (cursor.moveToNext());
			}
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbm.close();
		return ci;
	}

	public void deleteOldCacheInfo(int dayDelay) {
		dbm.checkIsOpen();
		Date d1 = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d1);
		calendar.add(Calendar.DAY_OF_YEAR, -dayDelay);
		Date d2 = calendar.getTime();
		long result = this.dbm.getDataBase().delete(
				WydatkiBaseHelper.TABLE_CACHE, "timestamp<?",
				new String[] { String.valueOf(d2.getTime()) });
		System.out.println("CACHE: Removed " + String.valueOf(result)
				+ " cache rows");
		dbm.close();
	}

}
