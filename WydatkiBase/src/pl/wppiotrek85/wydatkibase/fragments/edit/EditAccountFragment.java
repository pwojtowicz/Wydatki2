package pl.wppiotrek85.wydatkibase.fragments.edit;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.fragments.ObjectBaseFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EditAccountFragment extends ObjectBaseFragment {

	public EditAccountFragment(boolean shouldReload) {
		super(shouldReload);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View convertView = inflater.inflate(R.layout.edit_account_layout, null);
		return convertView;
	}

	@Override
	public void refreshFragment(boolean forceRefresh) {

	}

}
