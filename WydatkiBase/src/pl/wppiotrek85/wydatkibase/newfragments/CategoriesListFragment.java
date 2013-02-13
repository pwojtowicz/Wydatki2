package pl.wppiotrek85.wydatkibase.newfragments;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.adapters.BaseObjectAdapter;
import pl.wppiotrek85.wydatkibase.adapters.CategoriesAdapter;
import pl.wppiotrek85.wydatkibase.entities.Category;
import pl.wppiotrek85.wydatkibase.interfaces.IOnAdapterCheckboxClick;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import android.support.v4.app.FragmentActivity;

public class CategoriesListFragment extends BaseListFragment<Category> {

	public CategoriesListFragment() {
		super(R.layout.fragment_categories_list);
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
