package pl.wppiotrek85.wydatkibase.fragments;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.adapters.BaseObjectAdapter;
import pl.wppiotrek85.wydatkibase.entities.ModelBase;
import pl.wppiotrek85.wydatkibase.enums.DialogStyle;
import pl.wppiotrek85.wydatkibase.enums.EActionBarDialogType;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryManagerMethods;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryTypes;
import pl.wppiotrek85.wydatkibase.interfaces.IDialogButtonActions;
import pl.wppiotrek85.wydatkibase.interfaces.IFragmentActions;
import pl.wppiotrek85.wydatkibase.interfaces.IOnAdapterCheckboxClick;
import pl.wppiotrek85.wydatkibase.managers.DialogFactoryManager;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

public abstract class ObjectListBaseFragment<T> extends ObjectBaseFragment
		implements OnItemClickListener, IOnAdapterCheckboxClick,
		OnItemLongClickListener, IDialogButtonActions {

	protected ListView objectListView;
	protected ProgressDialog dialog;
	protected ObjectManager manager;
	protected BaseObjectAdapter<T> adapter;
	protected IFragmentActions actions;

	protected RelativeLayout actionBar;
	protected String selectedItems = "";
	private ERepositoryTypes repositoryType;
	protected Button btn_add;
	private int resources;
	private EActionBarDialogType currentDialogType;

	public ObjectListBaseFragment(int resources,
			ERepositoryTypes repositoryType, boolean shouldReload,
			Boolean isChecakble) {
		super(shouldReload, isChecakble);
		this.resources = resources;
		this.repositoryType = repositoryType;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View convertView = inflater.inflate(this.resources, null);
		loadStandartObjects(convertView);
		linkViews(convertView);
		return convertView;
	}

	@Override
	public void OnFirtsShowFragment() {
		refreshFragment(true);
	}

	@Override
	public void OnFragmentActive() {

	}

	protected void loadStandartObjects(View convertView) {
		actionBar = (RelativeLayout) convertView
				.findViewById(R.id.bottom_actionbar);

		if (actionBar != null) {
			((ImageButton) actionBar
					.findViewById(R.id.actionbar_btn_returnSelected))
					.setVisibility(ImageButton.GONE);

			ImageButton btn_edit = (ImageButton) actionBar
					.findViewById(R.id.actionbar_btn_edit);

			btn_edit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					editBtnClick();
				}
			});

			ImageButton btn_lock = (ImageButton) actionBar
					.findViewById(R.id.actionbar_btn_lock);

			btn_lock.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					lockBtnClick();
				}
			});

			ImageButton btn_delete = (ImageButton) actionBar
					.findViewById(R.id.actionbar_btn_delete);

			btn_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					deleteBtnClick();
				}
			});

		}

		objectListView = (ListView) convertView.findViewById(R.id.listview);

		objectListView.setOnItemClickListener(this);
		objectListView.setOnItemLongClickListener(this);

		if (adapter != null)
			objectListView.setAdapter(adapter);

		btn_add = (Button) convertView.findViewById(R.id.btn_add_new);
		if (btn_add != null)
			btn_add.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (actions != null) {
						actions.onAddBtnClick();
					}
				}
			});
	}

	protected void deleteBtnClick() {
		currentDialogType = EActionBarDialogType.Delete;
		DialogFactoryManager.create(DialogStyle.Question, getActivity(), null,
				"Czy napewno skasowaæ zaznaczone elementy?", this).show();
	}

	protected void lockBtnClick() {
		currentDialogType = EActionBarDialogType.Lock;
		DialogFactoryManager.create(DialogStyle.Question, getActivity(), null,
				"Czy napewno zablokowaæ/obdlokowaæ zaznaczone elementy?", this)
				.show();
	}

	protected void editBtnClick() {
		currentDialogType = EActionBarDialogType.Edit;

	}

	@Override
	public void refreshFragment(boolean forceRefresh) {
		if (!forceRefresh) {
			ArrayList<T> list = getObjectList();
			if (list == null)
				forceRefresh = true;
			else {
				if (adapter == null)
					adapter = getAdapter(getActivity(), list, null);
				else
					adapter.reloadItems(list);
				objectListView.setAdapter(adapter);
			}
		}

		if (forceRefresh) {
			getAllItems();
		}
		afterRefreshAction();
	}

	public void getAllItems() {
		this.manager = new ObjectManager(repositoryType, this,
				ERepositoryManagerMethods.ReadAll);
	}

	public abstract void afterRefreshAction();

	public abstract void linkViews(View convertView);

	public abstract BaseObjectAdapter<T> getAdapter(FragmentActivity activity,
			ArrayList<T> list, IOnAdapterCheckboxClick object);

	public abstract ArrayList<T> getObjectList();

	@Override
	public ArrayList<Integer> getSelectedItemsList() {
		if (adapter != null) {
			return adapter.getSelectedItemsList();
		}
		return null;
	}

	@Override
	public void changeEditListState() {
		super.isChecakble = !super.isChecakble;

		if (!this.isChecakble) {
			selectedItems = "";
			OnCheckBoxSelected(0);
		}
		adapter.setCheckableState(this, super.isChecakble);
	}

	@Override
	public void OnCheckBoxSelected(int size) {
		if (actionBar != null)

			if (this instanceof TransactionsFragmentList) {
				((ImageButton) actionBar.findViewById(R.id.actionbar_btn_lock))
						.setVisibility(ImageButton.GONE);
			}

		if (size > 0) {
			actionBar.setVisibility(LinearLayout.VISIBLE);
			if (size == 1)
				((ImageButton) actionBar.findViewById(R.id.actionbar_btn_edit))
						.setEnabled(true);
			else
				((ImageButton) actionBar.findViewById(R.id.actionbar_btn_edit))
						.setEnabled(false);
		} else
			actionBar.setVisibility(LinearLayout.GONE);
	}

	@Override
	public void onPositiveClick() {
		ArrayList<ModelBase> items = new ArrayList<ModelBase>();
		for (Integer value : getSelectedItemsList()) {
			items.add(new ModelBase(value));
		}

		switch (currentDialogType) {
		case Delete:
			deleteSelectedItems(items);
			break;
		case Lock:
			lockSelectedItems(items);
			break;
		default:
			break;
		}
	}

	@Override
	public void onNegativeClick() {

	}

	protected void deleteSelectedItems(ArrayList<ModelBase> items) {
		new ObjectManager(repositoryType, this,
				ERepositoryManagerMethods.Delete, items);
	}

	protected void lockSelectedItems(ArrayList<ModelBase> items) {
		new ObjectManager(repositoryType, this, ERepositoryManagerMethods.Lock,
				items);

	}

}
