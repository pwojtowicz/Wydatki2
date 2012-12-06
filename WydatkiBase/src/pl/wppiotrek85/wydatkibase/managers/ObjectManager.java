package pl.wppiotrek85.wydatkibase.managers;

import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryManagerMethods;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryTypes;
import pl.wppiotrek85.wydatkibase.interfaces.IObjectRepository;
import pl.wppiotrek85.wydatkibase.interfaces.IReadRepository;
import pl.wppiotrek85.wydatkibase.repositories.AccountRepository;

public class ObjectManager {
	
	
	
	public ObjectManager(ERepositoryTypes type, IReadRepository listener, ERepositoryManagerMethods method){
		IObjectRepository repository=createRepository(type);
		startTask(repository, listener, method);
	}
	
	public ObjectManager(ERepositoryTypes type, IReadRepository listener, ERepositoryManagerMethods method, Object item){
		IObjectRepository repository=createRepository(type);
		startTask(repository, listener, method);
	}
	
	private IObjectRepository createRepository(ERepositoryTypes type){
		IObjectRepository repository=null;
		switch (type) {
		case Acoounts:
			repository= new AccountRepository();			
			break;
		}
		return repository;
	}
	
	private void startTask(IObjectRepository repository,IReadRepository listener, ERepositoryManagerMethods method){
		ReadRepositoryAsyncTask task = new ReadRepositoryAsyncTask(listener, method, repository);
		task.execute((Void)null);
	}

}
