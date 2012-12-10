package pl.wppiotrek85.wydatkibase.fragments;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.adapters.ParametersAdapter;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.entities.Parameter;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryManagerMethods;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryTypes;
import pl.wppiotrek85.wydatkibase.interfaces.IFragmentActions;
import pl.wppiotrek85.wydatkibase.interfaces.IOnAdapterCheckboxClick;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class ParameterFragmentList extends ObjectBaseFragment implements
		OnItemClickListener, IOnAdapterCheckboxClick {

	private ListView objectListView;
	private ProgressDialog dialog;
	private ObjectManager manager;
	private ParametersAdapter adapter;
	private LinearLayout actionBar;
	private IFragmentActions actions;

	public ParameterFragmentList() {
		super(false);
	}

	public ParameterFragmentList(boolean shouldReload, IFragmentActions actions) {
		super(shouldReload);
		this.actions = actions;
	}

	@Override
	public void OnFirtsShowFragment() {
		manager = new ObjectManager(ERepositoryTypes.Parameters, this,
				ERepositoryManagerMethods.ReadAll);
	}

	@Override
	public void OnFragmentActive() {
		// TODO Auto-generated method stub

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View convertView = inflater.inflate(R.layout.fragment_parameters_list,
				null);
		objectListView = (ListView) convertView.findViewById(R.id.listview);

		objectListView.setOnItemClickListener(this);

		actionBar = (LinearLayout) convertView
				.findViewById(R.id.bottom_actionbar);
		OnCheckBoxSelected(0);

		Button btn_add = (Button) convertView.findViewById(R.id.btn_add_new);
		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (actions != null) {
					actions.onAddBtnClick();
				}
			}
		});

		if (adapter != null)
			objectListView.setAdapter(adapter);
		return convertView;
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		if (response.bundle instanceof ArrayList<?>) {
			adapter = new ParametersAdapter(getActivity(),
					(ArrayList<Parameter>) response.bundle, this);

			objectListView.setAdapter(adapter);
		}
		if (response.bundle instanceof Parameter) {
			OnFirtsShowFragment();
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (adapter != null) {
			Parameter p = (Parameter) adapter.getItem(arg2);
			Toast toast = Toast.makeText(getActivity(), p.getName(),
					Toast.LENGTH_LONG);
			toast.show();
		}

	}

	@Override
	public void OnCheckBoxSelected(int size) {
		if (size > 0)
			actionBar.setVisibility(LinearLayout.VISIBLE);
		else
			actionBar.setVisibility(LinearLayout.GONE);
	}

}
