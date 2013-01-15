package pl.wppiotrek85.wydatkibase.adapters;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.entities.ModelBase;
import pl.wppiotrek85.wydatkibase.entities.Parameter;
import pl.wppiotrek85.wydatkibase.interfaces.IOnAdapterCheckboxClick;
import pl.wppiotrek85.wydatkibase.units.ParameterTypes;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ParametersAdapter extends BaseObjectAdapter<Parameter> {

	public ParametersAdapter(Context context, ArrayList<Parameter> items,
			IOnAdapterCheckboxClick listener) {
		super(context, items, listener);

	}

	public ParametersAdapter(Context context, ArrayList<Parameter> items,
			IOnAdapterCheckboxClick listener, String selectedItems) {
		super(context, items, listener, selectedItems);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.row_parameter_layout, null);

		Object o = getItem(position);
		fillRow(convertView, o, position);
		super.setCheckableViewState(convertView, (ModelBase) o);
		return convertView;
	}

	private void fillRow(View convertView, Object o, int position) {
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
