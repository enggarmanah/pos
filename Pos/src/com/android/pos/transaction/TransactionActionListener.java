package com.android.pos.transaction;

import com.android.pos.dao.TransactionDay;
import com.android.pos.dao.Transactions;

public interface TransactionActionListener {
	
	public void onTransactionDaySelected(TransactionDay transactionDay);
	
	public void onTransactionSelected(Transactions transaction);
	
	public void onBackButtonClicked();
}
