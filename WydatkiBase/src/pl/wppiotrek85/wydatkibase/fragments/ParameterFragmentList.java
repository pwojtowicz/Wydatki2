package pl.wppiotrek85.wydatkibase.fragments;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.adapters.BaseObjectAdapter;
import pl.wppiotrek85.wydatkibase.adapters.ParametersAdapter;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.entities.Parameter;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryTypes;
import pl.wppiotrek85.wydatkibase.interfaces.IFragmentActions;
import pl.wppiotrek85.wydatkibase.interfaces.IOnAdapterCheckboxClick;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Toast;

public class ParameterFragmentList extends ObjectListBaseFragment<Parameter> {

	private boolean startFromCategory = false;

	public ParameterFragmentList() {
		super(R.layout.fragment_parameters_list, ERepositoryTypes.Parameters,
				false, false);
	}

	public ParameterFragmentList(boolean shouldReload,
			IFragmentActions actions, Boolean isChecakble, String selectedItems) {
		super(R.layout.fragment_parameters_list, ERepositoryTypes.Parameters,
				shouldReload, isChecakble);
		this.actions = actions;
		super.selectedItems = selectedItems;
	}

	public ParameterFragmentList(boolean shouldReload,
			IFragmentActions actions, Boolean isChecakble,
			String selectedItems, boolean startFromCategory) {
		super(R.layout.fragment_parameters_list, ERepositoryTypes.Parameters,
				shouldReload, isChecakble);
		this.actions = actions;
		super.selectedItems = selectedItems;
		this.startFromCategory = startFromCategory;
	}

	@Override
	public void linkViews(View convertView) {
		int size = 0;
		if (startFromCategory) {
			((ImageButton) convertView.findViewById(R.id.actionbar_btn_delete))
					.setVisibility(ImageButton.GONE);
			((ImageButton) convertView.findViewById(R.id.actionbar_btn_edit))
					.setVisibility(ImageButton.GONE);
			((ImageButton) convertView.findViewById(R.id.actionbar_btn_lock))
					.setVisibility(ImageButton.GONE);
			size = 1;
		}
		OnCheckBoxSelected(size);

		((ImageButton) convertView
				.findViewById(R.id.actionbar_btn_returnSelected))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (actions != null)
							actions.onReturnSelectedItemsIdClick();
					}
				});
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		if (response.bundle instanceof ArrayList<?>) {
			ArrayList<Parameter> list = (ArrayList<Parameter>) response.bundle;
			WydatkiGlobals.getInstance().setParameters(list);

			refreshFragment(false);
		}
		if (response.bundle instanceof Parameter) {
			OnFirtsShowFragment();
		}
	}

	@Override
	public void OnCheckBoxSelected(int size) {
		if (startFromCategory)
			size = 1;
		super.OnCheckBoxSelected(size);
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
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos,
			long arg3) {
		if (!startFromCategory) {
			actions.onUpdateObject(adapter.getItem(pos));
		}
		return true;
	}

	@Override
	public BaseObjectAdapter<Parameter> getAdapter(FragmentActivity activity,
			ArrayList<Parameter> list, IOnAdapterCheckboxClick object) {
		return new ParametersAdapter(activity, list, object, selectedItems);
	}

	@Override
	public ArrayList<Parameter> getObjectList() {
		return WydatkiGlobals.getInstance().getParametersList();
	}

}
