package pl.wppiotrek85.wydatkiphone;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.activities.FragmentBaseActivity;
import pl.wppiotrek85.wydatkibase.adapters.SimpleFragmentAdapter;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.entities.Account;
import pl.wppiotrek85.wydatkibase.entities.Category;
import pl.wppiotrek85.wydatkibase.entities.FragmentInfo;
import pl.wppiotrek85.wydatkibase.entities.Parameter;
import pl.wppiotrek85.wydatkibase.entities.Project;
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
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class StartActivity extends FragmentBaseActivity implements
		IAccountFragmentActions {

	private static final int BUNDLE_NEW_TRANSFER = 11111;
	private static final int BUNDLE_NEW_TRANSACTION = 22222;
	private ViewPager mViewPager;
	private ArrayList<FragmentInfo> fragments;
	private SimpleFragmentAdapter fAdapter;

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

		fAdapter = new SimpleFragmentAdapter(getSupportFragmentManager(),
				fragments);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(fAdapter);
	}

	@Override
	public void onResume() {
		super.onResume();
		checkIsAllListEnabled();
	}

	private void checkIsAllListEnabled() {
		boolean hasAccounts = WydatkiGlobals.getInstance().getAccountsList() != null;
		boolean hasCategories = WydatkiGlobals.getInstance()
				.getCategoriesList() != null;
		boolean hasParameters = WydatkiGlobals.getInstance()
				.getParametersList() != null;
		boolean hasProjects = WydatkiGlobals.getInstance().getProjectsList() != null;

		if (!hasAccounts) {
			new ObjectManager(ERepositoryTypes.Accounts, this,
					ERepositoryManagerMethods.ReadAll);
		} else if (!hasCategories) {
			new ObjectManager(ERepositoryTypes.Categories, this,
					ERepositoryManagerMethods.ReadAll);
		} else if (!hasParameters) {
			new ObjectManager(ERepositoryTypes.Parameters, this,
					ERepositoryManagerMethods.ReadAll);
		} else if (!hasProjects) {
			new ObjectManager(ERepositoryTypes.Projects, this,
					ERepositoryManagerMethods.ReadAll);
		} else {
			createFragments();
		}
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		if (response.bundle instanceof ArrayList<?>) {
			Object o = ((ArrayList<Object>) response.bundle).get(0);
			if (o instanceof Account) {
				ArrayList<Account> list = (ArrayList<Account>) response.bundle;
				WydatkiGlobals.getInstance().setAccounts(list);
			} else if (o instanceof Category) {
				ArrayList<Category> list = (ArrayList<Category>) response.bundle;
				WydatkiGlobals.getInstance().setCategories(list);
			} else if (o instanceof Parameter) {
				ArrayList<Parameter> list = (ArrayList<Parameter>) response.bundle;
				WydatkiGlobals.getInstance().setParameters(list);
			} else if (o instanceof Project) {
				ArrayList<Project> list = (ArrayList<Project>) response.bundle;
				WydatkiGlobals.getInstance().setProjects(list);
			}
			checkIsAllListEnabled();
		}
	}

	@Override
	public void onAddBtnClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void newTransfer() {
		Intent intent = new Intent(this, InvokeTransactionManagerActivity.class);
		intent.putExtra(InvokeTransactionFragment.BUNDLE_IS_NEW_TRANSFER, true);
		startActivityForResult(intent, BUNDLE_NEW_TRANSFER);

	}

	@Override
	public void newTransaction() {
		Intent intent = new Intent(this, InvokeTransactionManagerActivity.class);
		intent.putExtra(InvokeTransactionFragment.BUNDLE_IS_NEW_TRANSACTION,
				true);
		startActivityForResult(intent, BUNDLE_NEW_TRANSACTION);
	}

	public void btnSettingsClick(View v) {

	}

	public void btnUnitsClick(View v) {
		Intent i = new Intent(this, ObjectsListManagerActivity.class);
		startActivity(i);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == BUNDLE_NEW_TRANSACTION
				|| requestCode == BUNDLE_NEW_TRANSFER) {
			if (resultCode == BaseActivity.BUNDLE_ACTIVITY_RESPONSE_NEED_UPADTE) {
				WydatkiGlobals.getInstance().setAccounts(null);
				checkIsAllListEnabled();
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// case R.id.menu_refresh:
		// refreshActualFragment();
		// return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void refreshActualFragment() {
		Fragment currentFragment = fAdapter
				.getItem(mViewPager.getCurrentItem());
		if (currentFragment instanceof TransactionFragmentList) {
			((TransactionFragmentList) currentFragment).reload(true);
		}

	}
}
