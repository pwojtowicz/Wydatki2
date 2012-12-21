package pl.wppiotrek85.wydatkibase.fragments.edit;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.adapters.CategoryParametersAdapter;
import pl.wppiotrek85.wydatkibase.adapters.SpinnerAdapter;
import pl.wppiotrek85.wydatkibase.adapters.SpinnerAdapter.SpinerHelper;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.entities.Category;
import pl.wppiotrek85.wydatkibase.entities.Parameter;
import pl.wppiotrek85.wydatkibase.entities.SpinnerObject;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryManagerMethods;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryTypes;
import pl.wppiotrek85.wydatkibase.interfaces.IEditCategoryActions;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import pl.wppiotrek85.wydatkibase.support.ListSupport;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import pl.wppiotrek85.wydatkibase.units.ResultCodes;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class EditCategoryFragment extends EditObjectBaseFragment<Category> {

	private ListView listView;
	private ToggleButton tbn_isPositive;
	private Spinner spn_parent;
	private CategoryParametersAdapter adapter;
	private IEditCategoryActions listener;

	public static final String BUNDLE_SELECTED_PARAMETERS = "selectedParameters";

	public EditCategoryFragment(boolean shouldReload, Category item,
			IEditCategoryActions listener) {
		super(shouldReload, item);
		this.listener = listener;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View convertView = inflater
				.inflate(R.layout.edit_category_layout, null);
		setListHeader(convertView);
		return convertView;
	}

	private void setListHeader(View convertView) {
		LayoutInflater layoutInflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View header = layoutInflater.inflate(R.layout.edit_category_header,
				null);

		listView = (ListView) convertView
				.findViewById(R.id.edit_category_lv_parameters);

		etbx_name = (EditText) header.findViewById(R.id.edit_etbx_name);
		cbx_isActive = (CheckBox) header.findViewById(R.id.edit_cbx_isActive);

		Button btn_addParameters = (Button) header
				.findViewById(R.id.edit_category_btn_add_parameters);
		btn_addParameters.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (listener != null)
					listener.showParametersForCategory(currentObject);
			}
		});

		tbn_isPositive = (ToggleButton) header
				.findViewById(R.id.edit_category_tbn_ispositive);
		spn_parent = (Spinner) header
				.findViewById(R.id.edit_category_spn_parent);

		spn_parent.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> adapterView, View view,
					int index, long id) {
				SpinerHelper oh = (SpinerHelper) view.getTag();
				if (oh.object.getId() >= 0)
					onCategoryChange(oh.object.getId());

			}

			public void onNothingSelected(AdapterView<?> adapterView) {
			}
		});
		listView.addHeaderView(header);
	}

	@Override
	protected void linkViews() {

	}

	@Override
	protected void configureViews() {
		adapter = new CategoryParametersAdapter(getActivity());

		if (!isUpdate && currentObject == null) {
			currentObject = new Category();
		}
		listView.setAdapter(adapter);

		refreshFragment(false);
		if (isUpdate) {
			etbx_name.setText(currentObject.getName());
			cbx_isActive.setChecked(currentObject.isActive());
			tbn_isPositive.setChecked(currentObject.isPositive());
		}
	}

	private void getCategories() {
		WydatkiGlobals globals = WydatkiGlobals.getInstance();
		ArrayList<SpinnerObject> items = new ArrayList<SpinnerObject>();
		items.add(new SpinnerObject(0, getText(R.string.no_selected_value)
				.toString()));

		ArrayList<Category> categories = globals.getCategoriesList();
		if (categories != null) {
			for (Category item : categories) {
				if (item.isActive() && item.getParentId() > 0)
					items.add(new SpinnerObject(item.getId(), "- "
							+ item.getName()));
				else if (item.isActive() && item.getParentId() <= 0)
					items.add(new SpinnerObject(item.getId(), item.getName()));
			}

			SpinnerObject[] strArray = new SpinnerObject[items.size()];
			items.toArray(strArray);

			SpinnerAdapter adapter = new SpinnerAdapter(getActivity(),
					android.R.layout.simple_spinner_item, strArray);
			spn_parent.setAdapter(adapter);
			if (isUpdate && currentObject.getParentId() != 0)
				spn_parent.setSelection(getSelectedIndexById(
						currentObject.getParentId(), items));
		}
	}

	private int getSelectedIndexById(int id, ArrayList<SpinnerObject> items) {
		for (int i = 0; i < items.size(); i++) {
			if (((SpinnerObject) items.get(i)).getId() == id)
				return i;
		}
		return 0;
	}

	protected void onCategoryChange(int objectId) {
		currentObject.setParentId(objectId);
		adapter.clear();
		WydatkiGlobals globals = WydatkiGlobals.getInstance();

		if (currentObject.getAttributes() != null
				&& currentObject.getAttributes().length > 0) {
			adapter.addParameter("Parametry kategorii");
			for (Parameter item : currentObject.getAttributes()) {
				adapter.addParameter(item);
			}
		}
		if (objectId > 0) {
			adapter.addParameter("Parametry nadrz«dne");
			addParameterForCategoryId(objectId, globals);
		}
		adapter.refresh();
	}

	private void addParameterForCategoryId(int categoryId,
			WydatkiGlobals globals) {
		Category item = globals.getCategoryById(categoryId);
		if (item != null) {

			if (item.getParentId() > 0 && item.getId() != item.getParentId())
				addParameterForCategoryId(item.getParentId(), globals);
			for (Parameter parameter : item.getAttributes()) {
				adapter.addParameter(parameter);
			}
		}
	}

	@Override
	public void refreshFragment(boolean forceRefresh) {
		if (forceRefresh) {

		}
		getCategories();
	}

	@Override
	protected void prepareToSave() {
		boolean isValid = false;
		String name = etbx_name.getText().toString().trim();
		if (isUpdate
				|| (name.length() > 0 && !ListSupport.isCategoryNameUsed(name))) {
			isValid = true;
		}

		if (isValid) {
			Category category = new Category();

			if (isUpdate) {
				int categoryId = currentObject.getId();
				category.setId(categoryId);
			}
			category.setParentId(currentObject.getParentId());
			category.setName(name);
			category.setIsActive(cbx_isActive.isChecked());
			category.setIsPositive(tbn_isPositive.isChecked());

			category.setAttributes(currentObject.getAttributes());
			this.currentObject = category;
			saveObject();
		}
	}

	@Override
	protected void saveObject() {
		if (isUpdate)
			new ObjectManager(ERepositoryTypes.Categories, this,
					ERepositoryManagerMethods.Update, currentObject);
		else
			new ObjectManager(ERepositoryTypes.Categories, this,
					ERepositoryManagerMethods.Create, currentObject);
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		leaveActivity(ResultCodes.RESULT_NEED_UPDATE);
	}

	public void setParameters(Parameter[] parameters) {
		if (parameters != null) {
			if (currentObject != null)
				currentObject.setAttributes(parameters);
			refreshFragment(false);
		}

	}

}
