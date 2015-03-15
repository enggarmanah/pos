package com.android.pos.report.inventory;

import com.android.pos.dao.Product;

public interface InventoryReportActionListener {
	
	public void onProductSelected(Product product);
	
	public void onProductUnselected();
	
	public void onBackButtonClicked();
}
