package com.app.posweb.server.model;

import java.util.Date;
import java.util.List;

public class SyncResponse {

	public static final String SUCCESS = "SUC";
	public static final String ERROR = "ERR";

	protected String respCode;
	protected String respDescription;
	protected Date respDate;
	
	protected String sync_key;
	
	protected List<SyncStatus> status;
	
	protected List<String> taskHasUpdates;
	
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
	
	public String getSync_key() {
		return sync_key;
	}

	public void setSync_key(String sync_key) {
		this.sync_key = sync_key;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String code) {
		this.respCode = code;
	}

	public String getRespDescription() {
		return respDescription;
	}

	public void setRespDescription(String description) {
		this.respDescription = description;
	}
	
	public Date getRespDate() {
		return respDate;
	}

	public void setRespDate(Date respDate) {
		this.respDate = respDate;
	}

	public List<String> getTaskHasUpdates() {
		return taskHasUpdates;
	}

	public void setTaskHasUpdates(List<String> taskHasUpdates) {
		this.taskHasUpdates = taskHasUpdates;
	}

	public List<SyncStatus> getStatus() {
		return status;
	}

	public void setStatus(List<SyncStatus> status) {
		this.status = status;
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
