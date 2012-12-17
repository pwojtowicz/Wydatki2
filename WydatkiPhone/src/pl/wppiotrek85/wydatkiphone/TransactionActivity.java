package pl.wppiotrek85.wydatkiphone;

import java.util.ArrayList;

import pl.billennium.fragmenthelper.FragmentAdapter;
import pl.billennium.fragmenthelper.FragmentObject;
import pl.wppiotrek85.wydatkibase.fragments.TransactionFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;

public class TransactionActivity extends FragmentActivity {

	ViewPager mViewPager;

	private ArrayList<FragmentObject> fragments;

	private FragmentAdapter fAdapter;
	int i = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction);

		fragments = new ArrayList<FragmentObject>();

		fragments.add(new FragmentObject(new TransactionFragment(true),
				"Transakcja " + String.valueOf(i), null));

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

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_transaction, menu);
		return true;
	}

	public void btnAddView_Click(View view) {
		i++;
		fAdapter.addFragment(new FragmentObject(new TransactionFragment(true),
				"Transakcja " + String.valueOf(i), null));
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

	}

}
