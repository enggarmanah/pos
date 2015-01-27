package com.android.pos;

import com.android.pos.common.AlertDlgFragment;

public class NotificationUtil {
	
	public static String ALERT_DIALOG_FRAGMENT_TAG = "alertDialogFragment";
	
	public static AlertDlgFragment mAlertDialog = new AlertDlgFragment();
	
	public static AlertDlgFragment getAlertDialogInstance() {
		
		return mAlertDialog;
	}
}
