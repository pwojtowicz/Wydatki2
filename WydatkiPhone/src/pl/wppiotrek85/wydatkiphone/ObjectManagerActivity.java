package pl.wppiotrek85.wydatkiphone;

import java.util.ArrayList;

import pl.billennium.fragmenthelper.FragmentAdapter;
import pl.billennium.fragmenthelper.FragmentObject;
import pl.wppiotrek85.wydatkibase.entities.Account;
import pl.wppiotrek85.wydatkibase.entities.Category;
import pl.wppiotrek85.wydatkibase.entities.Parameter;
import pl.wppiotrek85.wydatkibase.entities.Project;
import pl.wppiotrek85.wydatkibase.enums.EObjectTypes;
import pl.wppiotrek85.wydatkibase.fragments.AccountFragmentList;
import pl.wppiotrek85.wydatkibase.fragments.CategoryFragmentList;
import pl.wppiotrek85.wydatkibase.fragments.ObjectBaseFragment;
import pl.wppiotrek85.wydatkibase.fragments.ParameterFragmentList;
import pl.wppiotrek85.wydatkibase.fragments.ProjectsFragmentList;
import pl.wppiotrek85.wydatkibase.interfaces.IFragmentActions;
import pl.wppiotrek85.wydatkibase.managers.DataBaseManager;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import pl.wppiotrek85.wydatkibase.units.ResultCodes;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;

public class ObjectManagerActivity extends FragmentActivity implements
		IFragmentActions {

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

		fragments.add(new FragmentObject(new AccountFragmentList(true, this,
				true), "Konta", null));
		fragments.add(new FragmentObject(new CategoryFragmentList(false, this),
				"Kategorie", null));
		fragments.add(new FragmentObject(new ParameterFragmentList(false, this,
				false, null), "Parametry", null));
		fragments.add(new FragmentObject(new ProjectsFragmentList(false, this),
				"Projekty", null));

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
		getMenuInflater().inflate(R.menu.object_list_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			refreshActualFragment();
			return true;
		case R.id.menu_edit_list:
			editListAtActualFragment();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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
		} else if (tmp.getClass() == AccountFragmentList.class) {
			b.putSerializable(EditElementActivity.BUNDLE_OBJECT_TYPE,
					EObjectTypes.Account);
			resultCode = ResultCodes.START_ACTIVITY_EDIT_ACOOUNT;
		}
		i.putExtras(b);
		startActivityForResult(i, resultCode);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ResultCodes.START_ACTIVITY_EDIT_PROJECT
				|| requestCode == ResultCodes.START_ACTIVITY_EDIT_ACOOUNT
				|| requestCode == ResultCodes.START_ACTIVITY_EDIT_CATEGORY
				|| requestCode == ResultCodes.START_ACTIVITY_EDIT_PARAMETER) {
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

	private void editListAtActualFragment() {
		ObjectBaseFragment fragment = (ObjectBaseFragment) fAdapter
				.getItem(mViewPager.getCurrentItem());
		fragment.changeEditListState();

	}

	@Override
	public void onReturnSelectedItemsIdClick() {

	}

	@Override
	public void onUpdateObject(Object item) {
		Intent i = new Intent(this, EditElementActivity.class);
		Bundle b = new Bundle();
		int resultCode = 0;
		if (item instanceof Parameter) {
			b.putSerializable(EditElementActivity.BUNDLE_OBJECT_TYPE,
					EObjectTypes.Parameter);
			resultCode = ResultCodes.START_ACTIVITY_EDIT_PARAMETER;
		} else if (item instanceof Project) {
			b.putSerializable(EditElementActivity.BUNDLE_OBJECT_TYPE,
					EObjectTypes.Project);
			resultCode = ResultCodes.START_ACTIVITY_EDIT_PROJECT;
		} else if (item instanceof Category) {
			b.putSerializable(EditElementActivity.BUNDLE_OBJECT_TYPE,
					EObjectTypes.Category);
			resultCode = ResultCodes.START_ACTIVITY_EDIT_CATEGORY;
		} else if (item instanceof Account) {
			b.putSerializable(EditElementActivity.BUNDLE_OBJECT_TYPE,
					EObjectTypes.Account);
			resultCode = ResultCodes.START_ACTIVITY_EDIT_ACOOUNT;
		}
		WydatkiGlobals.getInstance().setCurrentEditObject(item);

		i.putExtras(b);
		startActivityForResult(i, resultCode);
	}

	@Override
	public void onShowAccountTransactions(int accountId) {

	}

}
