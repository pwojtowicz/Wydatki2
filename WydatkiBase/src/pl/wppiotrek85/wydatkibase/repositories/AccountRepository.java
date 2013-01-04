package pl.wppiotrek85.wydatkibase.repositories;

import java.util.ArrayList;
import java.util.Date;

import pl.wppiotrek85.wydatkibase.database.WydatkiBaseHelper;
import pl.wppiotrek85.wydatkibase.entities.Account;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryException;
import pl.wppiotrek85.wydatkibase.exceptions.RepositoryException;
import pl.wppiotrek85.wydatkibase.interfaces.IObjectRepositoryArrayList;
import pl.wppiotrek85.wydatkibase.managers.DataBaseManager;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

public class AccountRepository implements IObjectRepositoryArrayList<Account> {

	DataBaseManager dbm = DataBaseManager.getInstance();

	private final String INSERT_TO_ACCOUNT = "INSERT INTO "
			+ WydatkiBaseHelper.TABLE_ACCOUNTS
			+ "(Name, Balance,LastActionDate, IsActive, IsSumInGlobalBalance, ImageIndex)  Values(?,?,?,?,?,?)";

	private boolean doNotOpenAndCloseDB = false;

	public AccountRepository() {

	}

	public AccountRepository(DataBaseManager dbm) {
		this.dbm = dbm;
		this.doNotOpenAndCloseDB = true;
	}

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

	public int updateAccountBalance(int acoountId, double balance,
			boolean isPositove) throws RepositoryException {
		Account a = read(acoountId);

		ContentValues values = new ContentValues();
		values.put("Balance",
				(isPositove ? a.getBalance() + balance : a.getBalance()
						- balance));
		values.put("LastActionDate", new Date().getTime());

		long result = dbm.getDataBase().update(
				WydatkiBaseHelper.TABLE_ACCOUNTS, values, "ID = ?",
				new String[] { String.valueOf(acoountId) });

		if (result == 0)
			throw new RepositoryException(
					ERepositoryException.CanNotUpdateAccountBalance);

		return (int) result;

	}

	@Override
	public Account read(int id) {
		if (!doNotOpenAndCloseDB)
			dbm.checkIsOpen();
		Account a = null;
		Cursor cursor = dbm
				.getDataBase()
				.query(WydatkiBaseHelper.TABLE_ACCOUNTS,
						new String[] { "ID,Name, Balance,LastActionDate, IsActive, IsSumInGlobalBalance, ImageIndex" },
						"ID = ?", new String[] { String.valueOf(id) }, null,
						null, null);
		if (cursor.moveToFirst()) {
			do {
				a = new Account();
				a.setId(cursor.getInt(0));
				a.setName(cursor.getString(1));
				a.setBalance(cursor.getDouble(2));
				a.setLastActionDate(new Date(cursor.getLong(3)));
				a.setIsActive(cursor.getLong(4) == 1 ? true : false);
				a.setIsSumInGlobalBalance(cursor.getLong(5) == 1 ? true : false);
				a.setImageIndex((byte) cursor.getLong(6));
				if (WydatkiGlobals.getInstance().isLocalVersion())
					a.setIsVisibleForAll(true);

			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		if (!doNotOpenAndCloseDB)
			dbm.close();
		return a;
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
				if (WydatkiGlobals.getInstance().isLocalVersion())
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
				"ID = ?", new String[] { String.valueOf(id) });
		dbm.close();
		return result > 0 ? true : false;
	}

	@Override
	public int createAllFromList(ArrayList<Account> items) {
		// TODO Auto-generated method stub
		return 0;
	}

}
