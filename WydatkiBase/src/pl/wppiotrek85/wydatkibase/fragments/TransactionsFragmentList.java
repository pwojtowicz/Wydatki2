package pl.wppiotrek85.wydatkibase.fragments;

import java.util.ArrayList;
import java.util.Arrays;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.adapters.TransactionAdapter;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.entities.ItemsContainer;
import pl.wppiotrek85.wydatkibase.entities.Transaction;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryTypes;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TransactionsFragmentList extends ObjectBaseFragment {

	private ListView objectListView;
	private ObjectManager manager;
	private TransactionAdapter adapter;

	public TransactionsFragmentList(boolean shouldReload, Boolean isChecakble) {
		super(shouldReload, isChecakble);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View convertView = inflater.inflate(
				R.layout.fragment_transactions_list, null);

		objectListView = (ListView) convertView.findViewById(R.id.listview);
		return convertView;
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		if (response.bundle instanceof ItemsContainer) {
			ItemsContainer<Transaction> containt = (ItemsContainer<Transaction>) response.bundle;

			WydatkiGlobals.getInstance().setTransactionsContainer(containt);
			refreshFragment(false);
		}
	}

	@Override
	public void OnFirtsShowFragment() {
		refreshFragment(true);
	}

	@Override
	public void OnFragmentActive() {
		// TODO Auto-generated method stub
	}

	@Override
	public void refreshFragment(boolean forceRefresh) {
		if (!forceRefresh) {
			ItemsContainer<Transaction> container = WydatkiGlobals
					.getInstance().getTransactionsContainer();
			ArrayList<Transaction> list = new ArrayList<Transaction>(
					Arrays.asList(container.getItems()));
			if (list == null)
				forceRefresh = true;
			else {
				adapter = new TransactionAdapter(getActivity(), list,
						container.hasMore());
				objectListView.setAdapter(adapter);

			}
		}

		if (forceRefresh) {
			manager = new ObjectManager(ERepositoryTypes.Transactions, this, 0,
					2);
		}
	}

	@Override
	public ArrayList<Integer> getSelectedItemsList() {
		// TODO Auto-generated method stub
		return null;
	}
}
