package pl.wppiotrek85.wydatkibase.repositoriesweb;

import java.util.ArrayList;
import java.util.Arrays;

import pl.wppiotrek85.wydatkibase.entities.ItemsContainer;
import pl.wppiotrek85.wydatkibase.entities.ModelBase;
import pl.wppiotrek85.wydatkibase.entities.Project;
import pl.wppiotrek85.wydatkibase.exceptions.RepositoryException;
import pl.wppiotrek85.wydatkibase.interfaces.IObjectRepositoryArrayList;
import pl.wppiotrek85.wydatkibase.providers.AbstractProvider;
import pl.wppiotrek85.wydatkibase.providers.CommunicationException;
import pl.wppiotrek85.wydatkibase.providers.Provider;

public class ProjectRepositoryWeb extends AbstractProvider implements
		IObjectRepositoryArrayList<Project> {

	@Override
	public int createAllFromList(ItemsContainer<ModelBase> items) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int create(Project item) {
		String url = server + "/projects/project";

		Provider<Project> provider = new Provider<Project>(Project.class);
		try {
			Project p = provider.sendObject(url, item, true, null);
			return p.getId();
		} catch (CommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
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
		String url = server + "/projects/project/" + String.valueOf(id);

		Provider<Project> provider = new Provider<Project>(Project.class);

		try {
			provider.deleteObject(url, null);
			return true;
		} catch (CommunicationException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public ArrayList<Project> readAll() {
		try {
			String url = server + "/projects";

			Provider<Project[]> provider = new Provider<Project[]>(
					Project[].class);
			Project[] objects = provider.getObjects(url, null);
			return new ArrayList<Project>(Arrays.asList(objects));
		} catch (CommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
