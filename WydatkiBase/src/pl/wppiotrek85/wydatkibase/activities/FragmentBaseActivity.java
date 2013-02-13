package pl.wppiotrek85.wydatkibase.activities;

import pl.wppiotrek85.wydatkibase.exceptions.RepositoryException;
import pl.wppiotrek85.wydatkibase.interfaces.IReadRepository;
import pl.wppiotrek85.wydatkibase.managers.DataBaseManager;
import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;

public abstract class FragmentBaseActivity extends FragmentActivity implements
		IReadRepository {

	public static int BUNDLE_ACTIVITY_RESPONSE_NEED_UPADTE = 1;

	private ProgressDialog progressDialog;

	@Override
	public void onStart() {
		super.onStart();
		DataBaseManager.inicjalizeInstance(this);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onTaskStart() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("Pobieranie");
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}
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
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	@Override
	public void onTaskInvalidResponse(RepositoryException exception) {
		// TODO Auto-generated method stub

	}
}
