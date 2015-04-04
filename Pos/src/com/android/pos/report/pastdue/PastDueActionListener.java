package com.android.pos.report.pastdue;

import com.android.pos.dao.Bills;

public interface PastDueActionListener {
	
	public void onBillSelected(Bills bill);
	
	public void onBackPressed();
}
