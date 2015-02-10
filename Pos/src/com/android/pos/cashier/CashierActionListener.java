package com.android.pos.cashier;

import com.android.pos.dao.Customer;
import com.android.pos.dao.Discount;
import com.android.pos.dao.Employee;
import com.android.pos.dao.Product;
import com.android.pos.dao.Transactions;

public interface CashierActionListener {
	
	public void onShowProductGroups();
	
	public void onProductSelected(Product product);
	
	public void onProductSelected(Product product, int quantity);
	
	public void onProductQuantitySelected(Product product, Employee personInCharge, int quantity);
	
	public void onPaymentRequested(int totalBill);
	
	public void onPaymentInfoProvided(Customer customer, String paymentType, int totalBill, int payment);
	
	public void onPaymentCompleted(Transactions transaction);
	
	public void onPrintReceipt(Transactions transaction);
	
	public void onSelectDiscount();
	
	public void onSelectDiscountAmount();
	
	public void onDiscountSelected(Discount discount);
	
	public void onSelectCustomer();
	
	public void onCustomerSelected(Customer customer);
	
	public void onClearTransaction();
}
