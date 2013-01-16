package pl.wppiotrek85.wydatkibase.asynctasks;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.entities.ModelBase;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryException;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryManagerMethods;
import pl.wppiotrek85.wydatkibase.exceptions.RepositoryException;
import pl.wppiotrek85.wydatkibase.interfaces.IObjectRepository;
import pl.wppiotrek85.wydatkibase.interfaces.IObjectRepositoryArrayList;
import pl.wppiotrek85.wydatkibase.interfaces.IObjectRepositoryItemCointainer;
import pl.wppiotrek85.wydatkibase.interfaces.IReadRepository;
import android.os.AsyncTask;
import android.os.Bundle;

public class ReadRepositoryAsyncTask extends AsyncTask<Void, Void, Void> {

	public final static String BUNDLE_ID = "ID";
	public final static String BUNDLE_SKIP = "SKIP";
	public final static String BUNDLE_TAKE = "TAKE";

	private IReadRepository listener;
	private ERepositoryManagerMethods method;
	private AsyncTaskResult response;
	private IObjectRepository repository;
	private ModelBase item;
	private ArrayList<ModelBase> items;

	private Bundle bundle;

	public ReadRepositoryAsyncTask(IReadRepository listener,
			ERepositoryManagerMethods method, IObjectRepository repository) {
		this.listener = listener;
		this.method = method;
		this.repository = repository;

	}

	public ReadRepositoryAsyncTask(IReadRepository listener,
			ERepositoryManagerMethods method, IObjectRepository repository,
			Bundle bundle) {
		this.listener = listener;
		this.method = method;
		this.repository = repository;
		this.bundle = bundle;
	}

	public ReadRepositoryAsyncTask(IReadRepository listener,
			ERepositoryManagerMethods method, IObjectRepository repository,
			ModelBase item) {
		this.listener = listener;
		this.method = method;
		this.repository = repository;
		this.item = item;
	}

	public ReadRepositoryAsyncTask(IReadRepository listener,
			ERepositoryManagerMethods method, IObjectRepository repository,
			ArrayList<ModelBase> items) {
		this.listener = listener;
		this.method = method;
		this.repository = repository;
		this.items = items;
	}

	@Override
	protected Void doInBackground(Void... params) {
		response = new AsyncTaskResult();

		int i = 1;
		while (i < 1) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i++;
		}
		try {
			int id = 0;
			switch (method) {
			case CreateAllFromList:
				id = repository.createAllFromList(items);
				if (id > 0) {
					response.bundle = items;
				} else
					throw new RepositoryException(
							ERepositoryException.ObjectNotCreated);
				break;
			case Create:
				id = repository.create(item);
				if (id > 0) {
					item.setId(id);
					response.bundle = item;
				} else
					throw new RepositoryException(
							ERepositoryException.ObjectNotCreated);
				break;
			case Delete:
				if (bundle != null) {
					int objectId = bundle.getInt(BUNDLE_ID, 0);
					response.bundle = repository.delete(objectId);
				} else
					throw new RepositoryException(
							ERepositoryException.NoObjectIdBundle);
				break;
			case Read:
				if (bundle != null) {
					int objectId = bundle.getInt(BUNDLE_ID, 0);
					response.bundle = repository.read(objectId);
				} else
					throw new RepositoryException(
							ERepositoryException.NoObjectIdBundle);

				break;
			case ReadAll:
				if (repository instanceof IObjectRepositoryArrayList)
					response.bundle = ((IObjectRepositoryArrayList) repository)
							.readAll();
				else if (repository instanceof IObjectRepositoryItemCointainer)
					response.bundle = ((IObjectRepositoryItemCointainer) repository)
							.readAll();
				break;
			case ReadAllWithSkip:
				if (bundle != null) {
					int skip = bundle.getInt(BUNDLE_SKIP, 0);
					int take = bundle.getInt(BUNDLE_TAKE, 0);
					int accountId = bundle.getInt(BUNDLE_ID, 0);
					if (repository instanceof IObjectRepositoryItemCointainer)
						response.bundle = ((IObjectRepositoryItemCointainer) repository)
								.readAll(skip, take, accountId);
					else
						throw new RepositoryException(
								ERepositoryException.RepositoryDoNotSupportsSkipAndTake);
				} else
					throw new RepositoryException(
							ERepositoryException.NoSkipOrTakeBundle);

				break;
			case Update:
				response.bundle = repository.update(item);
				break;
			}
		} catch (RepositoryException ex) {
			response.exception = ex;
		} catch (Exception ex) {
			response.exception = new RepositoryException(
					ERepositoryException.AsyncTaskException, ex);
		}

		return null;
	}

	@Override
	public void onPostExecute(Void result) {
		super.onPostExecute(result);
		if (listener != null) {
			listener.onTaskEnd();
			if (response.exception == null)
				listener.onTaskResponse(response);
			else
				listener.onTaskInvalidResponse(response.exception);
		}
	}

	@Override
	public void onPreExecute() {
		super.onPreExecute();
		if (listener != null) {
			listener.onTaskStart();
		}
	}

	public void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
		if (listener != null) {
			listener.onTaskProgress();
		}
	}

	public class AsyncTaskResult {
		public Object bundle;
		public RepositoryException exception;
	}

}
