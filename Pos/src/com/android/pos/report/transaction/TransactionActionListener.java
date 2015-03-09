package com.android.pos.report.transaction;

import com.android.pos.dao.Transactions;
import com.android.pos.model.TransactionDayBean;
import com.android.pos.model.TransactionMonthBean;
import com.android.pos.model.TransactionYearBean;

public interface TransactionActionListener {
	
	public void onTransactionYearSelected(TransactionYearBean transactionYear);
	
	public void onTransactionMonthSelected(TransactionMonthBean transactionMonth);
	
	public void onTransactionDaySelected(TransactionDayBean transactionDay);
	
	public void onTransactionSelected(Transactions transaction);
	
	public void onBackButtonClicked();
}
