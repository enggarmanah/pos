package com.android.pos.util;

import android.app.FragmentManager;

import com.android.pos.common.ConfirmListener;
import com.android.pos.common.ConfirmTaskDlgFragment;

public class ConfirmationUtil {
	
	public static final String CONFIRM_DIALOG_FRAGMENT_TAG = "confirmDialogFragment";
	
	public static final String PRINT_ORDER = "PRINT_ORDER";
	public static final String CANCEL_TRANSACTION = "CANCEL_TRANSACTION";
	
	public static ConfirmTaskDlgFragment mAlertDialog = new ConfirmTaskDlgFragment();
	
	private static ConfirmTaskDlgFragment getConfirmDialogInstance() {
		
		return mAlertDialog;
	}
	
	public static void confirmTask(FragmentManager fragmentManager, ConfirmListener listener, String task, String message) {
		
		ConfirmTaskDlgFragment mConfirmDialog = getConfirmDialogInstance();
		mConfirmDialog.show(fragmentManager, CONFIRM_DIALOG_FRAGMENT_TAG);
		mConfirmDialog.setConfirm(listener, task, message);
	}
}
