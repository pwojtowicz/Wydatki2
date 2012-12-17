package pl.wppiotrek85.wydatkibase.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.adapters.InvokeTransactionAdapter;
import pl.wppiotrek85.wydatkibase.adapters.SpinnerAdapter;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.entities.Account;
import pl.wppiotrek85.wydatkibase.entities.Category;
import pl.wppiotrek85.wydatkibase.entities.Project;
import pl.wppiotrek85.wydatkibase.entities.SpinnerObject;
import pl.wppiotrek85.wydatkibase.interfaces.ITransactionListener;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import pl.wppiotrek85.wydatkibase.units.UnitConverter;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class TransactionFragment extends ObjectBaseFragment {

	public static final String BUNDLE_IS_NEW_TRANSACTION = "newTransaction";
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

	public TransactionFragment() {
		super(false);
		this.listener = null;
	}

	public TransactionFragment(boolean shouldReload,
			ITransactionListener listener) {
		super(shouldReload);
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

		View convertView = inflater.inflate(
				R.layout.fragment_transaction_layout, null);

		Bundle extras = getArguments();
		if (extras != null) {
			extras.getBoolean(BUNDLE_IS_NEW_TRANSACTION, false);
			extras.getBoolean(BUNDLE_IS_NEW_TRANSFER, false);

			i = extras.getInt("INDEX");
		}
		System.out.println("onCreateView " + String.valueOf(i));
		linkViews(convertView);
		configureViews();
		return convertView;
	}

	private void linkViews(View convertView) {
		adapter = new InvokeTransactionAdapter(getActivity());

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
			// category.setOnItemSelectedListener(new OnItemSelectedListener() {
			//
			// public void onItemSelected(AdapterView<?> adapterView,
			// View view, int index, long id) {
			// SpinerHelper oh = (SpinerHelper) view.getTag();
			// if (oh.object.getId() > 0)
			// onCategoryChange(oh.object);
			//
			// }
			//
			// public void onNothingSelected(AdapterView<?> adapterView) {
			// }
			// });
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
		if (projects != null) {

			ArrayList<SpinnerObject> items = new ArrayList<SpinnerObject>();
			items.add(new SpinnerObject(-1, getText(R.string.no_selected_value)
					.toString()));

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
			accFrom = accountFrom.getSelectedItemPosition();
		}

		if (accountTo != null) {
			accTo = accountTo.getSelectedItemPosition();
		}

		if (spn_project != null) {
			projPos = spn_project.getSelectedItemPosition();
		}
	}

	private void restoreValues() {
		if (accountFrom != null && accFrom >= 0) {
			accountFrom.setSelection(accFrom);
		}
		if (accountTo != null && accTo >= 0) {
			accountTo.setSelection(accTo);
		}
		if (spn_project != null && projPos >= 0) {
			spn_project.setSelection(projPos);
		}
	}
}
