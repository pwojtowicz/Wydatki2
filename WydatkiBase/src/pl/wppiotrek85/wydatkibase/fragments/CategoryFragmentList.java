package pl.wppiotrek85.wydatkibase.fragments;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.adapters.CategoriesAdapter;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.entities.Category;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryManagerMethods;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryTypes;
import pl.wppiotrek85.wydatkibase.interfaces.IFragmentActions;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class CategoryFragmentList extends ObjectBaseFragment {

	private ListView objectListView;
	private ProgressDialog dialog;
	private ObjectManager manager;
	private CategoriesAdapter adapter;
	private IFragmentActions actions;

	public CategoryFragmentList() {
		super(false);
	}

	public CategoryFragmentList(boolean shouldReload, IFragmentActions actions) {
		super(shouldReload);
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
		View convertView = inflater.inflate(R.layout.fragment_categories_list,
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
			ArrayList<Category> list = (ArrayList<Category>) response.bundle;
			WydatkiGlobals.getInstance().setCategories(list);

			refreshFragment(false);
		}

	}

	@Override
	public void refreshFragment(boolean forceRefresh) {
		if (!forceRefresh) {
			ArrayList<Category> list = WydatkiGlobals.getInstance()
					.getCategoriesList();
			if (list == null)
				forceRefresh = true;
			else {
				if (adapter == null)
					adapter = new CategoriesAdapter(getActivity(), list, null);
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

}
