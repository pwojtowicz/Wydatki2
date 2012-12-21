package pl.wppiotrek85.wydatkibase.fragments.edit;

import java.util.Date;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.entities.Account;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryManagerMethods;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryTypes;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import pl.wppiotrek85.wydatkibase.support.ListSupport;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ToggleButton;

public class EditAccountFragment extends EditObjectBaseFragment<Account> {

	private EditText etbx_balance;
	private CheckBox cbx_visibleForAll;
	private CheckBox cbx_sumInGlobalbalance;
	private ToggleButton tbtn_isPositive;

	public EditAccountFragment() {
		super(false, null);
	}

	public EditAccountFragment(boolean shouldReload, Account object) {
		super(shouldReload, object);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View convertView = inflater.inflate(R.layout.edit_account_layout, null);
		return convertView;
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void linkViews() {
		etbx_balance = (EditText) getView().findViewById(
				R.id.edit_account_etxb_balance);
		cbx_visibleForAll = (CheckBox) getView().findViewById(
				R.id.edit_account_cbx_visibleForAll);
		cbx_sumInGlobalbalance = (CheckBox) getView().findViewById(
				R.id.edit_account_cbx_sumInGlobalbalance);
		tbtn_isPositive = (ToggleButton) getView().findViewById(
				R.id.edit_account_tbtn_isPositive);
	}

	@Override
	public void configureViews() {
		if (isUpdate) {
			etbx_name.setText(currentObject.getName());
			etbx_balance.setText(String.valueOf(Math.abs(currentObject
					.getBalance())));
			if (currentObject.getBalance() > 0)
				tbtn_isPositive.setChecked(true);

			cbx_isActive.setChecked(currentObject.isActive());
			cbx_visibleForAll.setChecked(currentObject.isVisibleForAll());
			cbx_sumInGlobalbalance.setChecked(currentObject
					.isSumInGlobalBalance());
		}

	}

	@Override
	protected void prepareToSave() {
		boolean isValid = false;
		double value = 0;
		String name = etbx_name.getText().toString().trim();
		if (isUpdate
				|| (name.length() > 0 && !ListSupport.isAccountNameUsed(name))) {
			if (etbx_balance.getText().length() > 0) {
				try {
					value = Double.parseDouble(etbx_balance.getText()
							.toString())
							* (tbtn_isPositive.isChecked() ? 1 : -1);
					isValid = true;
				} catch (Exception e) {

				}
			}
		}

		if (isValid) {
			Account account = new Account();

			if (isUpdate) {
				int accountId = currentObject.getId();
				account.setId(accountId);
			}

			account.setName(name);
			account.setIsActive(cbx_isActive.isChecked());
			account.setIsVisibleForAll(cbx_visibleForAll.isChecked());
			account.setLastActionDate(new Date());
			account.setIsSumInGlobalBalance(cbx_sumInGlobalbalance.isChecked());
			account.setBalance(value);
			account.setImageIndex((byte) 1);
			this.currentObject = account;
			saveObject();
		}
	}

	@Override
	protected void saveObject() {
		if (isUpdate)
			new ObjectManager(ERepositoryTypes.Accounts, this,
					ERepositoryManagerMethods.Update, currentObject);
		else
			new ObjectManager(ERepositoryTypes.Accounts, this,
					ERepositoryManagerMethods.Create, currentObject);

	}

}
