package pl.wppiotrek85.wydatkibase.fragmentsnew;

import pl.wppiotrek85.wydatkibase.exceptions.RepositoryException;
import pl.wppiotrek85.wydatkibase.interfaces.IReadRepository;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class NewBaseFragment extends Fragment implements
		IReadRepository {

	protected ProgressDialog dialog;
	protected ObjectManager manager;
	private int resourceLayout;

	public NewBaseFragment(int resourceLayout) {
		this.resourceLayout = resourceLayout;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(resourceLayout, container, false);
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
