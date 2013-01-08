package pl.wppiotrek85.wydatkibase.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.adapters.InvokeTransactionAdapter;
import pl.wppiotrek85.wydatkibase.adapters.SpinnerAdapter;
import pl.wppiotrek85.wydatkibase.adapters.SpinnerAdapter.SpinerHelper;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.entities.Account;
import pl.wppiotrek85.wydatkibase.entities.Category;
import pl.wppiotrek85.wydatkibase.entities.InvokeTransactionParameter;
import pl.wppiotrek85.wydatkibase.entities.Parameter;
import pl.wppiotrek85.wydatkibase.entities.Project;
import pl.wppiotrek85.wydatkibase.entities.SpinnerObject;
import pl.wppiotrek85.wydatkibase.entities.Transaction;
import pl.wppiotrek85.wydatkibase.interfaces.ITransactionListener;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import pl.wppiotrek85.wydatkibase.units.UnitConverter;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class TransactionFragment extends ObjectBaseFragment {

	// public static final String BUNDLE_IS_NEW_TRANSACTION = "newTransaction";
	public static final String BUNDLE_IS_NEW_TRANSFER = "newTransfer";

	private static final int TIME_DIALOG_ID = 0;
	private static final int DATE_DIALOG_ID = 1;

	private ITransactionListener listener;
	private ListView list;
	private InvokeTransactionAdapter adapter;
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
	private boolean isTransfer = false;
	private int Year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int i = 0;
	private int accFrom = -1;
	private int accTo = -1;
	private int projPos = -1;
	private boolean hasAdditionalParameters = false;

	private Transaction currentTransaction = new Transaction();
	private Transactionhelper helper = new Transactionhelper();

	public TransactionFragment() {
		super(false, false);
		this.listener = null;
	}

	public TransactionFragment(boolean shouldReload,
			ITransactionListener listener) {
		super(shouldReload, false);
		this.listener = listener;
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnFirtsShowFragment() {
		System.out.println("OnFirtsShowFragment " + String.valueOf(i));
	}

	@Override
	public void OnFragmentActive() {
		System.out.println("OnFragmentActive " + String.valueOf(i));

	}

	@Override
	public void refreshFragment(boolean forceRefresh) {
		System.out.println("refreshFragment " + String.valueOf(i));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("onCreateView " + String.valueOf(i));
		View convertView = inflater.inflate(
				R.layout.fragment_transaction_layout, null);

		Bundle extras = getArguments();
		if (extras != null) {
			isTransfer = extras.getBoolean(BUNDLE_IS_NEW_TRANSFER, false);

			i = extras.getInt("INDEX");
		}
		System.out.println("onCreateView " + String.valueOf(i));
		linkViews(convertView);
		configureViews();
		return convertView;
	}

	private void linkViews(View convertView) {

		list = (ListView) convertView
				.findViewById(R.id.invoke_transactions_listview);

		LayoutInflater layoutInflater = (LayoutInflater) this.getActivity()
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

		list.addHeaderView(header);
		list.addFooterView(footer);

		list.setClickable(true);
		list.setItemsCanFocus(true);
	}

	public void configureViews() {
		configureDateAndTime();

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
					// if (oh.object.getId() > 0)
					onCategoryChange(oh.object);

				}

				public void onNothingSelected(AdapterView<?> adapterView) {
				}
			});
		}

		list.setAdapter(adapter);

		if (accountFrom != null)
			getAccounts(accountFrom);

		if (isTransfer && accountTo != null)
			getAccounts(accountTo);

		if (category != null)
			getCategories();

		if (spn_project != null)
			getProjects();

		value.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (listener != null)
					listener.onChangeValue();
			}
		});

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

	private void configureDateAndTime() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);

		hour = cal.get(Calendar.HOUR_OF_DAY);
		minute = cal.get(Calendar.MINUTE);

		btn_date.setText(UnitConverter.convertDateToString(new Date()));
		btn_time.setText(UnitConverter.convertTimeToString(new Date()));
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

				// if (item.isActive() && item.getParentId() > 0
				// && item.getParentId() != blockCategoryId)
				//
				// else if (item.isActive())

			}

			SpinnerObject[] strArray = new SpinnerObject[items.size()];
			items.toArray(strArray);

			SpinnerAdapter adapter = new SpinnerAdapter(getActivity(),
					android.R.layout.simple_spinner_item, strArray);

			category.setAdapter(adapter);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		System.out.println("onSaveInstanceState " + String.valueOf(i));
	}

	@Override
	public void onStart() {
		super.onStart();
		System.out.println("onStart " + String.valueOf(i));
	}

	@Override
	public void onResume() {
		super.onResume();
		System.out.println("onResume " + String.valueOf(i));
		restoreValues();
	}

	@Override
	public void onPause() {
		super.onPause();
		System.out.println("onPause " + String.valueOf(i));
	}

	@Override
	public void onStop() {
		super.onStop();
		System.out.println("onStop " + String.valueOf(i));
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
		value.setText(helper.value);
		note.setText(helper.note);

		adapter = new InvokeTransactionAdapter(getActivity(), helper.items);
	}

	@Override
	public ArrayList<Integer> getSelectedItemsList() {
		return null;
	}

	public double getCurrentValue() {
		if (isTransfer)
			return 0.0;
		String v = value.getText().toString().trim();
		if (value.length() > 0)
			return Double.parseDouble(value.getText().toString())
					* (isPositive.isChecked() ? 1 : -1);

		return 0.0;
	}

	public ValidationHelper<Transaction> getCurrentTransaction() {
		prepareToSave();

		ValidationHelper<Transaction> result = new ValidationHelper<Transaction>();
		result.isValid = true;
		result.item = currentTransaction;

		return result;
	}

	private void prepareToSave() {
		String valueTxt = value.getText().toString();
		String noteTxt = note.getText().toString();
		// if (valueTxt.length() == 0) {
		// AlertDialog warning = DialogFactory.create(DialogType.ErrorDialog,
		// null, this, null);
		// warning.setMessage(getText(R.string.dialog_no_value));
		// warning.show();
		//
		// } else
		{

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Year);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DAY_OF_MONTH, day);
			cal.set(Calendar.HOUR_OF_DAY, hour);
			cal.set(Calendar.MINUTE, minute);

			currentTransaction.setValue(Double.parseDouble(valueTxt));
			currentTransaction.setNote(noteTxt);
			currentTransaction.setDate(cal.getTime());

			if (!isTransfer) {
				int accountFromId = ((SpinnerObject) accountFrom
						.getSelectedItem()).getId();

				int projectId = ((SpinnerObject) spn_project.getSelectedItem())
						.getId();

				if (projectId > 0) {
					currentTransaction.setProjectId(projectId);
				}

				currentTransaction.setAccMinus(accountFromId);
				int accountToId = -1;
				if (isTransfer) {
					accountToId = ((SpinnerObject) accountTo.getSelectedItem())
							.getId();
					currentTransaction.setAccPlus(accountToId);
				}

				if (isPositive.isChecked()) {
					accountToId = accountFromId;
					currentTransaction.setAccPlus(accountToId);
					currentTransaction.setAccMinus(0);
				}

				int categoryId = ((SpinnerObject) category.getSelectedItem())
						.getId();

				Category c = new Category();
				c.setId(categoryId);
				if (hasAdditionalParameters) {
					ArrayList<Parameter> items = new ArrayList<Parameter>();
					ArrayList<InvokeTransactionParameter> parameters = adapter
							.getAllItems();
					for (InvokeTransactionParameter item : parameters) {
						items.add(new Parameter(item.getId(), item.getValue()));
					}
					Parameter[] elements = new Parameter[items.size()];
					c.setAttributes(items.toArray(elements));
				}

				currentTransaction.setCategory(c);
			} else if (isTransfer) {
				int accountFromId = ((SpinnerObject) accountFrom
						.getSelectedItem()).getId();
				int accountToId = ((SpinnerObject) accountTo.getSelectedItem())
						.getId();
				currentTransaction.setAccMinus(accountFromId);
				currentTransaction.setAccPlus(accountToId);
			}
		}
	}

	private class Transactionhelper {
		private String note = "";
		private int accMinusPosition = -1;
		private int accPlusPosition = -1;
		private int categoryPosition = -1;
		private int projektPosition = -1;
		private String value = "";
		ArrayList<InvokeTransactionParameter> items;
	}

	public class ValidationHelper<T> {
		private boolean isValid = false;
		private String errorMessage;
		private T item;

		public boolean isValid() {
			return isValid;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		public T getItem() {
			return item;
		}

	}
}
