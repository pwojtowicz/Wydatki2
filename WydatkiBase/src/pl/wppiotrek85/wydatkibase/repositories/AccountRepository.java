package pl.wppiotrek85.wydatkibase.repositories;

import java.util.ArrayList;
import java.util.Date;

import pl.wppiotrek85.wydatkibase.database.WydatkiBaseHelper;
import pl.wppiotrek85.wydatkibase.entities.Account;
import pl.wppiotrek85.wydatkibase.interfaces.IObjectRepository;
import pl.wppiotrek85.wydatkibase.managers.DataBaseManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

public class AccountRepository implements IObjectRepository<Account> {

	DataBaseManager dbm = DataBaseManager.getInstance();

	private final String INSERT_TO_ACCOUNT = "INSERT INTO "
			+ WydatkiBaseHelper.TABLE_ACCOUNTS
			+ "(Name, Balance,LastActionDate, IsActive, IsSumInGlobalBalance, ImageIndex)  Values(?,?,?,?,?,?)";

	@Override
	public int create(Account item) {
		if (item != null) {
			dbm.checkIsOpen();

			SQLiteStatement insertStmt = dbm.getDataBase().compileStatement(
					INSERT_TO_ACCOUNT);
			insertStmt.bindString(1, item.getName());
			insertStmt.bindDouble(2, item.getBalance());
			insertStmt.bindLong(3, item.getLastActionDate().getTime());
			insertStmt.bindLong(4, item.isActive() ? 1 : 0);
			insertStmt.bindLong(5, item.isSumInGlobalBalance() ? 1 : 0);
			insertStmt.bindLong(6, item.getImageIndex());

			insertStmt.executeInsert();

			int id = dbm.getLastIncremetValue();
			dbm.close();
			return id;
		}
		return -1;

	}

	@Override
	public Account update(Account item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Account> readAll() {
		dbm.checkIsOpen();
		ArrayList<Account> list = new ArrayList<Account>();
		Cursor cursor = dbm
				.getDataBase()
				.query(WydatkiBaseHelper.TABLE_ACCOUNTS,
						new String[] { "ID,Name, Balance,LastActionDate, IsActive, IsSumInGlobalBalance, ImageIndex" },
						null, null, null, null, "Name");
		if (cursor.moveToFirst()) {
			do {
				Account a = new Account();
				a.setId(cursor.getInt(0));
				a.setName(cursor.getString(1));
				a.setBalance(cursor.getDouble(2));
				a.setLastActionDate(new Date(cursor.getLong(3)));
				a.setIsActive(cursor.getLong(4) == 1 ? true : false);
				a.setIsSumInGlobalBalance(cursor.getLong(5) == 1 ? true : false);
				a.setImageIndex((byte) cursor.getLong(6));
				a.setIsVisibleForAll(true);
				list.add(a);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		dbm.close();
		return list;
	}

	@Override
	public boolean delete(Account item) {
		return delete(item.getId());
	}

	@Override
	public boolean delete(int id) {
		dbm.checkIsOpen();
		int result = dbm.getDataBase().delete(WydatkiBaseHelper.TABLE_ACCOUNTS,
				"ID=?", new String[] { String.valueOf(id) });
		dbm.close();
		return result > 0 ? true : false;
	}

}
