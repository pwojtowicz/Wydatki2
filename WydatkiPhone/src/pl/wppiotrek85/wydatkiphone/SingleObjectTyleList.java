package pl.wppiotrek85.wydatkiphone;

import pl.wppiotrek85.wydatkibase.enums.EObjectTypes;
import pl.wppiotrek85.wydatkibase.fragments.ObjectBaseFragment;
import pl.wppiotrek85.wydatkibase.fragments.ParameterFragmentList;
import pl.wppiotrek85.wydatkibase.fragments.edit.EditCategoryFragment;
import pl.wppiotrek85.wydatkibase.interfaces.IFragmentActions;
import pl.wppiotrek85.wydatkibase.support.ListSupport;
import pl.wppiotrek85.wydatkibase.units.ResultCodes;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class SingleObjectTyleList extends FragmentActivity implements
		IFragmentActions {

	public static final String BUNDLE_OBJECT_TYPE = "ObjectType";
	public static final String BUNDLE_SELECTED_PARAMETERS_FOR_CATEGORY = "1";
	public static final String BUNDLE_SELECT_PARAMETER_FOR_CATEGORY = "2";
	ObjectBaseFragment details = null;
	private boolean isSelectedForCategory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_object_tyle_list);

		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {

			EObjectTypes type = (EObjectTypes) bundle
					.getSerializable(BUNDLE_OBJECT_TYPE);

			boolean isChecakble = bundle.getBoolean(
					ObjectBaseFragment.BUNDLE_ISCHECKABLE, false);

			isSelectedForCategory = bundle.getBoolean(
					BUNDLE_SELECT_PARAMETER_FOR_CATEGORY, false);

			switch (type) {
			case Parameter:
				details = new ParameterFragmentList(
						true,
						this,
						isChecakble,
						bundle.getString(BUNDLE_SELECTED_PARAMETERS_FOR_CATEGORY),
						true);
				break;
			default:
				break;
			}

			if (details != null) {
				FragmentTransaction ft = getSupportFragmentManager()
						.beginTransaction();
				ft.replace(R.id.details, details);

				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater()
				.inflate(R.menu.activity_single_object_tyle_list, menu);
		return true;
	}

	@Override
	public void onAddBtnClick() {

		Intent i = new Intent(this, EditElementActivity.class);
		Bundle b = new Bundle();
		int resultCode = 0;
		if (details.getClass() == ParameterFragmentList.class) {
			b.putSerializable(EditElementActivity.BUNDLE_OBJECT_TYPE,
					EObjectTypes.Parameter);
			resultCode = ResultCodes.START_ACTIVITY_EDIT_PARAMETER;
		}
		// } else if (details.getClass() == ProjectsFragmentList.class) {
		// b.putSerializable(EditElementActivity.BUNDLE_OBJECT_TYPE,
		// EObjectTypes.Project);
		// resultCode = ResultCodes.START_ACTIVITY_EDIT_PROJECT;
		// } else if (details.getClass() == CategoryFragmentList.class) {
		// b.putSerializable(EditElementActivity.BUNDLE_OBJECT_TYPE,
		// EObjectTypes.Category);
		// resultCode = ResultCodes.START_ACTIVITY_EDIT_CATEGORY;
		// }
		i.putExtras(b);
		startActivityForResult(i, resultCode);
	}

	@Override
	public void onReturnSelectedItemsIdClick() {
		if (isSelectedForCategory) {
			Intent intent = this.getIntent();
			this.setResult(RESULT_OK, intent);
			String items = ListSupport.ArrayListIntegerToString(details
					.getSelectedItemsList());
			intent.putExtra(EditCategoryFragment.BUNDLE_SELECTED_PARAMETERS,
					items.toString());
		}
		finish();
	}

	@Override
	public void onUpdateObject(Object item) {
		// TODO Auto-generated method stub

	}

}
