package com.android.pos.report.credit;

import com.android.pos.model.TransactionsBean;

public interface CreditActionListener {
	
	public void onTransactionSelected(TransactionsBean transaction);
	
	public void onTransactionUnselected();
	
	public void onBackPressed();
}
