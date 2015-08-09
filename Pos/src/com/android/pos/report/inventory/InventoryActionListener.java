package com.android.pos.report.inventory;

import com.android.pos.dao.Product;

public interface InventoryActionListener {
	
	public void onProductSelected(Product product);
	
	public void onProductUnselected();
	
	public void onBackPressed();
}
