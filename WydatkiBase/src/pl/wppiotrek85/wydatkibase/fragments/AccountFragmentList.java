package pl.wppiotrek85.wydatkibase.fragments;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.adapters.AccountAdapter;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.entities.Account;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryManagerMethods;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryTypes;
import pl.wppiotrek85.wydatkibase.interfaces.IFragmentActions;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import pl.wppiotrek85.wydatkibase.units.UnitConverter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class AccountFragmentList extends ObjectBaseFragment {

	private ListView objectListView;
	private AccountAdapter adapter;
	private boolean shouldBeShowAsEditList = false;
	private IFragmentActions actions;
	private TextView tbx_accounts_balance;
	private ObjectManager manager;

	public AccountFragmentList() {
		super(false, false);
	}

	public AccountFragmentList(boolean shouldReload) {
		super(shouldReload, false);
	}

	public AccountFragmentList(boolean shouldReload,
			boolean shouldBeShowAsEditList) {
		super(shouldReload, false);
		this.shouldBeShowAsEditList = shouldBeShowAsEditList;
	}

	public AccountFragmentList(boolean shouldReload, IFragmentActions actions) {
		super(shouldReload, false);
		this.actions = actions;
	}

	public AccountFragmentList(boolean shouldReload, IFragmentActions actions,
			boolean shouldBeShowAsEditList) {
		super(shouldReload, false);
		this.actions = actions;
		this.shouldBeShowAsEditList = shouldBeShowAsEditList;
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
		View convertView = inflater.inflate(R.layout.fragment_accounts_list,
				null);

		objectListView = (ListView) convertView.findViewById(R.id.listview);

		objectListView
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int pos, long id) {
						actions.onUpdateObject(adapter.getItem(pos));
						return true;
					}
				});

		if (adapter != null)
			objectListView.setAdapter(adapter);
		linkViews(convertView);
		return convertView;
	}

	private void linkViews(View convertView) {
		tbx_accounts_balance = (TextView) convertView
				.findViewById(R.id.account_tbx_balance);

		Button btn_add = (Button) convertView.findViewById(R.id.btn_add_new);
		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (actions != null) {
					actions.onAddBtnClick();
				}
			}
		});

		if (shouldBeShowAsEditList) {
			convertView.findViewById(R.id.ll_transactions).setVisibility(
					LinearLayout.GONE);
			convertView.findViewById(R.id.rl_accounts_balance).setVisibility(
					LinearLayout.GONE);
		} else {
			btn_add.setVisibility(Button.GONE);
		}
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		if (response.bundle instanceof ArrayList<?>) {
			ArrayList<Account> list = (ArrayList<Account>) response.bundle;
			WydatkiGlobals.getInstance().setAccounts(list);
			refreshFragment(false);
		}
	}

	@Override
	public void refreshFragment(boolean forceRefresh) {
		if (!forceRefresh) {
			ArrayList<Account> list = WydatkiGlobals.getInstance()
					.getAccountsList();
			if (list == null)
				forceRefresh = true;
			else {
				if (adapter == null)
					adapter = new AccountAdapter(getActivity(), list, null);
				else
					adapter.reloadItems(list);
				objectListView.setAdapter(adapter);

				updateBalance(list);

			}
		}

		if (forceRefresh) {
			manager = new ObjectManager(ERepositoryTypes.Accounts, this,
					ERepositoryManagerMethods.ReadAll);
		}
	}

	private void updateBalance(ArrayList<Account> list) {
		Double value = 0.0;
		if (!shouldBeShowAsEditList) {
			for (Account account : list) {
				if (account.isSumInGlobalBalance())
					value += account.getBalance();
			}
			tbx_accounts_balance.setText(UnitConverter.doubleToCurrency(value));

			if (value < 0)
				tbx_accounts_balance.setTextColor(getResources().getColor(
						R.color.darkRed));
			else if (value > 0)
				tbx_accounts_balance.setTextColor(getResources().getColor(
						R.color.darkGreen));
		}
	}

	@Override
	public ArrayList<Integer> getSelectedItemsList() {
		return null;
	}

}
