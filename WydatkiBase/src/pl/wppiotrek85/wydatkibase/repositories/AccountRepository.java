package pl.wppiotrek85.wydatkibase.repositories;

import pl.wppiotrek85.wydatkibase.entities.Account;
import pl.wppiotrek85.wydatkibase.interfaces.IObjectRepository;

public class AccountRepository implements IObjectRepository<Account> {

	@Override
	public int create(Account item) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Account update(Account item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account[] readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Account item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
