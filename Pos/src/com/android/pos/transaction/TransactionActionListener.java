package com.android.pos.transaction;

import com.android.pos.dao.TransactionSummary;
import com.android.pos.dao.Transactions;

public interface TransactionActionListener {
	
	public void onTransactionSummarySelected(TransactionSummary transactionSummary);
	
	public void onTransactionSelected(Transactions transaction);
	
	public void onBackButtonClicked();
}
