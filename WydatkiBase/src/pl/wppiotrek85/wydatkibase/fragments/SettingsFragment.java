package pl.wppiotrek85.wydatkibase.fragments;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsFragment extends ObjectBaseFragment {

	public SettingsFragment(boolean shouldReload) {
		super(shouldReload, false);
		// TODO Auto-generated constructor stub
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
		View convertView = inflater.inflate(R.layout.settings_fragment_layout,
				null);

		return convertView;
	}

	@Override
	public ArrayList<Integer> getSelectedItemsList() {
		return null;
	}

	@Override
	public void changeEditListState() {
		// TODO Auto-generated method stub

	}

}
