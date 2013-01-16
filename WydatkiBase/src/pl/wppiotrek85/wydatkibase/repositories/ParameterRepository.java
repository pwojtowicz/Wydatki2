package pl.wppiotrek85.wydatkibase.repositories;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.database.WydatkiBaseHelper;
import pl.wppiotrek85.wydatkibase.entities.Parameter;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryException;
import pl.wppiotrek85.wydatkibase.exceptions.RepositoryException;
import pl.wppiotrek85.wydatkibase.interfaces.IObjectRepositoryArrayList;
import pl.wppiotrek85.wydatkibase.managers.DataBaseManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

public class ParameterRepository implements
		IObjectRepositoryArrayList<Parameter> {

	DataBaseManager dbm = DataBaseManager.getInstance();

	private final String INSERT_TO_PARAMETERS = "INSERT INTO "
			+ WydatkiBaseHelper.TABLE_PARAMETERS
			+ "(Name, TypeId, DefaultValue, IsActive, DataSource)  Values(?,?,?,?,?)";

	@Override
	public int create(Parameter item) {
		if (item != null) {
			dbm.checkIsOpen();
			SQLiteStatement insertStmt = dbm.getDataBase().compileStatement(
					INSERT_TO_PARAMETERS);
			insertStmt.bindString(1, item.getName());
			insertStmt.bindLong(2, item.getTypeId());
			insertStmt.bindString(3, item.getDefaultValue());
			insertStmt.bindLong(4, item.isActive() ? 1 : 0);
			insertStmt.bindString(5,
					item.getDataSource() != null ? item.getDataSource() : "");
			insertStmt.executeInsert();

			int id = dbm.getLastIncremetValue();
			dbm.close();
			return id;
		}
		return -1;
	}

	@Override
	public Parameter update(Parameter item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Parameter read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Parameter> readAll() {
		dbm.checkIsOpen();
		ArrayList<Parameter> list = new ArrayList<Parameter>();
		Cursor cursor = dbm
				.getDataBase()
				.query(WydatkiBaseHelper.TABLE_PARAMETERS,
						new String[] { "ID,Name, TypeId, DefaultValue, IsActive, DataSource" },
						null, null, null, null, "Name");
		if (cursor.moveToFirst()) {
			do {
				Parameter p = new Parameter();
				p.setId(cursor.getInt(0));
				p.setName(cursor.getString(1));
				p.setTypeId(cursor.getInt(2));
				p.setDefaultValue(cursor.getString(3));
				p.setIsActive(cursor.getLong(4) == 1 ? true : false);
				p.setDataSource(cursor.getString(5));
				list.add(p);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		dbm.close();
		return list;
	}

	@Override
	public boolean delete(int id) throws RepositoryException {
		Cursor c = dbm.getDataBase().rawQuery(
				"SELECT COUNT(1) FROM "
						+ WydatkiBaseHelper.TABLE_CATERORY_PARAMETERS
						+ " WHERE parId=?;",
				new String[] { String.valueOf(id) });
		c.moveToFirst();
		int count = c.getInt(0);
		if (count == 0) {
			dbm.checkIsOpen();
			int result = dbm.getDataBase().delete(
					WydatkiBaseHelper.TABLE_PARAMETERS, "ID=?",
					new String[] { String.valueOf(id) });
			dbm.close();
			return result > 0 ? true : false;
		} else
			throw new RepositoryException(
					ERepositoryException.ParameterJoinToCategory, null);

	}

	@Override
	public int createAllFromList(ArrayList<Parameter> items) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean delete(ArrayList<Integer> ids) throws RepositoryException {
		// TODO Auto-generated method stub
		return false;
	}

}
