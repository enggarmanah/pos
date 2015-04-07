package com.android.pos.favorite.customer;

import com.android.pos.dao.Customer;
import com.android.pos.model.CustomerStatisticBean;

public interface CustomerActionListener {
	
	public void onCustomerStatisticSelected(CustomerStatisticBean customerStatistic);
	
	public void onCustomerStatisticUnselected();
	
	public void onBackPressed();
	
	public void showCustomerTransactions(Customer customer);
}
