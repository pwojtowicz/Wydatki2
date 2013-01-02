package pl.wppiotrek85.wydatkibase.fragments.edit;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.adapters.SpinnerAdapter;
import pl.wppiotrek85.wydatkibase.adapters.SpinnerAdapter.SpinerHelper;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.entities.Parameter;
import pl.wppiotrek85.wydatkibase.entities.SpinnerObject;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryManagerMethods;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryTypes;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import pl.wppiotrek85.wydatkibase.support.ListSupport;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import pl.wppiotrek85.wydatkibase.units.ParameterTypes;
import pl.wppiotrek85.wydatkibase.units.ResultCodes;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class EditParameterFragment extends EditObjectBaseFragment<Parameter> {

	private EditText etbx_defaultValue;
	private EditText etbx_dataSource;
	private Spinner spn_type;
	private LinearLayout ll_dataSource;
	private LinearLayout ll_etbx_defaultFalue;
	private LinearLayout ll_cbx_defaultValue;

	private int parameterType;

	private CheckBox cbx_defaultValue;

	public EditParameterFragment(boolean shouldReload, Parameter item) {
		super(shouldReload, item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View convertView = inflater.inflate(R.layout.edit_parameter_layout,
				null);
		return convertView;
	}

	@Override
	protected void linkViews() {
		etbx_defaultValue = (EditText) getView().findViewById(
				R.id.edit_parameter_etbx_defaultValue);
		etbx_dataSource = (EditText) getView().findViewById(
				R.id.edit_parameter_etbx_dataSource);

		spn_type = (Spinner) getView().findViewById(
				R.id.edit_parameter_spn_type);
		cbx_defaultValue = (CheckBox) getView().findViewById(
				R.id.edit_parameter_cbx_defaultValue);

		ll_dataSource = (LinearLayout) getView().findViewById(
				R.id.edit_parameter_ll_dataSource);
		ll_etbx_defaultFalue = (LinearLayout) getView().findViewById(
				R.id.edit_parameter_ll_etbx_defaultValue);
		ll_cbx_defaultValue = (LinearLayout) getView().findViewById(
				R.id.edit_parameter_ll_cbx_defaultValue);
	}

	@Override
	protected void configureViews() {
		if (!isUpdate)
			currentObject = new Parameter();
		// else
		// currentObject = AndroidGlobals.getInstance()
		// .getCurrentSelectedParameter();

		getTypes();
		spn_type.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> adapterView, View view,
					int index, long id) {
				SpinerHelper oh = (SpinerHelper) view.getTag();
				if (oh.object.getId() > 0)
					onCategoryChange(oh.object);

			}

			public void onNothingSelected(AdapterView<?> adapterView) {
			}
		});

		if (isUpdate) {
			cbx_isActive.setChecked(currentObject.isActive());

			etbx_name.setText(currentObject.getName());

			etbx_dataSource.setText(currentObject.getDataSource());

			if (currentObject.getTypeId() == ParameterTypes.ParameterBoolean) {
				ll_cbx_defaultValue.setVisibility(LinearLayout.VISIBLE);
				ll_etbx_defaultFalue.setVisibility(LinearLayout.GONE);
				manageCheckBox(currentObject.getDefaultValue());
			} else {
				etbx_defaultValue.setText(currentObject.getDefaultValue());
			}
			if (currentObject.getTypeId() == ParameterTypes.ParameterList) {
				ll_dataSource.setVisibility(LinearLayout.VISIBLE);

			}

		}
	}

	protected void onCategoryChange(SpinnerObject object) {
		ll_dataSource.setVisibility(LinearLayout.GONE);
		ll_cbx_defaultValue.setVisibility(LinearLayout.GONE);
		ll_etbx_defaultFalue.setVisibility(LinearLayout.VISIBLE);
		this.parameterType = object.getId();
		switch (parameterType) {
		case ParameterTypes.ParameterList:
			ll_dataSource.setVisibility(LinearLayout.VISIBLE);
			break;
		case ParameterTypes.ParameterBoolean:
			ll_cbx_defaultValue.setVisibility(LinearLayout.VISIBLE);
			ll_etbx_defaultFalue.setVisibility(LinearLayout.GONE);
			break;
		case ParameterTypes.ParameterNumber:
			break;
		case ParameterTypes.ParameterText:
			break;

		default:
			break;
		}

	}

	private void manageCheckBox(String trueOrFalse) {
		if (trueOrFalse.equals("True"))
			cbx_defaultValue.setChecked(true);
		else if (trueOrFalse.equals("False"))
			cbx_defaultValue.setChecked(false);

	}

	private void getTypes() {
		ArrayList<SpinnerObject> items = new ArrayList<SpinnerObject>();

		for (int i = 1; i < 5; i++) {
			items.add(new SpinnerObject(i, ParameterTypes.getParameterName(i)));
		}

		SpinnerObject[] strArray = new SpinnerObject[items.size()];
		items.toArray(strArray);

		SpinnerAdapter adapter = new SpinnerAdapter(getActivity(),
				android.R.layout.simple_spinner_item, strArray);
		spn_type.setAdapter(adapter);
		if (isUpdate)
			spn_type.setSelection(currentObject.getTypeId() - 1);

	}

	@Override
	protected void prepareToSave() {
		boolean isValid = true;
		StringBuilder sb = new StringBuilder();

		String name = etbx_name.getText().toString().trim();

		if (isUpdate) {
			int parameterId = currentObject.getId();
			currentObject = new Parameter();
			currentObject.setId(parameterId);
		}

		if (name.length() > 0 && !ListSupport.isParameterNameUsed(name)) {
			currentObject.setName(name);
		} else {
			isValid = false;
			sb.append("\n" + getText(R.string.name));
		}
		if (parameterType == ParameterTypes.ParameterList)
			if (etbx_dataSource.getText().length() > 0) {
				currentObject.setDataSource(etbx_dataSource.getText()
						.toString());
				currentObject.setDefaultValue(etbx_defaultValue.getText()
						.toString());
			} else {
				isValid = false;
				sb.append("\n" + getText(R.string.parameter_datasource));
			}

		if (parameterType == ParameterTypes.ParameterBoolean)
			currentObject.setDefaultValue(cbx_defaultValue.isChecked() ? "True"
					: "False");
		else
			currentObject.setDefaultValue(etbx_defaultValue.getText()
					.toString());

		currentObject.setTypeId(parameterType);
		currentObject.setIsActive(cbx_isActive.isChecked());

		if (isValid) {
			saveObject();

		} else {
			System.out.println(sb.toString());
		}
	}

	@Override
	protected void saveObject() {
		ObjectManager manager = new ObjectManager(ERepositoryTypes.Parameters,
				this, ERepositoryManagerMethods.Create, currentObject);
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		if (response.bundle instanceof Parameter) {
			WydatkiGlobals.getInstance().updateParametersList(
					(Parameter) response.bundle);
		}
		leaveActivity(ResultCodes.RESULT_NEED_UPDATE);
	}

}
