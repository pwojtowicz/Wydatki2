package pl.wppiotrek85.wydatkibase.fragments;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.adapters.BaseObjectAdapter;
import pl.wppiotrek85.wydatkibase.adapters.CategoriesAdapter;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.entities.Category;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryManagerMethods;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryTypes;
import pl.wppiotrek85.wydatkibase.interfaces.IFragmentActions;
import pl.wppiotrek85.wydatkibase.interfaces.IOnAdapterCheckboxClick;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;

public class CategoryFragmentList extends ObjectListBaseFragment<Category> {

	public CategoryFragmentList() {
		super(R.layout.fragment_categories_list, ERepositoryTypes.Categories,
				false, false);
	}

	public CategoryFragmentList(boolean shouldReload, IFragmentActions actions) {
		super(R.layout.fragment_categories_list, ERepositoryTypes.Categories,
				shouldReload, false);
		this.actions = actions;
		// TODO Auto-generated constructor stub
	}

	protected void onDeleteObject(Category item) {
		manager = new ObjectManager(ERepositoryTypes.Categories, this,
				ERepositoryManagerMethods.Delete, item);

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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}

	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos,
			long id) {
		actions.onUpdateObject(adapter.getItem(pos));
		return true;
	}

	@Override
	public BaseObjectAdapter<Category> getAdapter(FragmentActivity activity,
			ArrayList<Category> list, IOnAdapterCheckboxClick object) {
		return new CategoriesAdapter(activity, list, object);
	}

	@Override
	public ArrayList<Category> getObjectList() {
		return WydatkiGlobals.getInstance().getCategoriesList();
	}

	@Override
	public void linkViews(View convertView) {
		// TODO Auto-generated method stub

	}

}
