package pl.wppiotrek85.wydatkibase.fragments;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.adapters.BaseObjectAdapter;
import pl.wppiotrek85.wydatkibase.adapters.ProjectsAdapter;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.entities.Project;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryTypes;
import pl.wppiotrek85.wydatkibase.interfaces.IFragmentActions;
import pl.wppiotrek85.wydatkibase.interfaces.IOnAdapterCheckboxClick;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;

public class ProjectsFragmentList extends ObjectListBaseFragment<Project> {

	public ProjectsFragmentList() {
		super(R.layout.fragment_projects_list, ERepositoryTypes.Projects,
				false, false);
	}

	public ProjectsFragmentList(boolean shouldReload, IFragmentActions actions) {
		super(R.layout.fragment_projects_list, ERepositoryTypes.Projects,
				shouldReload, false);
		this.actions = actions;
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}

	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos,
			long id) {
		actions.onUpdateObject(adapter.getItem(pos));
		return true;
	}

	@Override
	public BaseObjectAdapter<Project> getAdapter(FragmentActivity activity,
			ArrayList<Project> list, IOnAdapterCheckboxClick object) {
		return new ProjectsAdapter(activity, list, object);
	}

	@Override
	public ArrayList<Project> getObjectList() {
		return WydatkiGlobals.getInstance().getProjectsList();
	}

	@Override
	public void linkViews(View convertView) {
		// TODO Auto-generated method stub

	}

}
