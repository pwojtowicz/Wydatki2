package pl.wppiotrek85.wydatkibase.adapters;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.entities.Category;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoriesAdapter extends BaseObjectAdapter<Category> {

	public CategoriesAdapter(Context context, ArrayList<Category> items) {
		super(context, items);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.row_category_layout, null);

		fillRow(convertView, getItem(position));
		return convertView;
	}

	private void fillRow(View convertView, Object o) {
		Category item = (Category) o;
		if (item != null) {
			TextView name = (TextView) convertView
					.findViewById(R.id.row_category_name);

			TextView details = (TextView) convertView
					.findViewById(R.id.row_category_details);

			ImageView lock = (ImageView) convertView
					.findViewById(R.id.row_category_lock);

			CheckBox cbx_selected = (CheckBox) convertView
					.findViewById(R.id.row_cbx_selected);

			if (item.isActive())
				lock.setVisibility(ImageView.GONE);
			else
				lock.setVisibility(ImageView.VISIBLE);

			name.setText(item.getLvl() + item.getName());

			details.setText(item.getParameters());

		}
	}

}
