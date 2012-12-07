package pl.wppiotrek85.wydatkibase.fragments;

import java.util.ArrayList;

import pl.billennium.fragmenthelper.BaseFragment;
import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.adapters.AccountAdapter;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.entities.Account;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryManagerMethods;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryTypes;
import pl.wppiotrek85.wydatkibase.exceptions.RepositoryException;
import pl.wppiotrek85.wydatkibase.interfaces.IReadRepository;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class AccountFragmentList extends BaseFragment implements
		IReadRepository {

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
		manager = new ObjectManager(ERepositoryTypes.Accounts, this,
				ERepositoryManagerMethods.ReadAll);
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
	public void onTaskStart() {
		System.out.println("onTaskStart");
		dialog = new ProgressDialog(getActivity());
		dialog.setMessage("Pobieranie");
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	public void onTaskCancel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTaskProgress() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTaskEnd() {
		System.out.println("onTaskEnd");
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		if (response.bundle instanceof ArrayList<?>) {
			adapter = new AccountAdapter(getActivity(),
					(ArrayList<Account>) response.bundle);

			objectListView.setAdapter(adapter);
		}

	}

	@Override
	public void onTaskInvalidResponse(RepositoryException exception) {
		// TODO Auto-generated method stub

	}

}
