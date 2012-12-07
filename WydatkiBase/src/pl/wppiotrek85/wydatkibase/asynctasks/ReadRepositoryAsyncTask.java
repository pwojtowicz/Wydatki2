package pl.wppiotrek85.wydatkibase.asynctasks;

import pl.wppiotrek85.wydatkibase.entities.ModelBase;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryException;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryManagerMethods;
import pl.wppiotrek85.wydatkibase.exceptions.RepositoryException;
import pl.wppiotrek85.wydatkibase.interfaces.IObjectRepository;
import pl.wppiotrek85.wydatkibase.interfaces.IReadRepository;
import android.os.AsyncTask;

public class ReadRepositoryAsyncTask extends AsyncTask<Void, Void, Void> {

	private IReadRepository listener;
	private ERepositoryManagerMethods method;
	private AsyncTaskResult response;
	private IObjectRepository repository;
	private ModelBase item;
	private int id;

	public ReadRepositoryAsyncTask(IReadRepository listener,
			ERepositoryManagerMethods method, IObjectRepository repository) {
		this.listener = listener;
		this.method = method;
		this.repository = repository;
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
			int id) {
		this.listener = listener;
		this.method = method;
		this.repository = repository;
		this.id = id;
	}

	@Override
	protected Void doInBackground(Void... params) {
		response = new AsyncTaskResult();

		int i = 1;
		while (i < 5) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		try {
			switch (method) {
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
				if (item != null)
					response.bundle = repository.delete(item);
				else
					response.bundle = repository.delete(id);
				break;
			case Read:
				response.bundle = repository.read(id);
				break;
			case ReadAll:
				response.bundle = repository.readAll();
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
