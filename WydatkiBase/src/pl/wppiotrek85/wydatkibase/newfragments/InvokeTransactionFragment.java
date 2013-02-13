package pl.wppiotrek85.wydatkibase.newfragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.activities.CalculatorInput;
import pl.wppiotrek85.wydatkibase.adapters.InvokeTransactionAdapter;
import pl.wppiotrek85.wydatkibase.adapters.SpinnerAdapter;
import pl.wppiotrek85.wydatkibase.adapters.SpinnerAdapter.SpinerHelper;
import pl.wppiotrek85.wydatkibase.entities.Account;
import pl.wppiotrek85.wydatkibase.entities.Category;
import pl.wppiotrek85.wydatkibase.entities.InvokeTransactionParameter;
import pl.wppiotrek85.wydatkibase.entities.Parameter;
import pl.wppiotrek85.wydatkibase.entities.Project;
import pl.wppiotrek85.wydatkibase.entities.SpinnerObject;
import pl.wppiotrek85.wydatkibase.entities.Transaction;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import pl.wppiotrek85.wydatkibase.units.UnitConverter;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class InvokeTransactionFragment extends BaseFragment implements
		OnClickListener {

	public static final String BUNDLE_IS_NEW_TRANSFER = "newTransfer";
	public static String BUNDLE_IS_NEW_TRANSACTION = "transaction";

	private static final int TIME_DIALOG_ID = 0;
	private static final int DATE_DIALOG_ID = 1;

	private static final int CalculatorInput_REQUESTCODE = 123;

	private ListView list;
	private LinearLayout ll_accountTo;
	private LinearLayout ll_category;
	private LinearLayout ll_project;
	private ToggleButton isPositive;
	private TextView additionParametersTextView;
	private Spinner accountFrom;
	private Spinner accountTo;
	private Spinner category;
	private Spinner spn_project;
	private EditText note;
	private EditText value;
	private Button btn_time;
	private Button btn_date;
	private boolean isTransfer;

	private int Year;
	private int month;
	private int day;
	private int hour;
	private int minute;

	private InvokeTransactionAdapter adapter;
	private boolean hasAdditionalParameters;

	private Transaction currentTransaction = new Transaction();
	private Transactionhelper helper = new Transactionhelper();

	public InvokeTransactionFragment() {
		super(R.layout.fragment_transaction_layout);
	}

	@Override
	protected void linkOtherView() {
		Bundle extras = getArguments();
		if (extras != null) {
			isTransfer = extras.getBoolean(BUNDLE_IS_NEW_TRANSFER, false);
		}

		View view = getCurrentView();

		list = (ListView) view.findViewById(R.id.invoke_transactions_listview);

		LayoutInflater layoutInflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View header = layoutInflater.inflate(
				R.layout.invoke_transaction_header, null);

		View footer = layoutInflater.inflate(
				R.layout.invoke_transaction_footer, null);

		ll_accountTo = (LinearLayout) header
				.findViewById(R.id.invoke_transaction_header_ll_account_to);

		ll_category = (LinearLayout) header
				.findViewById(R.id.invoke_transaction_header_ll_category);

		ll_project = (LinearLayout) footer
				.findViewById(R.id.invoke_transaction_footer_ll_project);

		isPositive = (ToggleButton) header
				.findViewById(R.id.invoke_transaction_header_tbn_ispositive);

		additionParametersTextView = (TextView) header
				.findViewById(R.id.invoke_transaction_header_addition_parameters);

		accountFrom = (Spinner) header
				.findViewById(R.id.invoke_transaction_header_spinner_account_from);
		accountTo = (Spinner) header
				.findViewById(R.id.invoke_transaction_header_spinner_account_to);
		category = (Spinner) header
				.findViewById(R.id.invoke_transaction_header_spinner_category);

		spn_project = (Spinner) footer
				.findViewById(R.id.invoke_transaction_footer_spinner_project);

		note = (EditText) header
				.findViewById(R.id.invoke_transaction_header_etbx_note);
		value = (EditText) header
				.findViewById(R.id.invoke_transaction_header_etbx_value);

		btn_time = (Button) header
				.findViewById(R.id.invoke_transaction_header_btn_time);

		btn_date = (Button) header
				.findViewById(R.id.invoke_transaction_header_btn_date);

		btn_time.setOnClickListener(this);
		btn_date.setOnClickListener(this);

		list.addHeaderView(header);
		list.addFooterView(footer);

		list.setClickable(true);
		list.setItemsCanFocus(true);

		Button btn_calculate = (Button) header
				.findViewById(R.id.invoke_transaction_header_btn_calculator);
		btn_calculate.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				showCalculatorInput();
			}
		});
	}

	private void showCalculatorInput() {
		Intent intent = new Intent(getActivity(), CalculatorInput.class);
		intent.putExtra(CalculatorInput.EXTRA_AMOUNT, value.getText()
				.toString());
		startActivityForResult(intent, CalculatorInput_REQUESTCODE);
	}

	@Override
	protected void configureView() {
		if (isTransfer) {
			ll_accountTo.setVisibility(LinearLayout.VISIBLE);
			ll_category.setVisibility(LinearLayout.GONE);
			ll_project.setVisibility(LinearLayout.GONE);
			isPositive.setVisibility(ToggleButton.GONE);
			additionParametersTextView.setVisibility(TextView.GONE);
		} else {
			ll_accountTo.setVisibility(LinearLayout.GONE);
			ll_category.setVisibility(LinearLayout.VISIBLE);
			ll_project.setVisibility(LinearLayout.VISIBLE);
			isPositive.setVisibility(ToggleButton.VISIBLE);
		}
		// refreshActivity();

		if (!isTransfer) {
			category.setOnItemSelectedListener(new OnItemSelectedListener() {

				public void onItemSelected(AdapterView<?> adapterView,
						View view, int index, long id) {
					SpinerHelper oh = (SpinerHelper) view.getTag();
					onCategoryChange(oh.object);
				}

				public void onNothingSelected(AdapterView<?> adapterView) {
				}
			});
		}

	}

	protected void onCategoryChange(SpinnerObject object) {
		if (!isTransfer) {
			WydatkiGlobals globals = WydatkiGlobals.getInstance();
			// ArrayList<Category> cat = globals.getCategoriesList();
			ArrayList<InvokeTransactionParameter> parameters = new ArrayList<InvokeTransactionParameter>();
			// if (cat != null) {

			addParameterForCategoryId(object.getId(), parameters);

			// }
			if (parameters.size() > 0) {
				additionParametersTextView.setVisibility(TextView.VISIBLE);
				hasAdditionalParameters = true;
			} else {
				additionParametersTextView.setVisibility(TextView.GONE);
				hasAdditionalParameters = false;
			}
			Category c = globals.getCategoryById(object.getId());
			if (c != null) {
				isPositive.setChecked(c.isPositive());
			}
			if (adapter == null)
				adapter = new InvokeTransactionAdapter(getActivity(),
						parameters);
			else
				adapter.setParamaters(parameters);

			list.setAdapter(adapter);

		}
	}

	private void addParameterForCategoryId(int categoryId,
			ArrayList<InvokeTransactionParameter> parameters) {
		WydatkiGlobals globals = WydatkiGlobals.getInstance();
		Category item = globals.getCategoryById(categoryId);
		if (item != null) {
			if (item.getId() == categoryId) {

				if (item.getParentId() > 0)
					addParameterForCategoryId(item.getParentId(), parameters);
				for (Parameter parameter : item.getAttributes()) {
					parameter = globals.getParameterById(parameter.getId());

					InvokeTransactionParameter param = new InvokeTransactionParameter(
							parameter.getId(), parameter.getName(),
							parameter.getTypeId(), parameter.getDefaultValue(),
							parameter.getDataSource());
					parameters.add(param);

				}
			}
		}
	}

	@Override
	public void reload(boolean forceReload) {
		if (adapter == null)
			adapter = new InvokeTransactionAdapter(getActivity(), null);

		list.setAdapter(adapter);

		if (accountFrom != null)
			getAccounts(accountFrom);

		if (isTransfer && accountTo != null)
			getAccounts(accountTo);

		if (category != null)
			getCategories();

		if (spn_project != null)
			getProjects();

		// value.setOnFocusChangeListener(new OnFocusChangeListener() {
		//
		// @Override
		// public void onFocusChange(View v, boolean hasFocus) {
		// if (listener != null)
		// listener.onChangeValue();
		// }
		// });
	}

	private void getProjects() {
		WydatkiGlobals globals = WydatkiGlobals.getInstance();
		ArrayList<Project> projects = globals.getProjectsList();

		ArrayList<SpinnerObject> items = new ArrayList<SpinnerObject>();
		items.add(new SpinnerObject(-1, getText(R.string.no_selected_value)
				.toString()));

		if (projects != null)
			for (Project item : projects) {
				if (item.isActive())
					items.add(new SpinnerObject(item.getId(), item.getName()));
			}

		SpinnerObject[] strArray = new SpinnerObject[items.size()];
		items.toArray(strArray);

		SpinnerAdapter adapter = new SpinnerAdapter(getActivity(),
				android.R.layout.simple_spinner_item, strArray);

		spn_project.setAdapter(adapter);

	}

	private void getAccounts(Spinner spinner) {
		WydatkiGlobals globals = WydatkiGlobals.getInstance();
		ArrayList<Account> accounts = globals.getAccountsList();

		if (accounts != null) {
			ArrayList<SpinnerObject> items = new ArrayList<SpinnerObject>();
			for (Account item : accounts) {
				if (item.isActive())
					items.add(new SpinnerObject(item.getId(), item.getName()
							+ " "
							+ UnitConverter.doubleToCurrency(item.getBalance())));
			}

			SpinnerObject[] strArray = new SpinnerObject[items.size()];
			items.toArray(strArray);

			SpinnerAdapter adapter = new SpinnerAdapter(getActivity(),
					android.R.layout.simple_spinner_item, strArray);
			spinner.setAdapter(adapter);

		}
	}

	private void getCategories() {

		WydatkiGlobals globals = WydatkiGlobals.getInstance();
		ArrayList<Category> categories = globals.getCategoriesList();
		if (categories != null) {

			ArrayList<SpinnerObject> items = new ArrayList<SpinnerObject>();
			items.add(new SpinnerObject(-1, getText(R.string.no_selected_value)
					.toString()));

			int blockCategoryId = -1;
			for (Category item : categories) {
				if (!item.isActive()) {
					blockCategoryId = item.getId();
					continue;
				}
				if (item.isActive() && item.getParentId() != blockCategoryId) {
					if (item.getParentId() > 0) {
						items.add(new SpinnerObject(item.getId(), item.getLvl()
								+ item.getName()));
					} else {
						items.add(new SpinnerObject(item.getId(), item
								.getName()));
					}
				}
				if (item.getParentId() == blockCategoryId)
					blockCategoryId = item.getId();

			}

			SpinnerObject[] strArray = new SpinnerObject[items.size()];
			items.toArray(strArray);

			SpinnerAdapter adapter = new SpinnerAdapter(getActivity(),
					android.R.layout.simple_spinner_item, strArray);

			category.setAdapter(adapter);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		restoreValues();
	}

	@Override
	public void onStop() {
		super.onStop();
		saveValues();
	}

	private void saveValues() {

		if (accountFrom != null) {
			helper.accMinusPosition = accountFrom.getSelectedItemPosition();
			SpinnerObject so = (SpinnerObject) accountFrom.getSelectedItem();
			if (so != null)
				if (isPositive.isChecked())
					currentTransaction.setAccPlus(so.getId());
				else
					currentTransaction.setAccMinus(so.getId());
		}

		if (accountTo != null) {
			SpinnerObject so = (SpinnerObject) accountTo.getSelectedItem();
			if (so != null) {
				helper.accPlusPosition = accountTo.getSelectedItemPosition();
				currentTransaction.setAccPlus(so.getId());
			}
		}

		if (spn_project != null) {
			SpinnerObject so = (SpinnerObject) spn_project.getSelectedItem();
			if (so != null) {
				helper.projektPosition = spn_project.getSelectedItemPosition();
				currentTransaction.setProjectId(so.getId());
			}
		}

		if (category != null) {
			helper.categoryPosition = category.getSelectedItemPosition();
		}

		helper.note = note.getText().toString().trim();

		helper.value = value.getText().toString();

		if (adapter != null)
			helper.items = adapter.getAllItems();

		helper.Year = Year;
		helper.day = day;
		helper.month = month;
		helper.minute = minute;
		helper.hour = hour;

	}

	private void restoreValues() {
		if (accountFrom != null && helper.accMinusPosition >= 0) {
			accountFrom.setSelection(helper.accMinusPosition);
		}
		if (accountTo != null && helper.accPlusPosition >= 0) {
			accountTo.setSelection(helper.accPlusPosition);
		}
		if (spn_project != null && helper.projektPosition >= 0) {
			spn_project.setSelection(helper.projektPosition);
		}
		if (category != null && helper.categoryPosition >= 0) {
			category.setSelection(helper.categoryPosition);
		}
		Year = helper.Year;
		day = helper.day;
		month = helper.month;
		minute = helper.minute;
		hour = helper.hour;

		setDate();
		setTime();

		value.setText(helper.value);
		note.setText(helper.note);

		adapter = new InvokeTransactionAdapter(getActivity(), helper.items);
	}

	private class Transactionhelper {
		private String note = "";
		private int accMinusPosition = -1;
		private int accPlusPosition = -1;
		private int categoryPosition = -1;
		private int projektPosition = -1;
		private String value = "";
		private int Year;
		private int month;
		private int day;
		private int hour;
		private int minute;
		ArrayList<InvokeTransactionParameter> items;

		public Transactionhelper() {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			Year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH);
			day = cal.get(Calendar.DAY_OF_MONTH);

			hour = cal.get(Calendar.HOUR_OF_DAY);
			minute = cal.get(Calendar.MINUTE);
		}
	}

	@Override
	public void onClick(View view) {
		if (view == btn_time)
			new TimePickerDialog(getActivity(), mTimeSetListener, hour, minute,
					true).show();
		else
			new DatePickerDialog(getActivity(), mDateSetListener, Year, month,
					day).show();
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			Year = year;
			month = monthOfYear;
			day = dayOfMonth;
			setDate();

		}
	};

	private void setDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);

		btn_date.setText(UnitConverter.convertDateToString(cal.getTime()));
	}

	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
			hour = hourOfDay;
			minute = minuteOfHour;
			setTime();
		}
	};

	private void setTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);

		btn_time.setText(UnitConverter.convertTimeToString(cal.getTime()));
	}

}
