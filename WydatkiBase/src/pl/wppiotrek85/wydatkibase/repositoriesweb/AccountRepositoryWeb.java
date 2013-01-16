package pl.wppiotrek85.wydatkibase.repositoriesweb;

import java.util.ArrayList;
import java.util.Arrays;

import pl.wppiotrek85.wydatkibase.entities.Account;
import pl.wppiotrek85.wydatkibase.exceptions.RepositoryException;
import pl.wppiotrek85.wydatkibase.interfaces.IObjectRepositoryArrayList;
import pl.wppiotrek85.wydatkibase.providers.AbstractProvider;
import pl.wppiotrek85.wydatkibase.providers.CommunicationException;
import pl.wppiotrek85.wydatkibase.providers.Provider;

public class AccountRepositoryWeb extends AbstractProvider implements
		IObjectRepositoryArrayList<Account> {

	@Override
	public int createAllFromList(ArrayList<Account> items) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int create(Account item) {
		try {
			String url = server + "/accounts/account";
			Provider<Account> provider = new Provider<Account>(Account.class);
			Account a = provider.sendObject(url, item, true, null);
			return a.getId();
		} catch (CommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public boolean delete(int id) throws RepositoryException {
		try {
			String url = server + "/accounts/account/" + String.valueOf(id);
			Provider<Account> provider = new Provider<Account>(Account.class);
			provider.deleteObject(url, null);
			return true;
		} catch (CommunicationException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ArrayList<Account> readAll() {
		try {
			String url = server + "/accounts";

			Provider<Account[]> provider = new Provider<Account[]>(
					Account[].class);
			Account[] objects = provider.getObjects(url, null);

			return new ArrayList<Account>(Arrays.asList(objects));
		} catch (CommunicationException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean delete(ArrayList<Integer> ids) throws RepositoryException {
		// TODO Auto-generated method stub
		return false;
	}

}
