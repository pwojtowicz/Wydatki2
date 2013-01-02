package pl.wppiotrek85.wydatkibase.views;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.entities.InvokeTransactionParameter;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class InvokeCheckBoxView extends InvokeBaseView {

	private CheckBox field;

	public InvokeCheckBoxView(Context context, View view,
			InvokeTransactionParameter content) {
		super(context, view, content);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void linkViews() {
		field = (CheckBox) view.findViewById(R.id.invoke_action_check_box_cbx);

		field.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				value = String.valueOf(isChecked);
				saveValue();
			}
		});
	}

	@Override
	public void fillViews() {
		if (content != null) {
			if (field != null)
				field.setText(content.getName());
		}

	}

	@Override
	protected void clean() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDataSource() {

	}

	@Override
	public void setDefaultValue() {
		boolean isChecked = content.getDefaultValue() != null ? Boolean
				.valueOf(content.getDefaultValue().toString()) : false;

		if (content.getValue() != null) {
			isChecked = Boolean.parseBoolean(content.getValue());
		}

		field.setChecked(isChecked);
	}

	@Override
	protected void saveValue() {
		save();
	}

}
