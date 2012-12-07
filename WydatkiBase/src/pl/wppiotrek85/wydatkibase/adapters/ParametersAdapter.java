package pl.wppiotrek85.wydatkibase.adapters;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.entities.Parameter;
import pl.wppiotrek85.wydatkibase.units.ParameterTypes;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class ParametersAdapter extends BaseObjectAdapter<Parameter> {

	public ParametersAdapter(Context context, ArrayList<Parameter> items) {
		super(context, items);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.row_parameter_layout, null);

		fillRow(convertView, getItem(position));
		return convertView;
	}

	private void fillRow(View convertView, Object o) {
		Parameter item = (Parameter) o;
		if (item != null) {
			TextView name = (TextView) convertView
					.findViewById(R.id.row_parameter_name);
			TextView type = (TextView) convertView
					.findViewById(R.id.row_parameter_type);
			TextView value = (TextView) convertView
					.findViewById(R.id.row_parameter_default);

			ImageView lock = (ImageView) convertView
					.findViewById(R.id.row_parameter_lock);

			CheckBox cbx_selected = (CheckBox) convertView
					.findViewById(R.id.row_cbx_selected);
			cbx_selected.setOnClickListener(new OnClickListener() {

				public void onClick(View view) {
					// int itemId = (Integer) view.getTag();
					// if (((CheckBox) view).isChecked())
					// selectedItemsId.add(itemId);
					// else {
					// boolean tmp = selectedItemsId.remove((Integer) itemId);
					// int p = 0;
					// }
				}
			});

			if (isCheckable)
				cbx_selected.setVisibility(CheckBox.VISIBLE);
			else
				cbx_selected.setVisibility(CheckBox.GONE);
			cbx_selected.setTag(item.getId());

			// if (selectedItemsId.contains((Integer) item.getId()))
			// cbx_selected.setChecked(true);
			// else
			// cbx_selected.setChecked(false);

			if (item.isActive())
				lock.setVisibility(ImageView.GONE);
			else
				lock.setVisibility(ImageView.VISIBLE);

			name.setText(item.getName());

			type.setText(ParameterTypes.getParameterName(item.getTypeId()));

			value.setText(item.getDefaultValue());

		}
	}

}
