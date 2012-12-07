package pl.wppiotrek85.wydatkibase.units;

import pl.wppiotrek85.wydatkibase.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class AccountImages {
	public final static int Money = 1;
	public final static int Cash = 2;
	public final static int Deposit = 3;
	public final static int CreditCard = 4;

	public static Drawable getImageForImageIndex(int imageIndex, Context context) {
		Resources resources = context.getResources();
		switch (imageIndex) {
		case Money:
			return resources.getDrawable(R.drawable.money);
		case Cash:
			return resources.getDrawable(R.drawable.money);
		case Deposit:
			return resources.getDrawable(R.drawable.money);
		case CreditCard:
			return resources.getDrawable(R.drawable.money);
		}
		return resources.getDrawable(R.drawable.money);

	}
}
