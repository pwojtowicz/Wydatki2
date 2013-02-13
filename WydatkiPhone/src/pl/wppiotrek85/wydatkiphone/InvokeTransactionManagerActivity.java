package pl.wppiotrek85.wydatkiphone;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.activities.FragmentBaseActivity;
import pl.wppiotrek85.wydatkibase.adapters.SimpleFragmentAdapter;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.entities.FragmentInfo;
import pl.wppiotrek85.wydatkibase.newfragments.InvokeTransactionFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class InvokeTransactionManagerActivity extends FragmentBaseActivity {

	private ViewPager mViewPager;
	private ArrayList<FragmentInfo> fragments;
	private SimpleFragmentAdapter fragmentAdapter;

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		Intent resultIntent = new Intent();
		setResult(BUNDLE_ACTIVITY_RESPONSE_NEED_UPADTE, resultIntent);
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invoke_transaction_manager);

		createFragments();
	}

	private void createFragments() {
		fragments = new ArrayList<FragmentInfo>();

		if (fragmentAdapter == null)
			fragmentAdapter = new SimpleFragmentAdapter(
					getSupportFragmentManager(), fragments);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(fragmentAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_transaction, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_save:
			saveTransactions();
			return true;
		case R.id.menu_add_transaction:
			btnAddTransactionView();
			return true;
		case R.id.menu_add_transfer:
			btnAddTransferView();
			return true;
		case R.id.menu_delete:
			btnRemoveCurrentView();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void btnRemoveCurrentView() {
		int position = mViewPager.getCurrentItem();
		if ((position < 0) || (position >= fragmentAdapter.getCount())
				|| (fragmentAdapter.getCount() <= 1)) {

		} else {
			fragmentAdapter.removeFragment(mViewPager.getCurrentItem());
		}
	}

	private void btnAddTransferView() {
		Bundle b = new Bundle();
		b.putBoolean(InvokeTransactionFragment.BUNDLE_IS_NEW_TRANSFER, true);
		InvokeTransactionFragment transaction = new InvokeTransactionFragment();
		transaction.setArguments(b);

		addFragment(transaction, "Przelew");
	}

	private void btnAddTransactionView() {
		Bundle b = new Bundle();
		b.putBoolean(InvokeTransactionFragment.BUNDLE_IS_NEW_TRANSACTION, true);
		InvokeTransactionFragment transaction = new InvokeTransactionFragment();
		transaction.setArguments(b);

		addFragment(transaction, "Transakcja");

	}

	private void addFragment(Fragment fragment, String title) {
		fragmentAdapter.addFragment(new FragmentInfo(fragment, title));

		mViewPager.setCurrentItem(fragmentAdapter.getCount() - 1, true);
	}

	private void saveTransactions() {
		// TODO Auto-generated method stub

	}

}
