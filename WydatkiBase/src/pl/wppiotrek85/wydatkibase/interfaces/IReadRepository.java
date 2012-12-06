package pl.wppiotrek85.wydatkibase.interfaces;

import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;

public interface IReadRepository {

	public void onTaskStart();

	public void onTaskCancel();

	public void onTaskProgress();

	public void onTaskEnd();

	public void onTaskResponse(AsyncTaskResult response);

	public void onTaskInvalidResponse();

}
