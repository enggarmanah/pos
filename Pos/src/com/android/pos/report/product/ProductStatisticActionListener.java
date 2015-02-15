package com.android.pos.report.product;

import com.android.pos.dao.TransactionMonth;
import com.android.pos.dao.TransactionYear;

public interface ProductStatisticActionListener {
	
	public void onTransactionYearSelected(TransactionYear transactionYear);
	
	public void onTransactionMonthSelected(TransactionMonth transactionMonth);
	
	public void onBackButtonClicked();
}
