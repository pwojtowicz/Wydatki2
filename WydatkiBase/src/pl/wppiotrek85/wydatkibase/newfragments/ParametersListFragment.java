package pl.wppiotrek85.wydatkibase.newfragments;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.adapters.BaseObjectAdapter;
import pl.wppiotrek85.wydatkibase.adapters.ParametersAdapter;
import pl.wppiotrek85.wydatkibase.entities.Parameter;
import pl.wppiotrek85.wydatkibase.interfaces.IOnAdapterCheckboxClick;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import android.support.v4.app.FragmentActivity;

public class ParametersListFragment extends BaseListFragment<Parameter> {

	public ParametersListFragment() {
		super(R.layout.fragment_parameters_list);
	}

	@Override
	public BaseObjectAdapter<Parameter> getAdapter(FragmentActivity activity,
			ArrayList<Parameter> list, IOnAdapterCheckboxClick object) {
		// if (startFromCategory)
		// object = this;
		return new ParametersAdapter(activity, list, object, null);
	}

	@Override
	public ArrayList<Parameter> getObjectList() {
		return WydatkiGlobals.getInstance().getParametersList();
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
