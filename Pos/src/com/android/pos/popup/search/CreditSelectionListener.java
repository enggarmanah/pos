package com.android.pos.popup.search;

import com.android.pos.model.TransactionsBean;

public interface CreditSelectionListener {
	
	public void onTransactionSelected(TransactionsBean transaction);
}
