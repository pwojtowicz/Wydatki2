package pl.wppiotrek85.wydatkibase.repositories;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.database.WydatkiBaseHelper;
import pl.wppiotrek85.wydatkibase.entities.Category;
import pl.wppiotrek85.wydatkibase.entities.Parameter;
import pl.wppiotrek85.wydatkibase.interfaces.IObjectRepository;
import pl.wppiotrek85.wydatkibase.managers.DataBaseManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

public class CategoryRepository implements IObjectRepository<Category> {

	DataBaseManager dbm = DataBaseManager.getInstance();

	private final String INSERT_TO_CATEGORIES = "INSERT INTO "
			+ WydatkiBaseHelper.TABLE_CATERORIES
			+ "(Name, IsActive, isPositive, ParentId)  Values(?,?,?,?)";

	private final String INSERT_TO_CATERORY_PARAMETERS = "INSERT INTO "
			+ WydatkiBaseHelper.TABLE_CATERORY_PARAMETERS
			+ "(catId, parId)  Values(?,?)";

	@Override
	public int create(Category item) {
		if (item != null) {
			dbm.checkIsOpen();
			SQLiteStatement insertStmt = dbm.getDataBase().compileStatement(
					INSERT_TO_CATEGORIES);
			insertStmt.bindString(1, item.getName());
			insertStmt.bindLong(2, item.isActive() ? 1 : 0);
			insertStmt.bindLong(3, item.isPositive() ? 1 : 0);
			insertStmt.bindLong(4, item.getParentId());
			long result = insertStmt.executeInsert();
			if (result > 0) {
				int id = dbm.getLastIncremetValue();
				if (id > 0) {
					insertCategoryParameter(id, item.getAttributes());
				}
				dbm.close();
				return (int) id;
			}
		}
		return -1;
	}

	private void insertCategoryParameter(int categoryId, Parameter[] parameters) {
		dbm.getDataBase().delete(WydatkiBaseHelper.TABLE_CATERORY_PARAMETERS,
				"catId=?", new String[] { String.valueOf(categoryId) });
		if (parameters != null)
			for (Parameter parameter : parameters) {
				SQLiteStatement insertStmt = dbm.getDataBase()
						.compileStatement(INSERT_TO_CATERORY_PARAMETERS);
				insertStmt.bindLong(1, categoryId);
				insertStmt.bindLong(2, parameter.getId());
				insertStmt.executeInsert();
			}
	}

	@Override
	public Category update(Category item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Category> readAll() {
		dbm.checkIsOpen();
		ArrayList<Category> list = new ArrayList<Category>();
		Cursor cursor = dbm.getDataBase().query(
				WydatkiBaseHelper.TABLE_CATERORIES,
				new String[] { "ID,Name, IsActive, isPositive, ParentId" },
				null, null, null, null, "Name");
		if (cursor.moveToFirst()) {
			do {
				Category item = new Category();
				item.setId(cursor.getInt(0));
				item.setName(cursor.getString(1));
				item.setIsActive(cursor.getLong(2) == 1 ? true : false);
				item.setIsPositive(cursor.getLong(3) == 1 ? true : false);
				item.setParentId((int) cursor.getLong(4));

				item.setAttributes(getCategoryParameters(item.getId()));

				list.add(item);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		dbm.close();
		return list;
	}

	private Parameter[] getCategoryParameters(int categoryId) {
		ArrayList<Parameter> list = new ArrayList<Parameter>();
		Cursor cursor = dbm.getDataBase().query(
				WydatkiBaseHelper.TABLE_CATERORY_PARAMETERS,
				new String[] { "catId, parId" }, "catId=?",
				new String[] { String.valueOf(categoryId) }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Parameter item = new Parameter();
				int parameterId = cursor.getInt(1);
				item.setId(parameterId);
				list.add(item);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list.toArray(new Parameter[list.size()]);
	}

	@Override
	public boolean delete(Category item) {
		if (item != null) {
			return delete(item.getId());
		}
		return false;
	}

	@Override
	public boolean delete(int id) {
		dbm.checkIsOpen();
		int removedCount = dbm.getDataBase().delete(
				WydatkiBaseHelper.TABLE_CATERORY_PARAMETERS, "catId=?",
				new String[] { String.valueOf(id) });

		long result = dbm.getDataBase().delete(
				WydatkiBaseHelper.TABLE_CATERORIES, "ID=?",
				new String[] { String.valueOf(id) });
		dbm.close();
		return result > 0 ? true : false;
	}

}
