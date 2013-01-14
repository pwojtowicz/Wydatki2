package pl.wppiotrek85.wydatkibase.repositoriesweb;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.entities.ItemsContainer;
import pl.wppiotrek85.wydatkibase.entities.Transaction;
import pl.wppiotrek85.wydatkibase.exceptions.RepositoryException;
import pl.wppiotrek85.wydatkibase.interfaces.IObjectRepositoryItemCointainer;
import pl.wppiotrek85.wydatkibase.providers.AbstractProvider;
import pl.wppiotrek85.wydatkibase.providers.CommunicationException;
import pl.wppiotrek85.wydatkibase.providers.Provider;

public class TransactionRepositoryWeb extends AbstractProvider implements
		IObjectRepositoryItemCointainer<Transaction> {

	@Override
	public int createAllFromList(ArrayList<Transaction> items) {
		int result = 0;
		for (Transaction transaction : items) {
			result = create(transaction);
			if (result == 0)
				break;
		}
		return (int) result;
	}

	@Override
	public int create(Transaction item) {
		String url = server + "/transactions/transaction";

		Provider<Transaction> provider = new Provider<Transaction>(
				Transaction.class);
		try {
			Transaction t = provider.sendObject(url, item, true, null);
			return t.getId();
		} catch (CommunicationException e) {

			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Transaction update(Transaction item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaction read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Transaction item) throws RepositoryException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) throws RepositoryException {
		String url = server + "/transactions/transaction/" + String.valueOf(id);

		Provider<Transaction> provider = new Provider<Transaction>(
				Transaction.class);
		try {
			provider.deleteObject(url, null);
			return true;
		} catch (CommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ItemsContainer<Transaction> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemsContainer<Transaction> readAll(int skip, int count,
			int accountId) {
		String url = server + "/transactions?account="
				+ String.valueOf(accountId) + "&from=" + String.valueOf(skip)
				+ "&to=" + String.valueOf(skip + count);

		Provider<Transaction> provider = new Provider<Transaction>(
				Transaction.class);
		try {
			return provider.getTransactions(0, 100, url, null);
		} catch (CommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
