package pl.wppiotrek85.wydatkibase.interfaces;

public interface IObjectRepository<T> {

	public int create(T item);
	
	public T update(T item);
	
	public T read(int id);
	
	public T[]readAll();
	
	public boolean delete(T item);
	
	public boolean delete(int id);
	
}
