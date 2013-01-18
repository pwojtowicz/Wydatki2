package pl.wppiotrek85.wydatkibase.repositoriesweb;

import java.util.ArrayList;
import java.util.Arrays;

import pl.wppiotrek85.wydatkibase.entities.ItemsContainer;
import pl.wppiotrek85.wydatkibase.entities.ModelBase;
import pl.wppiotrek85.wydatkibase.entities.Parameter;
import pl.wppiotrek85.wydatkibase.exceptions.RepositoryException;
import pl.wppiotrek85.wydatkibase.interfaces.IObjectRepositoryArrayList;
import pl.wppiotrek85.wydatkibase.providers.AbstractProvider;
import pl.wppiotrek85.wydatkibase.providers.CommunicationException;
import pl.wppiotrek85.wydatkibase.providers.Provider;

public class ParameterRepositoryWeb extends AbstractProvider implements
		IObjectRepositoryArrayList<Parameter> {

	@Override
	public int createAllFromList(ItemsContainer<ModelBase> items) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int create(Parameter item) {
		String url = server + "/attributes/parameter";
		Provider<Parameter> provider = new Provider<Parameter>(Parameter.class);
		try {
			Parameter p = provider.sendObject(url, item, true, null);
			return p.getId();
		} catch (CommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
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
		String url = server + "/attributes/parameter/" + String.valueOf(id);

		Provider<Parameter> provider = new Provider<Parameter>(Parameter.class);
		try {
			provider.deleteObject(url, null);
			return true;
		} catch (CommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ArrayList<Parameter> readAll() {
		try {
			Provider<Parameter[]> provider = new Provider<Parameter[]>(
					Parameter[].class);
			Parameter[] objects = provider.getObjects(server + "/attributes",
					null);
			return new ArrayList<Parameter>(Arrays.asList(objects));
		} catch (CommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
