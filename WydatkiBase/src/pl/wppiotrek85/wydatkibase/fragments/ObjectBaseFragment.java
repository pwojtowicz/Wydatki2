package pl.wppiotrek85.wydatkibase.fragments;

import java.util.ArrayList;

import pl.billennium.fragmenthelper.BaseFragment;
import pl.wppiotrek85.wydatkibase.exceptions.RepositoryException;
import pl.wppiotrek85.wydatkibase.interfaces.IReadRepository;
import android.app.ProgressDialog;
import android.content.Intent;

public abstract class ObjectBaseFragment extends BaseFragment implements
		IReadRepository {

	public static final String BUNDLE_ISCHECKABLE = "isCheckable";

	private ProgressDialog dialog;
	protected Boolean isChecakble = false;

	public ObjectBaseFragment(boolean shouldReload, Boolean isChecakble) {
		super(shouldReload);
		this.isChecakble = isChecakble;
	}

	public void leaveActivity(int requestCode) {
		Intent intent = getActivity().getIntent();
		getActivity().setResult(requestCode, intent);
		getActivity().finish();

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

	public abstract void refreshFragment(boolean forceRefresh);

	public abstract ArrayList<Integer> getSelectedItemsList();

}
