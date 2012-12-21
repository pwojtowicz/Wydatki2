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
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ParameterFragmentList extends ObjectBaseFragment implements
		OnItemClickListener, IOnAdapterCheckboxClick {

	private ListView objectListView;
	private ProgressDialog dialog;
	private ObjectManager manager;
	private ParametersAdapter adapter;
	private RelativeLayout actionBar;
	private IFragmentActions actions;
	private String selectedItems = "";
	private boolean startFromCategory = false;

	public ParameterFragmentList() {
		super(false, false);
	}

	public ParameterFragmentList(boolean shouldReload,
			IFragmentActions actions, Boolean isChecakble, String selectedItems) {
		super(shouldReload, isChecakble);
		this.actions = actions;
		this.selectedItems = selectedItems;
	}

	public ParameterFragmentList(boolean shouldReload,
			IFragmentActions actions, Boolean isChecakble,
			String selectedItems, boolean startFromCategory) {
		super(shouldReload, isChecakble);
		this.actions = actions;
		this.selectedItems = selectedItems;
		this.startFromCategory = startFromCategory;
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
		View convertView = inflater.inflate(R.layout.fragment_parameters_list,
				null);
		objectListView = (ListView) convertView.findViewById(R.id.listview);

		objectListView.setOnItemClickListener(this);

		actionBar = (RelativeLayout) convertView
				.findViewById(R.id.bottom_actionbar);

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

		Button btn_add = (Button) convertView.findViewById(R.id.btn_add_new);
		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (actions != null) {
					actions.onAddBtnClick();
				}
			}
		});

		((ImageButton) convertView
				.findViewById(R.id.actionbar_btn_returnSelected))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (actions != null)
							actions.onReturnSelectedItemsIdClick();
					}
				});

		if (adapter != null)
			objectListView.setAdapter(adapter);
		return convertView;
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
		if (startFromCategory)
			size = 1;
		if (size > 0)
			actionBar.setVisibility(LinearLayout.VISIBLE);
		else
			actionBar.setVisibility(LinearLayout.GONE);
	}

	@Override
	public void refreshFragment(boolean forceRefresh) {
		if (!forceRefresh) {
			ArrayList<Parameter> list = WydatkiGlobals.getInstance()
					.getParametersList();
			if (list == null)
				forceRefresh = true;
			else {
				if (adapter == null)
					if (super.isChecakble)
						adapter = new ParametersAdapter(getActivity(), list,
								this, selectedItems);
					else
						adapter = new ParametersAdapter(getActivity(), list,
								null);
				else
					adapter.reloadItems(list);
				objectListView.setAdapter(adapter);
			}
		}

		if (forceRefresh) {
			manager = new ObjectManager(ERepositoryTypes.Parameters, this,
					ERepositoryManagerMethods.ReadAll);
		}

	}

	@Override
	public ArrayList<Integer> getSelectedItemsList() {
		if (adapter != null) {
			return adapter.getSelectedItemsList();
		}
		return null;
	}

}
