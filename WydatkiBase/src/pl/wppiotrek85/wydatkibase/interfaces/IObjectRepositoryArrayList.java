package pl.wppiotrek85.wydatkibase.interfaces;

import java.util.ArrayList;

public interface IObjectRepositoryArrayList<T> extends IObjectRepository<T> {

	public ArrayList<T> readAll();

}
