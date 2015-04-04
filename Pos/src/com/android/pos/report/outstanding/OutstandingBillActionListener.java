package com.android.pos.report.outstanding;

import com.android.pos.dao.Bills;

public interface OutstandingBillActionListener {
	
	public void onBillSelected(Bills bill);
	
	public void onBackPressed();
}
