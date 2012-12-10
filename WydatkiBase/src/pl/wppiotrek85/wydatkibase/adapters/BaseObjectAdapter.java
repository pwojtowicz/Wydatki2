package pl.wppiotrek85.wydatkibase.adapters;

import java.util.ArrayList;
import java.util.List;

import pl.wppiotrek85.wydatkibase.entities.ModelBase;
import pl.wppiotrek85.wydatkibase.interfaces.IOnAdapterCheckboxClick;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

public abstract class BaseObjectAdapter<T> extends BaseAdapter {

	protected ArrayList<T> items;
	protected Context context;
	protected LayoutInflater inflater;
	protected Boolean isCheckable = true;
	protected IOnAdapterCheckboxClick listener;
	protected List<Object> selectedPositions = new ArrayList<Object>();

	public BaseObjectAdapter(Context context, ArrayList<T> items,
			IOnAdapterCheckboxClick listener) {
		this.context = context;
		this.items = items;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.listener = listener;
	}

	public void addItem(T item) {
		items.add(item);
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int index) {
		return items.get(index);
	}

	@Override
	public long getItemId(int index) {
		return ((ModelBase) items.get(index)).getId();
	}

}
