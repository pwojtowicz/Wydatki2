package pl.wppiotrek85.wydatkibase.fragments;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.adapters.ProjectsAdapter;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.entities.Project;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryManagerMethods;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryTypes;
import pl.wppiotrek85.wydatkibase.interfaces.IFragmentActions;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import pl.wppiotrek85.wydatkibase.units.ResultCodes;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class ProjectsFragmentList extends ObjectBaseFragment {

	private ListView objectListView;
	private ProgressDialog dialog;
	private ObjectManager manager;
	private ProjectsAdapter adapter;
	private IFragmentActions actions;

	public ProjectsFragmentList() {
		super(false, false);
	}

	public ProjectsFragmentList(boolean shouldReload, IFragmentActions actions) {
		super(shouldReload, false);
		this.actions = actions;
		// TODO Auto-generated constructor stub
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
		View convertView = inflater.inflate(R.layout.fragment_projects_list,
				null);
		objectListView = (ListView) convertView.findViewById(R.id.listview);

		if (adapter != null)
			objectListView.setAdapter(adapter);

		Button btn_add = (Button) convertView.findViewById(R.id.btn_add_new);
		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (actions != null) {
					actions.onAddBtnClick();
				}
			}
		});

		return convertView;
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		if (response.bundle instanceof ArrayList<?>) {
			ArrayList<Project> list = (ArrayList<Project>) response.bundle;
			WydatkiGlobals.getInstance().setProjects(list);

			refreshFragment(false);
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ResultCodes.START_ACTIVITY_EDIT_PROJECT) {
			if (resultCode == ResultCodes.RESULT_NEED_UPDATE) {
				refreshFragment(false);
			}
		}
	}

	@Override
	public void refreshFragment(boolean forceRefresh) {
		if (!forceRefresh) {
			ArrayList<Project> list = WydatkiGlobals.getInstance()
					.getProjectsList();
			if (list == null)
				forceRefresh = true;
			else {
				if (adapter == null)
					adapter = new ProjectsAdapter(getActivity(), list, null);
				else
					adapter.reloadItems(list);
				objectListView.setAdapter(adapter);
			}
		}

		if (forceRefresh) {
			manager = new ObjectManager(ERepositoryTypes.Projects, this,
					ERepositoryManagerMethods.ReadAll);
		}

	}

	@Override
	public ArrayList<Integer> getSelectedItemsList() {
		return null;
	}

}
