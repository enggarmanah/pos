package com.android.pos.util;

import android.app.FragmentManager;

import com.android.pos.common.AlertDlgFragment;

public class NotificationUtil {
	
	public static String ALERT_DIALOG_FRAGMENT_TAG = "alertDialogFragment";
	
	public static AlertDlgFragment mAlertDialog = new AlertDlgFragment();
	
	private static AlertDlgFragment getAlertDialogInstance() {
		
		return mAlertDialog;
	}
	
	public static void setAlertMessage(FragmentManager fragmentManager, String message) {
		
		AlertDlgFragment mAlertDialog = getAlertDialogInstance();
		mAlertDialog.show(fragmentManager, ALERT_DIALOG_FRAGMENT_TAG);
		mAlertDialog.setAlertMessage(message);
	}
}
