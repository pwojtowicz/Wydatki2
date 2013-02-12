package pl.wppiotrek85.wydatkiphone;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.activities.FragmentBaseActivity;
import pl.wppiotrek85.wydatkibase.adapters.SimpleFragmentAdapter;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.entities.Account;
import pl.wppiotrek85.wydatkibase.entities.FragmentInfo;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryManagerMethods;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryTypes;
import pl.wppiotrek85.wydatkibase.fragmentactions.IAccountFragmentActions;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import pl.wppiotrek85.wydatkibase.newfragments.AccountListFragment;
import pl.wppiotrek85.wydatkibase.newfragments.InvokeTransactionFragment;
import pl.wppiotrek85.wydatkibase.newfragments.SettingsFragment;
import pl.wppiotrek85.wydatkibase.newfragments.TransactionFragmentList;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;

public class StartActivity extends FragmentBaseActivity implements
		IAccountFragmentActions {

	private ViewPager mViewPager;
	private ArrayList<FragmentInfo> fragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_activiry);
		createFragments();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_start_activiry, menu);
		return true;
	}

	private void createFragments() {
		fragments = new ArrayList<FragmentInfo>();

		AccountListFragment accounts = new AccountListFragment();
		accounts.setIFragmentActions(this);

		TransactionFragmentList transactions = new TransactionFragmentList();

		SettingsFragment settingsFragment = new SettingsFragment();

		fragments.add(new FragmentInfo(accounts, "Konta"));

		fragments.add(new FragmentInfo(transactions, "Transakcje"));

		fragments.add(new FragmentInfo(settingsFragment, "Ustawienia"));

		SimpleFragmentAdapter fAdapter = new SimpleFragmentAdapter(
				getSupportFragmentManager(), fragments);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(fAdapter);
	}

	@Override
	public void onResume() {
		super.onResume();

		ArrayList<Account> accounts = WydatkiGlobals.getInstance()
				.getAccountsList();

		if (accounts == null) {
			new ObjectManager(ERepositoryTypes.Accounts, this,
					ERepositoryManagerMethods.ReadAll);
		}
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		if (response.bundle instanceof ArrayList<?>) {
			if (((ArrayList<Object>) response.bundle).size() > 0) {
				Object o = ((ArrayList<Object>) response.bundle).get(0);
				if (o instanceof Account) {
					ArrayList<Account> list = (ArrayList<Account>) response.bundle;
					WydatkiGlobals.getInstance().setAccounts(list);
				}
			}
		}
		createFragments();
	}

	@Override
	public void onAddBtnClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void newTransfer() {
		Intent intent = new Intent(this, TransactionActivity.class);
		intent.putExtra(InvokeTransactionFragment.BUNDLE_IS_NEW_TRANSFER, true);
		startActivity(intent);

	}

	@Override
	public void newTransaction() {
		Intent intent = new Intent(this, TransactionActivity.class);
		intent.putExtra(InvokeTransactionFragment.BUNDLE_IS_NEW_TRANSACTION,
				true);
		startActivity(intent);
	}

	public void btnSettingsClick(View v) {

	}

	public void btnUnitsClick(View v) {
		Intent i = new Intent(this, ObjectManagerActivity.class);
		startActivity(i);
	}
}
