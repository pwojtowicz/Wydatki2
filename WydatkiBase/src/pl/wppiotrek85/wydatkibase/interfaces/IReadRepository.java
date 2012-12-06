package pl.wppiotrek85.wydatkibase.interfaces;

public interface IReadRepository {
	
	public void onTaskStart();
	
	public void onTaskCancel();
	
	public void onTaskProgress();
	
	public void onTaskEnd();
	
	public void onTaskResponse();
	
	public void onTaskInvalidResponse();

}
