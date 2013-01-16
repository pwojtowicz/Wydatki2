package pl.wppiotrek85.wydatkibase.fragments;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.adapters.AccountAdapter;
import pl.wppiotrek85.wydatkibase.adapters.BaseObjectAdapter;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.entities.Account;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryTypes;
import pl.wppiotrek85.wydatkibase.interfaces.IFragmentActions;
import pl.wppiotrek85.wydatkibase.interfaces.IOnAdapterCheckboxClick;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import pl.wppiotrek85.wydatkibase.units.UnitConverter;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AccountFragmentList extends ObjectListBaseFragment<Account> {

	private boolean shouldBeShowAsEditList = false;

	private TextView tbx_accounts_balance;

	public AccountFragmentList() {
		super(R.layout.fragment_accounts_list, ERepositoryTypes.Accounts,
				false, false);
	}

	public AccountFragmentList(boolean shouldReload) {
		super(R.layout.fragment_accounts_list, ERepositoryTypes.Accounts,
				shouldReload, false);
	}

	public AccountFragmentList(boolean shouldReload,
			boolean shouldBeShowAsEditList) {
		super(R.layout.fragment_accounts_list, ERepositoryTypes.Accounts,
				shouldReload, false);
		this.shouldBeShowAsEditList = shouldBeShowAsEditList;
	}

	public AccountFragmentList(boolean shouldReload, IFragmentActions actions) {
		super(R.layout.fragment_accounts_list, ERepositoryTypes.Accounts,
				shouldReload, false);
		this.actions = actions;
	}

	public AccountFragmentList(boolean shouldReload, IFragmentActions actions,
			boolean shouldBeShowAsEditList) {
		super(R.layout.fragment_accounts_list, ERepositoryTypes.Accounts,
				shouldReload, false);
		this.actions = actions;
		this.shouldBeShowAsEditList = shouldBeShowAsEditList;
	}

	@Override
	public void linkViews(View convertView) {
		tbx_accounts_balance = (TextView) convertView
				.findViewById(R.id.account_tbx_balance);

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
			updateBalance(list);
		}
	}

	// @Override
	// public void refreshFragment(boolean forceRefresh) {
	// if (!forceRefresh) {
	//
	// if (list == null)
	// forceRefresh = true;
	// else {
	// if (adapter == null)
	// adapter =
	// else
	// adapter.reloadItems(list);
	// objectListView.setAdapter(adapter);
	//
	// updateBalance(list);
	//
	// }
	// }
	//
	// if (forceRefresh) {
	// manager =
	// }
	// }

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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}

	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos,
			long id) {
		actions.onShowAccountTransactions(((Account) adapter.getItem(pos))
				.getId());
		return true;
	}

	@Override
	public BaseObjectAdapter<Account> getAdapter(FragmentActivity activity,
			ArrayList<Account> list, IOnAdapterCheckboxClick object) {
		return new AccountAdapter(activity, list, object);
	}

	@Override
	public ArrayList<Account> getObjectList() {
		return WydatkiGlobals.getInstance().getAccountsList();
	}

	@Override
	public void afterRefreshAction() {

	}

}
