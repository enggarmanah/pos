package com.android.pos.popup.search;

import com.android.pos.dao.Transactions;

public interface TransactionSelectionListener {
	
	public void onTransactionSelected(Transactions transaction);
}
