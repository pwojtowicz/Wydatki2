package pl.wppiotrek85.wydatkibase.adapters;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.entities.InvokeTransactionParameter;
import pl.wppiotrek85.wydatkibase.views.ViewType;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class InvokeTransactionAdapter extends BaseAdapter {

	ArrayList<InvokeTransactionParameter> items = new ArrayList<InvokeTransactionParameter>();

	private final Context context;

	public InvokeTransactionAdapter(Context context) {
		this.context = context;
	}

	public int getCount() {
		return items.size();
	}

	public Object getItem(int index) {
		return items.get(index);
	}

	public long getItemId(int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getItemViewType(int index) {
		return items.get(index).getTypeId();
	}

	@Override
	public int getViewTypeCount() {
		return ViewType.COUNT;
	}

	public View getView(int index, View convertView, ViewGroup arg2) {
		View view = convertView;
		// clearFillableItems();

		// Pobieranie typu widoku
		int viewType = getItemViewType(index);

		InvokeTransactionParameter paramInstance = (InvokeTransactionParameter) getItem(index);

		if (view == null) {
			view = prepareView(view, viewType, paramInstance, false);
		} else {
			view = prepareView(view, viewType, paramInstance, true);
		}

		view.setTag(paramInstance);
		return view;
	}

	private View prepareView(View view, int viewType,
			InvokeTransactionParameter paramInstance, boolean fillOnly) {
		// view = CustomViewFactory.createOrFill(viewType, context,
		// paramInstance,
		// fillOnly, view);

		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		view = layoutInflater.inflate(R.layout.invoke_action_comment, null);
		return view;
	}

	public void setParamaters(ArrayList<InvokeTransactionParameter> arrayList) {
		items = arrayList;
		this.notifyDataSetChanged();
	}

	public ArrayList<InvokeTransactionParameter> getAllItems() {
		return items;
	}
}
