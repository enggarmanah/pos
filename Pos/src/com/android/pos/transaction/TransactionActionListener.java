package com.android.pos.transaction;

import com.android.pos.dao.TransactionDay;
import com.android.pos.dao.Transactions;

public interface TransactionActionListener {
	
	public void onTransactionSummarySelected(TransactionDay transactionSummary);
	
	public void onTransactionSelected(Transactions transaction);
	
	public void onBackButtonClicked();
}
