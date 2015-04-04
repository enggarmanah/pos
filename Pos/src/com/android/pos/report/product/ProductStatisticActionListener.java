package com.android.pos.report.product;

import com.android.pos.model.TransactionMonthBean;
import com.android.pos.model.TransactionYearBean;

public interface ProductStatisticActionListener {
	
	public void onTransactionYearSelected(TransactionYearBean transactionYear);
	
	public void onTransactionMonthSelected(TransactionMonthBean transactionMonth);
	
	public void onBackPressed();
}
