package pl.wppiotrek85.wydatkiphone;

import java.util.ArrayList;

import pl.billennium.fragmenthelper.FragmentAdapter;
import pl.billennium.fragmenthelper.FragmentObject;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.entities.ModelBase;
import pl.wppiotrek85.wydatkibase.entities.Transaction;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryManagerMethods;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryTypes;
import pl.wppiotrek85.wydatkibase.exceptions.RepositoryException;
import pl.wppiotrek85.wydatkibase.fragments.InvokeTransactionFragment;
import pl.wppiotrek85.wydatkibase.fragments.InvokeTransactionFragment.ValidationHelper;
import pl.wppiotrek85.wydatkibase.interfaces.IReadRepository;
import pl.wppiotrek85.wydatkibase.interfaces.ITransactionListener;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

public class TransactionActivity extends BaseActivity implements
		ITransactionListener, IReadRepository {

	ViewPager mViewPager;

	private ArrayList<FragmentObject> fragments;

	private FragmentAdapter fAdapter;
	int i = 1;
	// boolean isTransaction = false;
	boolean isTransfer = false;

	private ImageButton removeViewBtn;

	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction);

		Bundle bundle = getIntent().getExtras();

		if (bundle != null) {
			// isTransaction = bundle.getBoolean(
			// TransactionFragment.BUNDLE_IS_NEW_TRANSACTION, false);
			isTransfer = bundle.getBoolean(
					InvokeTransactionFragment.BUNDLE_IS_NEW_TRANSFER, false);
		}

		fragments = new ArrayList<FragmentObject>();

		Bundle b = new Bundle();
		// b.putBoolean(TransactionFragment.BUNDLE_IS_NEW_TRANSACTION,
		// isTransaction);
		b.putBoolean(InvokeTransactionFragment.BUNDLE_IS_NEW_TRANSFER,
				isTransfer);
		b.putInt("INDEX", i);

		fragments.add(new FragmentObject(new InvokeTransactionFragment(true,
				this), "Transakcja " + String.valueOf(i), b));

		fAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments,
				0);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(fAdapter);

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int index, float arg1, int arg2) {
				fAdapter.OnPageChanged(index);

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
		linkViews();
	}

	public void linkViews() {
		removeViewBtn = (ImageButton) findViewById(R.id.ib_removeView);

		setBtnEnabledState();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_transaction, menu);
		if (isTransfer)
			for (int i = 0; i < menu.size(); i++) {
				MenuItem item = menu.getItem(i);
				switch (item.getItemId()) {
				case R.id.menu_add_transfer:
				case R.id.menu_add_transaction:
					item.setVisible(false);
				case R.id.menu_delete:
					item.setVisible(false);
				}
			}

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
			btnRemoveView();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void btnAddTransactionView() {
		i++;
		Bundle b = new Bundle();
		// b.putBoolean(TransactionFragment.BUNDLE_IS_NEW_TRANSACTION,
		// isTransaction);
		b.putBoolean(InvokeTransactionFragment.BUNDLE_IS_NEW_TRANSFER,
				isTransfer);
		b.putInt("INDEX", i);
		fAdapter.addFragment(new FragmentObject(new InvokeTransactionFragment(
				true, this), "Transakcja " + String.valueOf(i), b));
		setBtnEnabledState();
		mViewPager.setCurrentItem(fAdapter.getCount() - 1, true);
	}

	public void btnAddTransferView() {
		i++;
		Bundle b = new Bundle();
		// b.putBoolean(TransactionFragment.BUNDLE_IS_NEW_TRANSACTION,
		// isTransaction);
		b.putBoolean(InvokeTransactionFragment.BUNDLE_IS_NEW_TRANSFER, true);
		b.putInt("INDEX", i);
		fAdapter.addFragment(new FragmentObject(new InvokeTransactionFragment(
				true, this), "Transfer " + String.valueOf(i), b));
		setBtnEnabledState();
		mViewPager.setCurrentItem(fAdapter.getCount() - 1, true);
	}

	public void btnRemoveView() {
		int position = mViewPager.getCurrentItem();
		if ((position < 0) || (position >= fAdapter.getCount())
				|| (fAdapter.getCount() <= 1)) {

		} else {
			fAdapter.removeFragment(mViewPager.getCurrentItem());
			System.out.println("RemovedPage= "
					+ String.valueOf(mViewPager.getCurrentItem()));
		}
		setBtnEnabledState();
	}

	public void setBtnEnabledState() {
		int size = fAdapter.getCount();

		if (size > 1)
			removeViewBtn.setEnabled(true);
		else
			removeViewBtn.setEnabled(false);
	}

	@Override
	public void onChangeValue() {
		double value = 0.0;
		for (FragmentObject fragment : fragments) {
			value += ((InvokeTransactionFragment) fragment.getFragment())
					.getCurrentValue();
		}
		this.setTitle("Kwota: " + String.valueOf(value) + " z³");
	}

	private void saveTransactions() {
		ArrayList<ModelBase> transactions = new ArrayList<ModelBase>();
		String errorMessage = null;
		for (FragmentObject fragment : fragments) {
			ValidationHelper<Transaction> validation = ((InvokeTransactionFragment) fragment
					.getFragment()).getCurrentTransaction();
			if (validation.isValid())
				transactions.add(validation.getItem());
			else {
				errorMessage = validation.getErrorMessage();
				break;
			}
		}

		if (errorMessage != null) {

		} else {
			new ObjectManager(ERepositoryTypes.Transactions, this,
					ERepositoryManagerMethods.CreateAllFromList, transactions);
		}

	}

	@Override
	public void onTaskStart() {
		System.out.println("onTaskStart");
		dialog = new ProgressDialog(this);
		dialog.setMessage("Zapisywanie");
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	public void onTaskCancel() {

	}

	@Override
	public void onTaskProgress() {

	}

	@Override
	public void onTaskEnd() {
		System.out.println("onTaskEnd");
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		finish();
	}

	@Override
	public void onTaskInvalidResponse(RepositoryException exception) {
		System.out.println("onTaskInvalidResponse");
	}
}
