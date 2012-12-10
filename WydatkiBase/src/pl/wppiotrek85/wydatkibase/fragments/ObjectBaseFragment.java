package pl.wppiotrek85.wydatkibase.fragments;

import pl.billennium.fragmenthelper.BaseFragment;
import pl.wppiotrek85.wydatkibase.exceptions.RepositoryException;
import pl.wppiotrek85.wydatkibase.interfaces.IReadRepository;
import android.app.ProgressDialog;

public abstract class ObjectBaseFragment extends BaseFragment implements
		IReadRepository {

	private ProgressDialog dialog;

	public ObjectBaseFragment(boolean shouldReload) {
		super(shouldReload);
	}

	@Override
	public void onTaskStart() {
		System.out.println("onTaskStart");
		dialog = new ProgressDialog(getActivity());
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
		System.out.println("onTaskEnd");
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}

	@Override
	public void onTaskInvalidResponse(RepositoryException exception) {
		System.out.println("onTaskInvalidResponse");

	}

}
