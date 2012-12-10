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
import pl.wppiotrek85.wydatkibase.interfaces.IFragmentActions;
import pl.wppiotrek85.wydatkibase.managers.DataBaseManager;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;

public class RootActivity extends FragmentActivity implements
		ActionBar.TabListener, IFragmentActions {

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
		fragments.add(new FragmentObject(new CategoryFragmentList(false, this),
				"Kategorie", null));
		fragments.add(new FragmentObject(
				new ParameterFragmentList(false, this), "Parametry", null));
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

	@Override
	public void onAddBtnClick() {

		ObjectBaseFragment tmp = (ObjectBaseFragment) fAdapter
				.getItem(mViewPager.getCurrentItem());

		Intent i = new Intent(this, EditElementActivity.class);
		Bundle b = new Bundle();
		if (tmp.getClass() == ParameterFragmentList.class) {
			b.putSerializable(EditElementActivity.BUNDLE_OBJECT_TYPE,
					EObjectTypes.Parameter);
		} else if (tmp.getClass() == ProjectsFragmentList.class) {
			b.putSerializable(EditElementActivity.BUNDLE_OBJECT_TYPE,
					EObjectTypes.Project);
		} else if (tmp.getClass() == CategoryFragmentList.class) {
			b.putSerializable(EditElementActivity.BUNDLE_OBJECT_TYPE,
					EObjectTypes.Category);
		}
		i.putExtras(b);
		startActivity(i);
	}

}
