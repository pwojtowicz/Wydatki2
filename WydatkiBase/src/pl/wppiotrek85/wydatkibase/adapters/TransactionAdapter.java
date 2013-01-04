package pl.wppiotrek85.wydatkibase.adapters;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.entities.Transaction;
import pl.wppiotrek85.wydatkibase.enums.ViewState;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import pl.wppiotrek85.wydatkibase.units.UnitConverter;
import pl.wppiotrek85.wydatkibase.views.ControlListRowView;
import pl.wppiotrek85.wydatkibase.views.ViewType;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TransactionAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Transaction> items = new ArrayList<Transaction>();
	private LayoutInflater mInflater;
	private View controlView;
	private ViewState actualControlState = ViewState.Normal;

	public TransactionAdapter(Context context, ArrayList<Transaction> list,
			boolean hasMore) {
		this.context = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (list != null)
			this.items = list;

		if (hasMore) {
			this.items.add(null);
			actualControlState = ViewState.DownloadMore;
		}
	}

	public int getCount() {
		return items.size();
	}

	public Object getItem(int index) {
		return items.get(index);
	}

	public long getItemId(int index) {
		return index;
	}

	@Override
	public int getItemViewType(int index) {
		Object o = items.get(index);
		if (o != null && !(o instanceof String))
			return ViewType.DEFAULT;
		else
			return ViewType.CONTROL;
	}

	public View getView(int index, View convertView, ViewGroup arg2) {
		TransactionAdapterObjectHandler oh = null;
		int viewType = getItemViewType(index);
		Object obj = null;
		if (convertView != null) {
			obj = convertView.getTag();
		}

		Object o = getItem(index);
		if (convertView == null
				|| (obj != null && viewType == ViewType.DEFAULT && obj instanceof ViewState)
				|| (obj != null && viewType == ViewType.CONTROL && obj instanceof TransactionAdapterObjectHandler)) {

			switch (viewType) {
			case ViewType.DEFAULT: {
				oh = new TransactionAdapterObjectHandler();
				convertView = mInflater.inflate(
						R.layout.row_transaction_layout, null);
				oh.value = (TextView) convertView
						.findViewById(R.id.row_transaction_value);
				oh.accounts = (TextView) convertView
						.findViewById(R.id.row_transaction_accounts);
				oh.note = (TextView) convertView
						.findViewById(R.id.row_transaction_note);
				oh.date = (TextView) convertView
						.findViewById(R.id.row_transaction_date);
				convertView.setTag(oh);
			}
				break;
			case ViewType.CONTROL:
				convertView = mInflater
						.inflate(R.layout.row_control_view, null);
				break;
			}
		}
		if (o != null) {
			TransactionAdapterObjectHandler ohs = (TransactionAdapterObjectHandler) convertView
					.getTag();
			fillRow(convertView, o, index);
		} else {
			setDownloadView(convertView);
		}

		return convertView;
	}

	private void fillRow(View convertView, Object o, int index) {
		TransactionAdapterObjectHandler oh = (TransactionAdapterObjectHandler) convertView
				.getTag();
		Transaction transaction = (Transaction) o;
		if (transaction != null) {
			WydatkiGlobals globals = WydatkiGlobals.getInstance();

			StringBuilder accounts = new StringBuilder();
			if (transaction.getAccMinus() > 0)
				accounts.append(globals.getAccountById(
						transaction.getAccMinus()).getName());

			if (transaction.getAccMinus() > 0 && transaction.getAccPlus() > 0)
				accounts.append(" >> ");

			if (transaction.getAccPlus() > 0)
				accounts.append(globals
						.getAccountById(transaction.getAccPlus()).getName());

			oh.accounts.setText(accounts.toString());

			StringBuilder note = new StringBuilder();
			if (transaction.getCategory() != null
					&& transaction.getCategory().getName() != null)
				note.append(transaction.getCategory().getName());
			if (transaction.getNote() != null
					&& transaction.getNote().length() > 0)
				note.append(" (" + transaction.getNote() + ")");

			oh.note.setText(note.toString());

			oh.value.setText(UnitConverter.doubleToCurrency(transaction
					.getValue()));

			oh.date.setText(UnitConverter.dateTimeString(transaction.getDate()));

			if (transaction.getAccMinus() > 0 && transaction.getAccPlus() > 0) {
				oh.value.setTextColor(context.getResources().getColor(
						R.color.yellow));
			} else if (transaction.getAccMinus() > 0)
				oh.value.setTextColor(context.getResources().getColor(
						R.color.darkRed));
			else if (transaction.getAccPlus() > 0)
				oh.value.setTextColor(context.getResources().getColor(
						R.color.darkGreen));
			oh.transaction = transaction;
		}

	}

	private void setDownloadView(View convertView) {
		ControlListRowView control = new ControlListRowView(context,
				convertView, actualControlState);

		controlView = control.getView();
	}

	public class TransactionAdapterObjectHandler {
		public Transaction transaction;
		public TextView value;
		public TextView accounts;
		public TextView note;
		public TextView date;

	}

	public void getMoreTransactions() {
		actualControlState = ViewState.Downloading;
		// setDownloadView(controlView);
		this.notifyDataSetChanged();
	}

}
