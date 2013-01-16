package pl.wppiotrek85.wydatkibase.managers;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.enums.DialogStyle;
import pl.wppiotrek85.wydatkibase.interfaces.IDialogButtonActions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogFactoryManager {

	public static AlertDialog create(DialogStyle dialogStyle, Context context,
			Object dialogBundle, String message,
			final IDialogButtonActions actionsListener) {

		boolean isPositiveBtnEnabled = false;
		boolean isNegativeBtnEnabled = false;

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

		switch (dialogStyle) {
		case Error:
		case Critical_Error:
			dialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
			dialogBuilder.setTitle(context
					.getString(R.string.dialog_error_title));
			isPositiveBtnEnabled = true;
			break;
		case Information:
			dialogBuilder.setIcon(android.R.drawable.ic_dialog_info);
			dialogBuilder.setTitle(context
					.getString(R.string.dialog_information_title));
			isPositiveBtnEnabled = true;
			break;
		case Progress:
			ProgressDialog progressDialog = new ProgressDialog(context);
			progressDialog.setMessage(context
					.getString(R.string.dialog_progress_message));
			progressDialog.setCancelable(false);
			return progressDialog;
		case Question:
			dialogBuilder.setIcon(android.R.drawable.ic_dialog_info);
			isPositiveBtnEnabled = true;
			isNegativeBtnEnabled = true;
			break;
		default:
			break;
		}

		if (isPositiveBtnEnabled)
			dialogBuilder.setPositiveButton(R.string.dialog_ok,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							if (actionsListener != null)
								actionsListener.onPositiveClick();
						}
					});

		if (isNegativeBtnEnabled)
			dialogBuilder.setNegativeButton(R.string.dialog_cancel,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							if (actionsListener != null)
								actionsListener.onNegativeClick();
						}
					});

		dialogBuilder.setMessage(message);
		return dialogBuilder.create();
	}

}
