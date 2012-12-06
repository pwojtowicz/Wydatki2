package pl.wppiotrek85.wydatkibase.asynctasks;

import pl.wppiotrek85.wydatkibase.enums.ERepositoryManagerMethods;
import pl.wppiotrek85.wydatkibase.interfaces.IObjectRepository;
import pl.wppiotrek85.wydatkibase.interfaces.IReadRepository;
import android.os.AsyncTask;

public class ReadRepositoryAsyncTask extends AsyncTask<Void, Void, Void> {
	
	
	private IReadRepository listener;
	private ERepositoryManagerMethods method;
	private AsyncTaskResult result;
	private IObjectRepository repository;
	private Object item;
	private int id;

	public ReadRepositoryAsyncTask(IReadRepository listener, ERepositoryManagerMethods method, IObjectRepository repository){
		this.listener=listener;
		this.method=method;
		this.repository=repository;
	}
	
	public ReadRepositoryAsyncTask(IReadRepository listener, ERepositoryManagerMethods method, IObjectRepository repository, Object item){
		this.listener=listener;
		this.method=method;
		this.repository=repository;
		this.item=item;
	}
	
	public ReadRepositoryAsyncTask(IReadRepository listener, ERepositoryManagerMethods method, IObjectRepository repository, int id){
		this.listener=listener;
		this.method=method;
		this.repository=repository;
		this.id=id;
	}

	@Override
	protected Void doInBackground(Void... params) {
		result= new AsyncTaskResult();
		
		int i=1;
		while (i<5) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		
		
		switch (method) {
		case Create:
			result.bundle=repository.create(item);
			break;
		case Delete:
			if(item!=null)
				result.bundle=repository.delete(item);
			else
				result.bundle=repository.delete(id);
			break;
		case Read:
			result.bundle=repository.read(id);
			break;
		case ReadAll:
			result.bundle=repository.readAll();
			break;
		case Update:
			result.bundle=repository.update(item);
			break;
		}
		return null;
	}
	
	@Override
	public void onPostExecute(Void result){
		super.onPostExecute(result);
		if(listener!=null){
			listener.onTaskEnd();
		}
	}
	
	@Override
	public void onPreExecute(){
		super.onPreExecute();
		if(listener!=null){
			listener.onTaskStart();
		}
	}
	
	public void onProgressUpdate(Void... values){
		super.onProgressUpdate(values);
		if(listener!=null){
			listener.onTaskProgress();
		}
	}
	
	public class AsyncTaskResult{
		public Object bundle;
	}

}
