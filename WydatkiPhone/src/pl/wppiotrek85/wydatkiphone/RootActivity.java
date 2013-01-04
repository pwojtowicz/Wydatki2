package pl.wppiotrek85.wydatkiphone;

import java.util.ArrayList;

import pl.billennium.fragmenthelper.FragmentAdapter;
import pl.billennium.fragmenthelper.FragmentObject;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.entities.Category;
import pl.wppiotrek85.wydatkibase.entities.Parameter;
import pl.wppiotrek85.wydatkibase.entities.Project;
import pl.wppiotrek85.wydatkibase.enums.EObjectTypes;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryManagerMethods;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryTypes;
import pl.wppiotrek85.wydatkibase.exceptions.RepositoryException;
import pl.wppiotrek85.wydatkibase.fragments.AccountFragmentList;
import pl.wppiotrek85.wydatkibase.fragments.CategoryFragmentList;
import pl.wppiotrek85.wydatkibase.fragments.ObjectBaseFragment;
import pl.wppiotrek85.wydatkibase.fragments.ParameterFragmentList;
import pl.wppiotrek85.wydatkibase.fragments.ProjectsFragmentList;
import pl.wppiotrek85.wydatkibase.fragments.SettingsFragment;
import pl.wppiotrek85.wydatkibase.fragments.TransactionFragment;
import pl.wppiotrek85.wydatkibase.fragments.TransactionsFragmentList;
import pl.wppiotrek85.wydatkibase.interfaces.IFragmentActions;
import pl.wppiotrek85.wydatkibase.interfaces.IReadRepository;
import pl.wppiotrek85.wydatkibase.managers.DataBaseManager;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
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
		ActionBar.TabListener, IFragmentActions, IReadRepository {

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
				false), "Konta", null));

		fragments.add(new FragmentObject(new TransactionsFragmentList(true,
				false, 0), "Transakcje", null));

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

		loadObjects();
	}

	private void loadObjects() {
		new ObjectManager(ERepositoryTypes.Categories, this,
				ERepositoryManagerMethods.ReadAll);
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
		// intent.putExtra(TransactionFragment.BUNDLE_IS_NEW_TRANSACTION, true);
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

	@Override
	public void onTaskStart() {
		dialog = new ProgressDialog(this);
		dialog.setMessage("Pobieranie");
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	public void onTaskCancel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTaskProgress() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTaskEnd() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		if (response.bundle instanceof ArrayList<?>) {
			if (((ArrayList<Object>) response.bundle).size() > 0) {
				Object o = ((ArrayList<Object>) response.bundle).get(0);
				if (o instanceof Category) {
					ArrayList<Category> list = (ArrayList<Category>) response.bundle;
					WydatkiGlobals.getInstance().setCategories(list);

					new ObjectManager(ERepositoryTypes.Parameters, this,
							ERepositoryManagerMethods.ReadAll);
				} else if (o instanceof Parameter) {
					ArrayList<Parameter> list = (ArrayList<Parameter>) response.bundle;
					WydatkiGlobals.getInstance().setParameters(list);

					new ObjectManager(ERepositoryTypes.Projects, this,
							ERepositoryManagerMethods.ReadAll);
				} else if (o instanceof Project) {
					ArrayList<Project> list = (ArrayList<Project>) response.bundle;
					WydatkiGlobals.getInstance().setProjects(list);
				}
			}
		}
	}

	@Override
	public void onTaskInvalidResponse(RepositoryException exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onShowAccountTransactions(int accountId) {
		Intent intent = new Intent(this, SingleObjectTyleList.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(SingleObjectTyleList.BUNDLE_OBJECT_TYPE,
				EObjectTypes.Transactions);
		bundle.putInt(SingleObjectTyleList.BUNDLE_ACCOUNT_ID, accountId);
		intent.putExtras(bundle);

		startActivity(intent);
	}

}
