package pl.wppiotrek85.wydatkibase.interfaces;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.exceptions.RepositoryException;

public interface IObjectRepository<T> {

	public int create(T item);

	public T update(T item);

	public T read(int id);

	public ArrayList<T> readAll();

	public boolean delete(T item) throws RepositoryException;

	public boolean delete(int id) throws RepositoryException;

}
