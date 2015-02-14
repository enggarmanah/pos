package com.android.pos.transaction;

import com.android.pos.dao.TransactionDay;
import com.android.pos.dao.TransactionMonth;
import com.android.pos.dao.TransactionYear;
import com.android.pos.dao.Transactions;

public interface TransactionActionListener {
	
	public void onTransactionYearSelected(TransactionYear transactionYear);
	
	public void onTransactionMonthSelected(TransactionMonth transactionMonth);
	
	public void onTransactionDaySelected(TransactionDay transactionDay);
	
	public void onTransactionSelected(Transactions transaction);
	
	public void onBackButtonClicked();
}
