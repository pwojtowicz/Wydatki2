package pl.wppiotrek85.wydatkibase.interfaces;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.entities.ItemsContainer;
import pl.wppiotrek85.wydatkibase.entities.ModelBase;
import pl.wppiotrek85.wydatkibase.exceptions.RepositoryException;

public interface IObjectRepository<T> {

	public int createAllFromList(ItemsContainer<ModelBase> items);

	public int create(T item);

	public T update(T item);

	public T read(int id);

	public boolean delete(int id) throws RepositoryException;

	public boolean delete(ArrayList<ModelBase> ids) throws RepositoryException;

}
