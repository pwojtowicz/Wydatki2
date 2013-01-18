package pl.wppiotrek85.wydatkibase.repositoriesweb;

import java.util.ArrayList;
import java.util.Arrays;

import pl.wppiotrek85.wydatkibase.entities.Category;
import pl.wppiotrek85.wydatkibase.entities.ItemsContainer;
import pl.wppiotrek85.wydatkibase.entities.ModelBase;
import pl.wppiotrek85.wydatkibase.exceptions.RepositoryException;
import pl.wppiotrek85.wydatkibase.interfaces.IObjectRepositoryArrayList;
import pl.wppiotrek85.wydatkibase.providers.AbstractProvider;
import pl.wppiotrek85.wydatkibase.providers.CommunicationException;
import pl.wppiotrek85.wydatkibase.providers.Provider;

public class CategoryRepositoryWeb extends AbstractProvider implements
		IObjectRepositoryArrayList<Category> {

	@Override
	public int createAllFromList(ItemsContainer<ModelBase> items) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int create(Category item) {
		String url = server + "/categories/category";

		Provider<Category> provider = new Provider<Category>(Category.class);
		try {
			Category c = provider.sendObject(url, item, true, null);
			return c.getId();
		} catch (CommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
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
	public boolean delete(ArrayList<ModelBase> items)
			throws RepositoryException {
		boolean result = true;

		for (ModelBase item : items) {
			result = delete(item.getId());
			if (!result)
				break;
		}
		return result;
	}

	@Override
	public boolean delete(int id) throws RepositoryException {
		throw new ExceptionInInitializerError();
	}

	@Override
	public ArrayList<Category> readAll() {

		try {
			Provider<Category[]> provider = new Provider<Category[]>(
					Category[].class);
			Category[] objects = provider.getObjects(server + "/categories",
					null);
			return new ArrayList<Category>(Arrays.asList(objects));
		} catch (CommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
