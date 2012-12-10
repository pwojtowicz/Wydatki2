package pl.wppiotrek85.wydatkiphone;

import pl.wppiotrek85.wydatkibase.enums.EObjectTypes;
import pl.wppiotrek85.wydatkibase.fragments.ObjectBaseFragment;
import pl.wppiotrek85.wydatkibase.fragments.edit.EditAccountFragment;
import pl.wppiotrek85.wydatkibase.fragments.edit.EditCategoryFragment;
import pl.wppiotrek85.wydatkibase.fragments.edit.EditParameterFragment;
import pl.wppiotrek85.wydatkibase.fragments.edit.EditProjectFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class EditElementActivity extends FragmentActivity {

	public static final String BUNDLE_OBJECT_TYPE = "Type";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_element_activity_layout);

		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {
			ObjectBaseFragment details = null;
			EObjectTypes type = (EObjectTypes) bundle
					.getSerializable(BUNDLE_OBJECT_TYPE);

			switch (type) {
			case Category:
				details = new EditCategoryFragment(false);
				break;
			case Parameter:
				details = new EditParameterFragment(false);
				break;
			case Project:
				details = new EditProjectFragment(false);
				break;
			case Account:
				details = new EditAccountFragment(false);
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
}
