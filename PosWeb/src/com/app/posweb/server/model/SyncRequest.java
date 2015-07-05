package com.app.posweb.server.model;

import java.util.Date;
import java.util.List;

public class SyncRequest {

	protected Long merchant_id;
	protected String sync_type;
	protected Date last_sync_date;
	protected Date sync_date;
	protected String uuid;
	
	protected List<String> getRequests;
	
	protected Sync sync;
	protected Merchant merchant;
	protected User user;
	
	protected List<Bills> bills;
	protected List<Customer> customers;
	protected List<Cashflow> cashflows;
	protected List<Discount> discounts;
	protected List<Employee> employees;
	protected List<Inventory> inventories;
	protected List<MerchantAccess> merchantAccesses;
	protected List<Merchant> merchants;
	protected List<OrderItem> orderItems;
	protected List<Orders> orders;
	protected List<Product> products; 
	protected List<ProductGroup> productGroups;
	protected List<Supplier> suppliers;
	protected List<TransactionItem> transactionItems;
	protected List<Transactions> transactions;
	protected List<UserAccess> userAccesses;
	protected List<User> users;

	public Long getMerchant_id() {
		return merchant_id;
	}

	public void setMerchant_id(Long merchant_id) {
		this.merchant_id = merchant_id;
	}
	
	public String getSync_type() {
		return sync_type;
	}

	public void setSync_type(String sync_type) {
		this.sync_type = sync_type;
	}

	public Date getLast_sync_date() {
		return last_sync_date;
	}

	public void setLast_sync_date(Date last_sync_date) {
		this.last_sync_date = last_sync_date;
	}
	
	public Date getSync_date() {
		return sync_date;
	}

	public void setSync_date(Date sync_date) {
		this.sync_date = sync_date;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public List<String> getGetRequests() {
		return getRequests;
	}

	public void setGetRequests(List<String> getRequests) {
		this.getRequests = getRequests;
	}

	public Sync getSync() {
		return sync;
	}

	public void setSync(Sync sync) {
		this.sync = sync;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Bills> getBills() {
		return bills;
	}

	public void setBills(List<Bills> bills) {
		this.bills = bills;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
	public List<Cashflow> getCashflows() {
		return cashflows;
	}

	public void setCashflows(List<Cashflow> cashflows) {
		this.cashflows = cashflows;
	}

	public List<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<Discount> discounts) {
		this.discounts = discounts;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public List<Inventory> getInventories() {
		return inventories;
	}

	public void setInventories(List<Inventory> inventories) {
		this.inventories = inventories;
	}

	public List<MerchantAccess> getMerchantAccesses() {
		return merchantAccesses;
	}

	public void setMerchantAccesses(List<MerchantAccess> merchantAccesses) {
		this.merchantAccesses = merchantAccesses;
	}

	public List<Merchant> getMerchants() {
		return merchants;
	}

	public void setMerchants(List<Merchant> merchants) {
		this.merchants = merchants;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public List<Orders> getOrders() {
		return orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<ProductGroup> getProductGroups() {
		return productGroups;
	}

	public void setProductGroups(List<ProductGroup> productGroups) {
		this.productGroups = productGroups;
	}

	public List<Supplier> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(List<Supplier> suppliers) {
		this.suppliers = suppliers;
	}

	public List<TransactionItem> getTransactionItems() {
		return transactionItems;
	}

	public void setTransactionItems(List<TransactionItem> transactionItems) {
		this.transactionItems = transactionItems;
	}

	public List<Transactions> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transactions> transactions) {
		this.transactions = transactions;
	}

	public List<UserAccess> getUserAccesses() {
		return userAccesses;
	}

	public void setUserAccesses(List<UserAccess> userAccesses) {
		this.userAccesses = userAccesses;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
