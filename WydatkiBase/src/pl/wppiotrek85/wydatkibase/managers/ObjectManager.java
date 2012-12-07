package pl.wppiotrek85.wydatkibase.managers;

import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask;
import pl.wppiotrek85.wydatkibase.entities.ModelBase;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryManagerMethods;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryTypes;
import pl.wppiotrek85.wydatkibase.interfaces.IObjectRepository;
import pl.wppiotrek85.wydatkibase.interfaces.IReadRepository;
import pl.wppiotrek85.wydatkibase.repositories.AccountRepository;
import pl.wppiotrek85.wydatkibase.repositories.CategoryRepository;
import pl.wppiotrek85.wydatkibase.repositories.ParameterRepository;
import pl.wppiotrek85.wydatkibase.repositories.ProjectRepository;

public class ObjectManager {

	public ObjectManager(ERepositoryTypes type, IReadRepository listener,
			ERepositoryManagerMethods method) {
		IObjectRepository repository = createRepository(type);
		startTask(repository, listener, method, null);
	}

	public ObjectManager(ERepositoryTypes type, IReadRepository listener,
			ERepositoryManagerMethods method, ModelBase item) {
		IObjectRepository repository = createRepository(type);
		startTask(repository, listener, method, item);
	}

	private IObjectRepository createRepository(ERepositoryTypes type) {
		switch (type) {
		case Accounts:
			return new AccountRepository();
		case Categories:
			return new CategoryRepository();
		case Projects:
			return new ProjectRepository();
		case Parameters:
			return new ParameterRepository();
		}
		return null;
	}

	private void startTask(IObjectRepository repository,
			IReadRepository listener, ERepositoryManagerMethods method,
			ModelBase item) {
		ReadRepositoryAsyncTask task;
		if (item != null)
			task = new ReadRepositoryAsyncTask(listener, method, repository,
					item);
		else
			task = new ReadRepositoryAsyncTask(listener, method, repository);
		task.execute((Void) null);
	}

}
