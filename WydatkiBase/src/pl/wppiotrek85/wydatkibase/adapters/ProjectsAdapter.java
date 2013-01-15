package pl.wppiotrek85.wydatkibase.adapters;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.entities.ModelBase;
import pl.wppiotrek85.wydatkibase.entities.Project;
import pl.wppiotrek85.wydatkibase.interfaces.IOnAdapterCheckboxClick;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ProjectsAdapter extends BaseObjectAdapter<Project> {

	public ProjectsAdapter(Context context, ArrayList<Project> items,
			IOnAdapterCheckboxClick listener) {
		super(context, items, listener);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.row_project_layout, null);

		Object o = getItem(position);
		fillRow(convertView, o);
		super.setCheckableViewState(convertView, (ModelBase) o);
		return convertView;
	}

	private void fillRow(View convertView, Object o) {
		Project item = (Project) o;
		if (item != null) {
			TextView name = (TextView) convertView
					.findViewById(R.id.row_project_name);

			ImageView lock = (ImageView) convertView
					.findViewById(R.id.row_project_lock);

			if (item.isActive())
				lock.setVisibility(ImageView.GONE);
			else
				lock.setVisibility(ImageView.VISIBLE);

			name.setText(item.getName());
		}
	}

}
