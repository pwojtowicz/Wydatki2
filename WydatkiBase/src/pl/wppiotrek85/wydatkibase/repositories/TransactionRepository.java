package pl.wppiotrek85.wydatkibase.repositories;

import java.util.ArrayList;
import java.util.Date;

import pl.wppiotrek85.wydatkibase.database.WydatkiBaseHelper;
import pl.wppiotrek85.wydatkibase.entities.Category;
import pl.wppiotrek85.wydatkibase.entities.ItemsContainer;
import pl.wppiotrek85.wydatkibase.entities.Parameter;
import pl.wppiotrek85.wydatkibase.entities.Transaction;
import pl.wppiotrek85.wydatkibase.exceptions.RepositoryException;
import pl.wppiotrek85.wydatkibase.interfaces.IObjectRepositoryItemCointainer;
import pl.wppiotrek85.wydatkibase.managers.DataBaseManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;

public class TransactionRepository implements
		IObjectRepositoryItemCointainer<Transaction> {

	DataBaseManager dbm = DataBaseManager.getInstance();

	private final String INSERT_TO_TRANSACTION = "INSERT INTO "
			+ WydatkiBaseHelper.TABLE_TRANSACTION
			+ "(AccPlus, AccMinus, Value,ActionDate, Note, CategoryId, ProjectId)  Values(?,?,?,?,?,?,?)";

	private final String INSERT_TO_TRANSACTION_PARAMETERS = "INSERT INTO "
			+ WydatkiBaseHelper.TABLE_TRANSACTION_PARAMETERS
			+ "(TransactionId, ParameterId, Value)  Values(?,?,?)";

	@Override
	public int createAllFromList(ArrayList<Transaction> items) {
		long result = 0;
		dbm.checkIsOpen();
		dbm.getDataBase().beginTransaction();
		try {
			for (Transaction transaction : items) {
				result = createTransaction(transaction, dbm);
				if (result == 0)
					break;
			}
			if (result != 0)
				dbm.getDataBase().setTransactionSuccessful();
		} catch (SQLException e) {

		} finally {
			dbm.getDataBase().endTransaction();
			dbm.close();
		}

		return (int) result;
	}

	@Override
	public int create(Transaction item) {
		dbm.checkIsOpen();
		dbm.getDataBase().beginTransaction();
		long result = 0;
		try {
			createTransaction(item, dbm);
			dbm.getDataBase().setTransactionSuccessful();
		} catch (SQLException e) {

		} finally {
			dbm.getDataBase().endTransaction();
			dbm.close();
		}
		return (int) result;
	}

	private int createTransaction(Transaction item, DataBaseManager dbm) {
		long result = 0;
		SQLiteStatement insertStmt = dbm.getDataBase().compileStatement(
				INSERT_TO_TRANSACTION);
		insertStmt.bindLong(1, item.getAccPlus());
		insertStmt.bindLong(2, item.getAccMinus());
		insertStmt.bindDouble(3, item.getValue());
		insertStmt.bindLong(4, item.getDate().getTime());
		insertStmt.bindString(5, item.getNote());
		insertStmt.bindLong(6, item.getCategory().getId());
		insertStmt.bindLong(7, item.getProjectId());
		result = insertStmt.executeInsert();
		if (result > 0) {
			int id = 0;
			if (item.getCategory() != null
					&& item.getCategory().getAttributes() != null) {
				id = dbm.getLastIncremetValue();
				if (id > 0) {
					insertTransactionParameter(id, item.getCategory()
							.getAttributes());
				}
			}
		}
		return (int) result;
	}

	private void insertTransactionParameter(int transactionId,
			Parameter[] parameters) {
		if (parameters != null)
			for (Parameter parameter : parameters) {
				SQLiteStatement insertStmt = dbm.getDataBase()
						.compileStatement(INSERT_TO_TRANSACTION_PARAMETERS);
				insertStmt.bindLong(1, transactionId);
				insertStmt.bindLong(2, parameter.getId());
				insertStmt.bindString(3, parameter.getValue());
				insertStmt.executeInsert();
			}
	}

	@Override
	public Transaction update(Transaction item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaction read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Transaction item) throws RepositoryException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) throws RepositoryException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ItemsContainer<Transaction> readAll() {
		dbm.checkIsOpen();
		Cursor cursor = dbm
				.getDataBase()
				.query(WydatkiBaseHelper.TABLE_TRANSACTION,
						new String[] { "ID,AccPlus, AccMinus, Value,ActionDate, Note, CategoryId, ProjectId" },
						null, null, null, null, "ActionDate");
		return read(cursor);
	}

	@Override
	public ItemsContainer<Transaction> readAll(int skip, int count) {
		dbm.checkIsOpen();
		Cursor cursor = dbm
				.getDataBase()
				.query(WydatkiBaseHelper.TABLE_TRANSACTION,
						new String[] { "ID,AccPlus, AccMinus, Value,ActionDate, Note, CategoryId, ProjectId" },
						null,
						null,
						null,
						null,
						"ActionDate DESC "
								+ String.format("LIMIT %d, %d", skip, count));
		return read(cursor);
	}

	private ItemsContainer<Transaction> read(Cursor cursor) {
		ItemsContainer<Transaction> container = new ItemsContainer<Transaction>();
		container.setTotalCount(8);

		ArrayList<Transaction> list = new ArrayList<Transaction>();

		if (cursor.moveToFirst()) {
			do {
				Transaction item = new Transaction();
				item.setId(cursor.getInt(0));
				item.setAccPlus((int) cursor.getLong(1));
				item.setAccMinus((int) cursor.getLong(2));
				item.setValue(cursor.getDouble(3));
				item.setDate(new Date(cursor.getLong(4)));
				item.setNote(cursor.getString(5));
				item.setCategory(new Category(cursor.getInt(6)));
				item.setProjectId((int) cursor.getLong(7));

				list.add(item);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		dbm.close();
		container.setItems(list.toArray(new Transaction[list.size()]));

		return container;
	}

}
