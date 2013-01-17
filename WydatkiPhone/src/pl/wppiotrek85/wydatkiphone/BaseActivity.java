package pl.wppiotrek85.wydatkiphone;

import pl.wppiotrek85.wydatkibase.managers.DataBaseManager;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {

	@Override
	public void onResume() {
		super.onResume();

		DataBaseManager.inicjalizeInstance(this);

	}

}
