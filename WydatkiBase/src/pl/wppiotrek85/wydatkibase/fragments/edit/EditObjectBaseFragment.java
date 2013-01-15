package pl.wppiotrek85.wydatkibase.fragments.edit;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.fragments.ObjectBaseFragment;
import pl.wppiotrek85.wydatkibase.units.ResultCodes;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public abstract class EditObjectBaseFragment<T> extends ObjectBaseFragment {

	protected EditText etbx_name;
	protected CheckBox cbx_isActive;

	protected T currentObject;
	protected boolean isUpdate = false;

	public EditObjectBaseFragment(boolean shouldReload, T object) {
		super(shouldReload, false);
		this.currentObject = object;
		if (currentObject != null)
			isUpdate = true;
	}

	@Override
	public void OnFirtsShowFragment() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnFragmentActive() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart() {
		super.onStart();
		linkStandardViews();
		linkViews();
		configureViews();
	}

	protected void linkStandardViews() {
		etbx_name = (EditText) this.getView().findViewById(R.id.edit_etbx_name);
		cbx_isActive = (CheckBox) this.getView().findViewById(
				R.id.edit_cbx_isActive);

		Button b = (Button) this.getView().findViewById(R.id.bSave);
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				prepareToSave();
			}
		});
		b = (Button) this.getView().findViewById(R.id.bCancel);
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				leaveActivity(ResultCodes.RESULT_NO_ACTION);
			}
		});
	}

	@Override
	public void refreshFragment(boolean forceRefresh) {

	}

	@Override
	public void changeEditListState() {

	}

	@Override
	public ArrayList<Integer> getSelectedItemsList() {
		return null;
	}

	protected abstract void linkViews();

	protected abstract void configureViews();

	protected abstract void prepareToSave();

	protected abstract void saveObject();

}
