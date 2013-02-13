package pl.wppiotrek85.wydatkibase.newfragments;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.adapters.BaseObjectAdapter;
import pl.wppiotrek85.wydatkibase.adapters.ProjectsAdapter;
import pl.wppiotrek85.wydatkibase.entities.Project;
import pl.wppiotrek85.wydatkibase.interfaces.IOnAdapterCheckboxClick;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import android.support.v4.app.FragmentActivity;

public class ProjectsListFragment extends BaseListFragment<Project> {

	public ProjectsListFragment() {
		super(R.layout.fragment_projects_list);
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
	protected void linkOtherView() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void configureView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterReloadAction() {
		// TODO Auto-generated method stub

	}

}
