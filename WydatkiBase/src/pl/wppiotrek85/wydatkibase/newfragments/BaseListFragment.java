package pl.wppiotrek85.wydatkibase.newfragments;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.adapters.BaseObjectAdapter;
import pl.wppiotrek85.wydatkibase.interfaces.IOnAdapterCheckboxClick;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public abstract class BaseListFragment<T> extends BaseFragment {

	protected ObjectManager manager;
	protected BaseObjectAdapter<T> adapter;
	protected ListView objectListView;

	private boolean canAddNewItem = false;
	private Button btn_add;

	public BaseListFragment(int viewResourceId) {
		super(viewResourceId);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View convertView = super.onCreateView(inflater, container,
				savedInstanceState);
		loadStandartObjects(getCurrentView());
		return convertView;
	}

	protected void loadStandartObjects(View convertView) {
		// actionBar = (RelativeLayout) convertView
		// .findViewById(R.id.bottom_actionbar);
		//
		// if (actionBar != null) {
		// ((ImageButton) actionBar
		// .findViewById(R.id.actionbar_btn_returnSelected))
		// .setVisibility(ImageButton.GONE);
		//
		// ImageButton btn_edit = (ImageButton) actionBar
		// .findViewById(R.id.actionbar_btn_edit);
		//
		// btn_edit.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// editBtnClick();
		// }
		// });
		//
		// ImageButton btn_lock = (ImageButton) actionBar
		// .findViewById(R.id.actionbar_btn_lock);
		//
		// btn_lock.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// lockBtnClick();
		// }
		// });
		//
		// ImageButton btn_delete = (ImageButton) actionBar
		// .findViewById(R.id.actionbar_btn_delete);
		//
		// btn_delete.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// deleteBtnClick();
		// }
		// });
		//
		// }

		objectListView = (ListView) convertView.findViewById(R.id.listview);

		// objectListView.setOnItemClickListener(this);
		// objectListView.setOnItemLongClickListener(this);

		if (adapter != null)
			objectListView.setAdapter(adapter);

		btn_add = (Button) convertView.findViewById(R.id.btn_add_new);

		if (btn_add != null)
			if (canAddNewItem) {
				btn_add.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (actions != null) {
							actions.onAddBtnClick();
						}
					}
				});
			} else {
				btn_add.setVisibility(Button.GONE);
			}
	}

	@Override
	public void reload(boolean forceReload) {
		if (!forceReload) {
			ArrayList<T> list = getObjectList();
			if (list == null)
				forceReload = true;
			else {
				if (adapter == null)
					adapter = getAdapter(getActivity(), list, null);
				else
					adapter.reloadItems(list);
				objectListView.setAdapter(adapter);
			}
		}
		afterReloadAction();
	}

	public abstract ArrayList<T> getObjectList();

	public abstract void afterReloadAction();

	public abstract BaseObjectAdapter<T> getAdapter(FragmentActivity activity,
			ArrayList<T> list, IOnAdapterCheckboxClick object);

}
