package pl.wppiotrek85.wydatkibase.interfaces;

public interface IHttpRequestToAsyncTaskCommunication {

	void onObjectsProgressUpdate(int progressPercent);

	boolean checkIsTaskCancled();

}
