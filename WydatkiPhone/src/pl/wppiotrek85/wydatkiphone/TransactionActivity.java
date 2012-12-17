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
import android.view.View;
import android.widget.ImageButton;

public class TransactionActivity extends FragmentActivity implements
		ITransactionListener {

	ViewPager mViewPager;

	private ArrayList<FragmentObject> fragments;

	private FragmentAdapter fAdapter;
	int i = 1;

	private ImageButton removeViewBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction);

		fragments = new ArrayList<FragmentObject>();

		Bundle b = new Bundle();
		b.putBoolean(TransactionFragment.BUNDLE_IS_NEW_TRANSACTION, true);
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
		return true;
	}

	public void btnAddView_Click(View view) {
		i++;
		Bundle b = new Bundle();
		b.putBoolean(TransactionFragment.BUNDLE_IS_NEW_TRANSACTION, true);
		b.putInt("INDEX", i);
		fAdapter.addFragment(new FragmentObject(new TransactionFragment(true,
				this), "Transakcja " + String.valueOf(i), b));
		setBtnEnabledState();
		mViewPager.setCurrentItem(fAdapter.getCount() - 1, true);
	}

	public void btnRemoveView_Click(View view) {
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
	public void onChangeValue(double value) {
		// TODO Auto-generated method stub

	}
}
