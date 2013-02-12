package pl.wppiotrek85.wydatkibase.newfragments;

import pl.wppiotrek85.wydatkibase.fragmentactions.IFragmentActions;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

	private int viewResourceId;
	private View convertView;
	private Context context;

	protected IFragmentActions actions;

	public BaseFragment(int viewResourceId) {
		this.viewResourceId = viewResourceId;
	}

	public void setIFragmentActions(IFragmentActions actions) {
		this.actions = actions;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		convertView = inflater.inflate(viewResourceId, null);
		linkOtherView();
		return convertView;
	}

	@Override
	public void onResume() {
		super.onResume();
		configureView();
	}

	protected abstract void linkOtherView();

	protected abstract void configureView();

	public abstract void reload(boolean forceReload);

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (getActivity() != null) {
			this.context = getActivity();
		}
		reload(false);
	}

	protected View getCurrentView() {
		return convertView;
	}

	protected Context getCurrentContext() {
		return context;
	}

}
