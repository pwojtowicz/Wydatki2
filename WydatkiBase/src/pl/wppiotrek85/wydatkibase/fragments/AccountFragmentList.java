package pl.wppiotrek85.wydatkibase.fragments;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.adapters.AccountAdapter;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.entities.Account;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryManagerMethods;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryTypes;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class AccountFragmentList extends ObjectBaseFragment {

	private ListView objectListView;
	private ProgressDialog dialog;
	private ObjectManager manager;
	private AccountAdapter adapter;

	public AccountFragmentList() {
		super(false);
	}

	public AccountFragmentList(boolean shouldReload) {
		super(shouldReload);
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View convertView = inflater.inflate(R.layout.fragment_accounts_list,
				null);

		objectListView = (ListView) convertView.findViewById(R.id.listview);

		if (adapter != null)
			objectListView.setAdapter(adapter);

		return convertView;
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		if (response.bundle instanceof ArrayList<?>) {
			ArrayList<Account> list = (ArrayList<Account>) response.bundle;
			WydatkiGlobals.getInstance().setAccounts(list);
			refreshFragment(false);
		}

	}

	@Override
	public void refreshFragment(boolean forceRefresh) {
		if (!forceRefresh) {
			ArrayList<Account> list = WydatkiGlobals.getInstance()
					.getAccountsList();
			if (list == null)
				forceRefresh = true;
			else {
				if (adapter == null)
					adapter = new AccountAdapter(getActivity(), list, null);
				else
					adapter.reloadItems(list);
				objectListView.setAdapter(adapter);
			}
		}

		if (forceRefresh) {
			manager = new ObjectManager(ERepositoryTypes.Accounts, this,
					ERepositoryManagerMethods.ReadAll);
		}

	}

}
