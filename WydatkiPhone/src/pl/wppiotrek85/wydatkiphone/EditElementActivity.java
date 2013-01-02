package pl.wppiotrek85.wydatkiphone;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.entities.Account;
import pl.wppiotrek85.wydatkibase.entities.Category;
import pl.wppiotrek85.wydatkibase.entities.ModelBase;
import pl.wppiotrek85.wydatkibase.entities.Parameter;
import pl.wppiotrek85.wydatkibase.entities.Project;
import pl.wppiotrek85.wydatkibase.enums.EObjectTypes;
import pl.wppiotrek85.wydatkibase.fragments.ObjectBaseFragment;
import pl.wppiotrek85.wydatkibase.fragments.edit.EditAccountFragment;
import pl.wppiotrek85.wydatkibase.fragments.edit.EditCategoryFragment;
import pl.wppiotrek85.wydatkibase.fragments.edit.EditParameterFragment;
import pl.wppiotrek85.wydatkibase.fragments.edit.EditProjectFragment;
import pl.wppiotrek85.wydatkibase.interfaces.IEditCategoryActions;
import pl.wppiotrek85.wydatkibase.support.ListSupport;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import pl.wppiotrek85.wydatkibase.units.ResultCodes;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class EditElementActivity extends FragmentActivity implements
		IEditCategoryActions {

	public static final String BUNDLE_OBJECT_TYPE = "Type";
	public static final String BUNDLE_OBJECT = "Object";
	private ModelBase currentObject;
	ObjectBaseFragment details = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_element_activity_layout);

		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {

			EObjectTypes type = (EObjectTypes) bundle
					.getSerializable(BUNDLE_OBJECT_TYPE);

			Object o = WydatkiGlobals.getInstance().getCurrentEditObject();

			switch (type) {
			case Category:
				details = new EditCategoryFragment(false, (o != null
						&& o instanceof Category ? (Category) o : null), this);
				break;
			case Parameter:
				details = new EditParameterFragment(false, (o != null
						&& o instanceof Parameter ? (Parameter) o : null));
				break;
			case Project:
				details = new EditProjectFragment(false, (o != null
						&& o instanceof Project ? (Project) o : null));
				break;
			case Account:
				details = new EditAccountFragment(false, (o != null
						&& o instanceof Account ? (Account) o : null));
				break;
			default:
				break;
			}
			WydatkiGlobals.getInstance().setCurrentEditObject(null);

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
	public void showParametersForCategory(Category currentObject) {
		this.currentObject = currentObject;
		Intent intent = new Intent(this, SingleObjectTyleList.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(SingleObjectTyleList.BUNDLE_OBJECT_TYPE,
				EObjectTypes.Parameter);

		bundle.putBoolean(ObjectBaseFragment.BUNDLE_ISCHECKABLE, true);
		bundle.putBoolean(
				SingleObjectTyleList.BUNDLE_SELECT_PARAMETER_FOR_CATEGORY, true);

		if (currentObject.getAttributes() != null) {
			bundle.putString(
					SingleObjectTyleList.BUNDLE_SELECTED_PARAMETERS_FOR_CATEGORY,
					ListSupport.ArrayToString(currentObject.getAttributes()));
		}

		intent.putExtras(bundle);
		startActivityForResult(intent,
				ResultCodes.START_ACTIVITY_LIST_PARAMETER);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == ResultCodes.START_ACTIVITY_LIST_PARAMETER) {
				if (currentObject != null) {
					String items = data
							.getStringExtra(EditCategoryFragment.BUNDLE_SELECTED_PARAMETERS);
					ArrayList<Integer> itemsArr = ListSupport
							.StringToIntegerArrayList(items);

					int size = itemsArr.size();
					Parameter[] parameters = new Parameter[size];
					for (int i = 0; i < size; i++) {
						Parameter p = new Parameter();
						p.setId(itemsArr.get(i));
						parameters[i] = p;
					}

					if (details != null) {
						((EditCategoryFragment) details)
								.setParameters(parameters);
					}
				}
			}
		}
	}
}
