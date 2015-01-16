package com.android.pos.transaction;

import com.android.pos.dao.Transactions;

public interface TransactionActionListener {
	
	public void onTransactionSelected(Transactions transaction);
}
