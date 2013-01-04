package pl.wppiotrek85.wydatkibase.managers;

import java.util.ArrayList;

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
import pl.wppiotrek85.wydatkibase.repositories.TransactionRepository;
import android.os.Bundle;

public class ObjectManager {

	public ObjectManager(ERepositoryTypes type, IReadRepository listener,
			ERepositoryManagerMethods method) {
		IObjectRepository repository = createRepository(type);
		startTask(repository, listener, method, null, -1, -1, -1);
	}

	public ObjectManager(ERepositoryTypes type, IReadRepository listener,
			int skip, int take, int accountId) {
		IObjectRepository repository = createRepository(type);
		startTask(repository, listener,
				ERepositoryManagerMethods.ReadAllWithSkip, null, skip, take,
				accountId);
	}

	public ObjectManager(ERepositoryTypes type, IReadRepository listener,
			ERepositoryManagerMethods method, ModelBase item) {
		IObjectRepository repository = createRepository(type);
		startTask(repository, listener, method, item, -1, -1, -1);
	}

	public ObjectManager(ERepositoryTypes type, IReadRepository listener,
			ERepositoryManagerMethods method, ArrayList<ModelBase> items) {
		IObjectRepository repository = createRepository(type);
		startTask(items, repository, listener, method);
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
		case Transactions:
			return new TransactionRepository();
		}
		return null;
	}

	private void startTask(IObjectRepository repository,
			IReadRepository listener, ERepositoryManagerMethods method,
			ModelBase item, int skip, int take, int accountId) {
		ReadRepositoryAsyncTask task;
		if (item != null)
			task = new ReadRepositoryAsyncTask(listener, method, repository,
					item);
		else {
			if (skip != -1 && take != -1) {
				Bundle b = new Bundle();
				b.putInt(ReadRepositoryAsyncTask.BUNDLE_SKIP, skip);
				b.putInt(ReadRepositoryAsyncTask.BUNDLE_TAKE, take);
				b.putInt(ReadRepositoryAsyncTask.BUNDLE_ID, accountId);
				task = new ReadRepositoryAsyncTask(listener, method,
						repository, b);
			} else
				task = new ReadRepositoryAsyncTask(listener, method, repository);
		}
		task.execute((Void) null);
	}

	private void startTask(ArrayList<ModelBase> items,
			IObjectRepository repository, IReadRepository listener,
			ERepositoryManagerMethods method) {
		ReadRepositoryAsyncTask task;

		task = new ReadRepositoryAsyncTask(listener, method, repository, items);

		task.execute((Void) null);
	}

}
