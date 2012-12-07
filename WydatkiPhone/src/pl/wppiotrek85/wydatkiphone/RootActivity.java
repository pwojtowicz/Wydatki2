package pl.wppiotrek85.wydatkiphone;

import java.util.ArrayList;

import pl.billennium.fragmenthelper.FragmentAdapter;
import pl.billennium.fragmenthelper.FragmentObject;
import pl.wppiotrek85.wydatkibase.fragments.AccountFragmentList;
import pl.wppiotrek85.wydatkibase.fragments.CategoryFragmentList;
import pl.wppiotrek85.wydatkibase.fragments.ParameterFragmentList;
import pl.wppiotrek85.wydatkibase.fragments.ProjectsFragmentList;
import pl.wppiotrek85.wydatkibase.managers.DataBaseManager;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;

public class RootActivity extends FragmentActivity implements
		ActionBar.TabListener {

	ViewPager mViewPager;

	private ProgressDialog dialog;

	private ObjectManager manager;

	private FragmentAdapter fAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_root);

		DataBaseManager.inicjalizeInstance(this);

		ArrayList<FragmentObject> fragments = new ArrayList<FragmentObject>();

		fragments.add(new FragmentObject(new AccountFragmentList(true),
				"Konta", null));
		fragments.add(new FragmentObject(new CategoryFragmentList(false),
				"Kategorie", null));
		fragments.add(new FragmentObject(new ParameterFragmentList(false),
				"Parametry", null));
		fragments.add(new FragmentObject(new ProjectsFragmentList(false),
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

		getMenuInflater().inflate(R.menu.activity_root, menu);
		return true;
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

	// private void loadAccounts() {
	// System.out.println("loadAccounts");

	//
	// Account a = new Account(1, "Konto ING", 100.12, true);
	// a.setIsSumInGlobalBalance(true);
	// a.setLastActionDate(new Date());
	// a.setImageIndex((byte) 1);
	//
	// // manager = new ObjectManager(ERepositoryTypes.Acoounts,
	// // this, ERepositoryManagerMethods.Create, a);
	//
	// Category category = new Category(-1, "Kat1", true);
	// category.setIsPositive(true);
	// category.setParentId(0);
	//
	// Parameter[] parameters = new Parameter[2];
	// parameters[0] = new Parameter(1, "1");
	// parameters[1] = new Parameter(2, "2");
	//
	// category.setAttributes(parameters);
	//
	// manager = new ObjectManager(ERepositoryTypes.Categories, this,
	// ERepositoryManagerMethods.Create, category);
	// }
	//
	// @Override
	// public void onTaskCancel() {
	// System.out.println("onTaskCancel");
	// }
	//
	// @Override
	// public void onTaskProgress() {
	// System.out.println("onTaskProgress");
	// }
	//
	// @Override
	// public void onTaskEnd() {
	// System.out.println("onTaskEnd");
	// if (dialog != null) {
	// dialog.dismiss();
	// dialog = null;
	// }
	// }
	//
	// @Override
	// public void onTaskInvalidResponse(RepositoryException exception) {
	// System.out.println("onTaskInvalidResponse");
	// }
	//
	// @Override
	// public void onTaskStart() {
	// System.out.println("onTaskStart");
	// dialog = new ProgressDialog(this);
	// dialog.setMessage("Pobieranie");
	// dialog.setIndeterminate(true);
	// dialog.show();
	// }
	//
	// @Override
	// public void onTaskResponse(AsyncTaskResult response) {
	// System.out.println("onTaskResponse");
	// if (response.bundle instanceof Account) {
	//
	// } else if (response.bundle instanceof Account[]) {
	//
	// }
	//
	// }

}
