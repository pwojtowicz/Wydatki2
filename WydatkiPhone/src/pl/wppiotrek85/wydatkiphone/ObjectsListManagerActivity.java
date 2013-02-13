package pl.wppiotrek85.wydatkiphone;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.activities.FragmentBaseActivity;
import pl.wppiotrek85.wydatkibase.adapters.SimpleFragmentAdapter;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.entities.Category;
import pl.wppiotrek85.wydatkibase.entities.FragmentInfo;
import pl.wppiotrek85.wydatkibase.entities.Parameter;
import pl.wppiotrek85.wydatkibase.entities.Project;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryManagerMethods;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryTypes;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import pl.wppiotrek85.wydatkibase.newfragments.AccountListFragment;
import pl.wppiotrek85.wydatkibase.newfragments.CategoriesListFragment;
import pl.wppiotrek85.wydatkibase.newfragments.ParametersListFragment;
import pl.wppiotrek85.wydatkibase.newfragments.ProjectsListFragment;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class ObjectsListManagerActivity extends FragmentBaseActivity {

	private ViewPager mViewPager;
	private ArrayList<FragmentInfo> fragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_objects_list_manager);
		createFragments();
	}

	private void createFragments() {
		fragments = new ArrayList<FragmentInfo>();

		Bundle b = new Bundle();
		b.putBoolean(AccountListFragment.BUNDLE_SHOW_AS_LIST, true);
		AccountListFragment accounts = new AccountListFragment();
		accounts.setArguments(b);

		fragments.add(new FragmentInfo(accounts, "Konta"));

		fragments.add(new FragmentInfo(new CategoriesListFragment(),
				"Kategorie"));

		fragments.add(new FragmentInfo(new ParametersListFragment(),
				"Parametry"));

		fragments.add(new FragmentInfo(new ProjectsListFragment(), "Projekty"));

		SimpleFragmentAdapter fAdapter = new SimpleFragmentAdapter(
				getSupportFragmentManager(), fragments);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(fAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_objects_list_manager, menu);
		return true;
	}

	@Override
	public void onResume() {
		super.onResume();
		checkIsAllListEnabled();
	}

	private void checkIsAllListEnabled() {
		boolean hasAccounts = WydatkiGlobals.getInstance().getAccountsList() != null;
		boolean hasCategories = WydatkiGlobals.getInstance()
				.getCategoriesList() != null;
		boolean hasParameters = WydatkiGlobals.getInstance()
				.getParametersList() != null;
		boolean hasProjects = WydatkiGlobals.getInstance().getProjectsList() != null;

		if (!hasAccounts) {
			new ObjectManager(ERepositoryTypes.Accounts, this,
					ERepositoryManagerMethods.ReadAll);
		} else if (!hasCategories) {
			new ObjectManager(ERepositoryTypes.Categories, this,
					ERepositoryManagerMethods.ReadAll);
		} else if (!hasParameters) {
			new ObjectManager(ERepositoryTypes.Parameters, this,
					ERepositoryManagerMethods.ReadAll);
		} else if (!hasProjects) {
			new ObjectManager(ERepositoryTypes.Projects, this,
					ERepositoryManagerMethods.ReadAll);
		} else {
			createFragments();
		}
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		if (response.bundle instanceof ArrayList<?>) {
			Object o = ((ArrayList<Object>) response.bundle).get(0);
			if (o instanceof Category) {
				ArrayList<Category> list = (ArrayList<Category>) response.bundle;
				WydatkiGlobals.getInstance().setCategories(list);
			} else if (o instanceof Parameter) {
				ArrayList<Parameter> list = (ArrayList<Parameter>) response.bundle;
				WydatkiGlobals.getInstance().setParameters(list);
			} else if (o instanceof Project) {
				ArrayList<Project> list = (ArrayList<Project>) response.bundle;
				WydatkiGlobals.getInstance().setProjects(list);
			}
			checkIsAllListEnabled();
		}

	}

}
