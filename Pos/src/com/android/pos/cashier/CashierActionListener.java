package com.android.pos.cashier;

import com.android.pos.dao.Customer;
import com.android.pos.dao.Discount;
import com.android.pos.dao.Employee;
import com.android.pos.dao.Orders;
import com.android.pos.dao.Product;
import com.android.pos.dao.Transactions;

public interface CashierActionListener {
	
	public void onShowProductGroups();
	
	public void onProductSelected(Product product);
	
	public void onProductSelected(Product product, int price, Employee personInCharge, int quantity, String remarks);
	
	public void onPaymentRequested(int totalBill);
	
	public void onOrderRequested(int totalOrder);
	
	public void onPaymentInfoProvided(Customer customer, String paymentType, int totalBill, int payment);
	
	public void onOrderInfoProvided(String orderReference, String orderType);
	
	public void onPaymentCompleted(Transactions transaction);
	
	public void onOrderConfirmed(Orders order);
	
	public void onPrintReceipt(Transactions transaction);
	
	public void onPrintOrder(Orders order);
	
	public void onSelectDiscount();
	
	public void onDiscountSelected(Discount discount);
	
	public void onSelectCustomer();
	
	public void onClearTransaction();
}
