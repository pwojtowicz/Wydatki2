package pl.wppiotrek85.wydatkibase.interfaces;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.exceptions.RepositoryException;

public interface IObjectRepository<T> {

	public int createAllFromList(ArrayList<T> items);

	public int create(T item);

	public T update(T item);

	public T read(int id);

	public boolean delete(int id) throws RepositoryException;

	public boolean delete(ArrayList<Integer> ids) throws RepositoryException;

}
