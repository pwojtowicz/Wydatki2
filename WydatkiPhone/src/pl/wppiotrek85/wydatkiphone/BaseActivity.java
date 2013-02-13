package pl.wppiotrek85.wydatkiphone;

import pl.wppiotrek85.wydatkibase.managers.DataBaseManager;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {

	public static int BUNDLE_ACTIVITY_RESPONSE_NEED_UPADTE = 1;

	@Override
	public void onStart() {
		super.onStart();
		DataBaseManager.inicjalizeInstance(this);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

}
