package pl.wppiotrek85.wydatkibase.fragments;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TransactionFragment extends ObjectBaseFragment {

	public static final String BUNDLE_IS_NEW_TRANSACTION = "newTransaction";
	public static final String BUNDLE_IS_NEW_TRANSFER = "newTransfer";

	public TransactionFragment(boolean shouldReload) {
		super(shouldReload);
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnFirtsShowFragment() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnFragmentActive() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshFragment(boolean forceRefresh) {
		// TODO Auto-generated method stub

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View convertView = inflater.inflate(
				R.layout.fragment_transaction_layout, null);

		return convertView;
	}

}
