package pl.wppiotrek85.wydatkibase.adapters;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.entities.ModelBase;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

public abstract class BaseObjectAdapter<T> extends BaseAdapter {

	protected ArrayList<T> items;
	protected Context context;
	protected LayoutInflater inflater;
	protected Boolean isCheckable = false;

	public BaseObjectAdapter(Context context, ArrayList<T> items) {
		this.context = context;
		this.items = items;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
