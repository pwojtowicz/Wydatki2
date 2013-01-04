package pl.wppiotrek85.wydatkibase.interfaces;

import pl.wppiotrek85.wydatkibase.entities.ItemsContainer;

public interface IObjectRepositoryItemCointainer<T> extends
		IObjectRepository<T> {

	public ItemsContainer<T> readAll();

	public ItemsContainer<T> readAll(int skip, int count, int accountId);

}
