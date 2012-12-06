package pl.wppiotrek85.wydatkibase.exceptions;

import pl.wppiotrek85.wydatkibase.enums.ERepositoryException;

public class RepositoryException extends Exception {

	private static final long serialVersionUID = 1L;
	private ERepositoryException type;
	private Exception ex;

	public RepositoryException(ERepositoryException type, Exception ex) {
		this.type = type;
		this.ex = ex;
	}
}
