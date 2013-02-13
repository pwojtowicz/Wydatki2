package pl.wppiotrek85.wydatkibase.newfragments;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.adapters.AccountAdapter;
import pl.wppiotrek85.wydatkibase.adapters.BaseObjectAdapter;
import pl.wppiotrek85.wydatkibase.entities.Account;
import pl.wppiotrek85.wydatkibase.fragmentactions.IAccountFragmentActions;
import pl.wppiotrek85.wydatkibase.interfaces.IOnAdapterCheckboxClick;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import pl.wppiotrek85.wydatkibase.units.UnitConverter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AccountListFragment extends BaseListFragment<Account> {

	private static final int BUNDLE_NEW_TRANSATION = 1;
	private static final int BUNDLE_NEW_TRANSFER = 2;

	public static final String BUNDLE_SHOW_AS_LIST = "asList";

	private TextView tbx_accounts_balance;

	public AccountListFragment() {
		super(R.layout.fragment_accounts_list);
	}

	@Override
	protected void linkOtherView() {
		tbx_accounts_balance = (TextView) getCurrentView().findViewById(
				R.id.account_tbx_balance);
	}

	@Override
	public ArrayList<Account> getObjectList() {
		return WydatkiGlobals.getInstance().getAccountsList();
	}

	@Override
	public BaseObjectAdapter<Account> getAdapter(FragmentActivity activity,
			ArrayList<Account> list, IOnAdapterCheckboxClick object) {
		return new AccountAdapter(activity, list, object);
	}

	@Override
	protected void configureView() {
		Bundle extras = getArguments();
		boolean showAsList = false;
		if (extras != null) {
			showAsList = extras.getBoolean(BUNDLE_SHOW_AS_LIST);
		}

		Button btn_newTransaction = (Button) getCurrentView().findViewById(
				R.id.account_btn_new_transaction);
		Button btn_newTransfer = (Button) getCurrentView().findViewById(
				R.id.account_btn_new_transfer);
		if (showAsList) {
			btn_newTransaction.setVisibility(Button.GONE);
			btn_newTransfer.setVisibility(Button.GONE);
			((RelativeLayout) getCurrentView().findViewById(
					R.id.rl_accounts_balance))
					.setVisibility(RelativeLayout.GONE);
		} else {
			tbx_accounts_balance = (TextView) getCurrentView().findViewById(
					R.id.account_tbx_balance);
			btn_newTransaction.setTag(BUNDLE_NEW_TRANSATION);
			btn_newTransfer.setTag(BUNDLE_NEW_TRANSFER);
			btn_newTransaction.setOnClickListener(btnTransactions);
			btn_newTransfer.setOnClickListener(btnTransactions);
		}

	}

	OnClickListener btnTransactions = new OnClickListener() {

		@Override
		public void onClick(View v) {
			btnNewAction(v);
		}
	};

	public void btnNewAction(View v) {
		int action = (Integer) v.getTag();
		if (actions != null) {
			switch (action) {
			case BUNDLE_NEW_TRANSATION:
				((IAccountFragmentActions) actions).newTransaction();
				break;
			case BUNDLE_NEW_TRANSFER:
				((IAccountFragmentActions) actions).newTransfer();
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void afterReloadAction() {
		if (tbx_accounts_balance != null) {
			ArrayList<Account> objects = getObjectList();
			if (objects != null && objects.size() > 0) {
				Double value = 0.0;
				for (Account account : objects) {
					if (account.isSumInGlobalBalance())
						value += account.getBalance();
				}
				tbx_accounts_balance.setText(UnitConverter
						.doubleToCurrency(value));

				if (value < 0)
					tbx_accounts_balance.setTextColor(getResources().getColor(
							R.color.darkRed));
				else if (value > 0)
					tbx_accounts_balance.setTextColor(getResources().getColor(
							R.color.darkGreen));

			}
		}
	}
}
