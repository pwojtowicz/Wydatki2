package pl.wppiotrek85.wydatkiphone;

import java.util.ArrayList;

import pl.billennium.fragmenthelper.FragmentAdapter;
import pl.billennium.fragmenthelper.FragmentObject;
import pl.wppiotrek85.wydatkibase.fragments.TransactionFragment;
import pl.wppiotrek85.wydatkibase.interfaces.ITransactionListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

public class TransactionActivity extends FragmentActivity implements
		ITransactionListener {

	ViewPager mViewPager;

	private ArrayList<FragmentObject> fragments;

	private FragmentAdapter fAdapter;
	int i = 1;
	// boolean isTransaction = false;
	boolean isTransfer = false;

	private ImageButton removeViewBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction);

		Bundle bundle = getIntent().getExtras();

		if (bundle != null) {
			// isTransaction = bundle.getBoolean(
			// TransactionFragment.BUNDLE_IS_NEW_TRANSACTION, false);
			isTransfer = bundle.getBoolean(
					TransactionFragment.BUNDLE_IS_NEW_TRANSFER, false);
		}

		fragments = new ArrayList<FragmentObject>();

		Bundle b = new Bundle();
		// b.putBoolean(TransactionFragment.BUNDLE_IS_NEW_TRANSACTION,
		// isTransaction);
		b.putBoolean(TransactionFragment.BUNDLE_IS_NEW_TRANSFER, isTransfer);
		b.putInt("INDEX", i);

		fragments.add(new FragmentObject(new TransactionFragment(true, this),
				"Transakcja " + String.valueOf(i), b));

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
				case R.id.menu_add:
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
			return true;
		case R.id.menu_add:
			btnAddView();
			return true;
		case R.id.menu_delete:
			btnRemoveView();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void btnAddView() {
		i++;
		Bundle b = new Bundle();
		// b.putBoolean(TransactionFragment.BUNDLE_IS_NEW_TRANSACTION,
		// isTransaction);
		b.putBoolean(TransactionFragment.BUNDLE_IS_NEW_TRANSFER, isTransfer);
		b.putInt("INDEX", i);
		fAdapter.addFragment(new FragmentObject(new TransactionFragment(true,
				this), "Transakcja " + String.valueOf(i), b));
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
			value += ((TransactionFragment) fragment.getFragment())
					.getCurrentValue();
		}
		this.setTitle("Kwota: " + String.valueOf(value) + " z³");
	}
}
