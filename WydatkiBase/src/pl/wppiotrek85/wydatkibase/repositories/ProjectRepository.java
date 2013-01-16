package pl.wppiotrek85.wydatkibase.repositories;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.database.WydatkiBaseHelper;
import pl.wppiotrek85.wydatkibase.entities.Project;
import pl.wppiotrek85.wydatkibase.exceptions.RepositoryException;
import pl.wppiotrek85.wydatkibase.interfaces.IObjectRepositoryArrayList;
import pl.wppiotrek85.wydatkibase.managers.DataBaseManager;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

public class ProjectRepository implements IObjectRepositoryArrayList<Project> {

	DataBaseManager dbm = DataBaseManager.getInstance();

	private final String INSERT_TO_PROJECTS = "INSERT INTO "
			+ WydatkiBaseHelper.TABLE_PROJECTS
			+ "(Name, IsActive)  Values(?,?)";

	@Override
	public int create(Project item) {
		if (item != null) {
			dbm.checkIsOpen();
			SQLiteStatement insertStmt = dbm.getDataBase().compileStatement(
					INSERT_TO_PROJECTS);
			insertStmt.bindString(1, item.getName());
			insertStmt.bindLong(2, item.isActive() ? 1 : 0);
			insertStmt.executeInsert();

			int id = dbm.getLastIncremetValue();
			dbm.close();
			return id;
		}
		return -1;
	}

	@Override
	public Project update(Project item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Project read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Project> readAll() {
		dbm.checkIsOpen();
		ArrayList<Project> list = new ArrayList<Project>();
		Cursor cursor = dbm.getDataBase().query(
				WydatkiBaseHelper.TABLE_PROJECTS,
				new String[] { "ID,Name, IsActive" }, null, null, null, null,
				"Name");
		if (cursor.moveToFirst()) {
			do {
				Project p = new Project();
				p.setId(cursor.getInt(0));
				p.setName(cursor.getString(1));
				p.setIsActive(cursor.getLong(2) == 1 ? true : false);

				if (WydatkiGlobals.getInstance().isLocalVersion())
					p.setIsVisibleForAll(true);
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
	public boolean delete(int id) {
		dbm.checkIsOpen();
		int result = dbm.getDataBase().delete(WydatkiBaseHelper.TABLE_PROJECTS,
				"ID=?", new String[] { String.valueOf(id) });
		dbm.close();
		return result > 0 ? true : false;
	}

	@Override
	public int createAllFromList(ArrayList<Project> items) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean delete(ArrayList<Integer> ids) throws RepositoryException {
		// TODO Auto-generated method stub
		return false;
	}

}
