package pl.wppiotrek85.wydatkiphone;

import java.util.ArrayList;

import pl.billennium.fragmenthelper.FragmentAdapter;
import pl.billennium.fragmenthelper.FragmentObject;
import pl.wppiotrek85.wydatkibase.enums.EObjectTypes;
import pl.wppiotrek85.wydatkibase.fragments.AccountFragmentList;
import pl.wppiotrek85.wydatkibase.fragments.CategoryFragmentList;
import pl.wppiotrek85.wydatkibase.fragments.ObjectBaseFragment;
import pl.wppiotrek85.wydatkibase.fragments.ParameterFragmentList;
import pl.wppiotrek85.wydatkibase.fragments.ProjectsFragmentList;
import pl.wppiotrek85.wydatkibase.fragments.SettingsFragment;
import pl.wppiotrek85.wydatkibase.fragments.TransactionFragment;
import pl.wppiotrek85.wydatkibase.interfaces.IFragmentActions;
import pl.wppiotrek85.wydatkibase.managers.DataBaseManager;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import pl.wppiotrek85.wydatkibase.units.ResultCodes;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class RootActivity extends FragmentActivity implements
		ActionBar.TabListener, IFragmentActions {

	ViewPager mViewPager;

	private ProgressDialog dialog;

	private ObjectManager manager;

	private FragmentAdapter fAdapter;

	private ArrayList<FragmentObject> fragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_root);

		DataBaseManager.inicjalizeInstance(this);

		fragments = new ArrayList<FragmentObject>();

		fragments.add(new FragmentObject(new AccountFragmentList(true, false),
				"Konta", null));

		fragments.add(new FragmentObject(new SettingsFragment(true),
				"Ustawienia", null));

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
		getMenuInflater().inflate(R.menu.refresh_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			refreshActualFragment();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onAddBtnClick() {

		ObjectBaseFragment tmp = (ObjectBaseFragment) fAdapter
				.getItem(mViewPager.getCurrentItem());

		Intent i = new Intent(this, EditElementActivity.class);
		Bundle b = new Bundle();
		int resultCode = 0;
		if (tmp.getClass() == ParameterFragmentList.class) {
			b.putSerializable(EditElementActivity.BUNDLE_OBJECT_TYPE,
					EObjectTypes.Parameter);
			resultCode = ResultCodes.START_ACTIVITY_EDIT_PARAMETER;
		} else if (tmp.getClass() == ProjectsFragmentList.class) {
			b.putSerializable(EditElementActivity.BUNDLE_OBJECT_TYPE,
					EObjectTypes.Project);
			resultCode = ResultCodes.START_ACTIVITY_EDIT_PROJECT;
		} else if (tmp.getClass() == CategoryFragmentList.class) {
			b.putSerializable(EditElementActivity.BUNDLE_OBJECT_TYPE,
					EObjectTypes.Category);
			resultCode = ResultCodes.START_ACTIVITY_EDIT_CATEGORY;
		}
		i.putExtras(b);
		startActivityForResult(i, resultCode);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ResultCodes.START_ACTIVITY_EDIT_PROJECT) {
			if (resultCode == ResultCodes.RESULT_NEED_UPDATE) {
				refreshActualFragment();
			}
		}
	}

	private void refreshActualFragment() {
		ObjectBaseFragment fragment = (ObjectBaseFragment) fAdapter
				.getItem(mViewPager.getCurrentItem());
		fragment.refreshFragment(true);
	}

	public void btnSettingsClick(View v) {

	}

	public void btnUnitsClick(View v) {
		Intent i = new Intent(this, ObjectManagerActivity.class);
		startActivity(i);
	}

	public void btnNewTransaction_Click(View v) {
		Intent intent = new Intent(this, TransactionActivity.class);
		intent.putExtra(TransactionFragment.BUNDLE_IS_NEW_TRANSACTION, true);
		startActivity(intent);
	}

	public void btnNewTransfer_Click(View v) {
		Intent intent = new Intent(this, TransactionActivity.class);
		intent.putExtra(TransactionFragment.BUNDLE_IS_NEW_TRANSFER, true);
		startActivity(intent);
	}

	@Override
	public void onReturnSelectedItemsIdClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpdateObject(Object item) {
		// TODO Auto-generated method stub

	}

}
