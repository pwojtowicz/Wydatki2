package pl.wppiotrek85.wydatkibase.adapters;

import java.util.ArrayList;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.entities.Account;
import pl.wppiotrek85.wydatkibase.entities.ModelBase;
import pl.wppiotrek85.wydatkibase.interfaces.IOnAdapterCheckboxClick;
import pl.wppiotrek85.wydatkibase.units.AccountImages;
import pl.wppiotrek85.wydatkibase.units.UnitConverter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountAdapter extends BaseObjectAdapter<Account> {

	public AccountAdapter(Context context, ArrayList<Account> items,
			IOnAdapterCheckboxClick listener) {
		super(context, items, listener);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.row_account_layout, null);

		Object o = getItem(position);
		fillRow(convertView, o);
		super.setCheckableViewState(convertView, (ModelBase) o);
		return convertView;
	}

	private void fillRow(View convertView, Object o) {
		Account item = (Account) o;
		if (item != null) {
			TextView name = (TextView) convertView
					.findViewById(R.id.row_account_name);
			TextView lasAction = (TextView) convertView
					.findViewById(R.id.row_account_last_action_date);
			TextView balance = (TextView) convertView
					.findViewById(R.id.row_account_balance);
			ImageView image = (ImageView) convertView
					.findViewById(R.id.row_account_image);
			ImageView lock = (ImageView) convertView
					.findViewById(R.id.row_account_lock);

			if (item.isActive())
				lock.setVisibility(ImageView.GONE);
			else
				lock.setVisibility(ImageView.VISIBLE);

			name.setText(item.getName());

			lasAction.setText(UnitConverter.convertDateTimeToString(item
					.getLastActionDate()));

			balance.setText(UnitConverter.doubleToCurrency(item.getBalance()));

			if (item.getBalance() < 0)
				balance.setTextColor(context.getResources().getColor(
						R.color.darkRed));
			else if (item.getBalance() > 0)
				balance.setTextColor(context.getResources().getColor(
						R.color.darkGreen));
			image.setImageDrawable(AccountImages.getImageForImageIndex(
					item.getImageIndex(), context));
		}

	}

}
