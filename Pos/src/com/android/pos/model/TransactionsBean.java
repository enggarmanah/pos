package com.android.pos.model;

public class TransactionsBean {

	private Long id;
	private Long remote_id;
	private long merchant_id;
	private String transaction_no;
	private String order_type;
	private String order_reference;
	private java.util.Date transaction_date;
	private Integer bill_amount;
	private String discount_name;
	private Integer discount_percentage;
	private Integer discount_amount;
	private Integer tax_percentage;
	private Integer tax_amount;
	private Integer service_charge_percentage;
	private Integer service_charge_amount;
	private Integer total_amount;
	private Integer payment_amount;
	private Integer return_amount;
	private String payment_type;
	private long cashier_id;
	private String cashier_name;
	private long customer_id;
	private String customer_name;	
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRemote_id() {
		return remote_id;
	}

	public void setRemote_id(Long remote_id) {
		this.remote_id = remote_id;
	}

	public long getMerchant_id() {
		return merchant_id;
	}

	public void setMerchant_id(long merchant_id) {
		this.merchant_id = merchant_id;
	}

	public String getTransaction_no() {
		return transaction_no;
	}

	public void setTransaction_no(String transaction_no) {
		this.transaction_no = transaction_no;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getOrder_reference() {
		return order_reference;
	}

	public void setOrder_reference(String order_reference) {
		this.order_reference = order_reference;
	}

	public java.util.Date getTransaction_date() {
		return transaction_date;
	}

	public void setTransaction_date(java.util.Date transaction_date) {
		this.transaction_date = transaction_date;
	}

	public Integer getBill_amount() {
		return bill_amount;
	}

	public void setBill_amount(Integer bill_amount) {
		this.bill_amount = bill_amount;
	}

	public String getDiscount_name() {
		return discount_name;
	}

	public void setDiscount_name(String discount_name) {
		this.discount_name = discount_name;
	}

	public Integer getDiscount_percentage() {
		return discount_percentage;
	}

	public void setDiscount_percentage(Integer discount_percentage) {
		this.discount_percentage = discount_percentage;
	}

	public Integer getDiscount_amount() {
		return discount_amount;
	}

	public void setDiscount_amount(Integer discount_amount) {
		this.discount_amount = discount_amount;
	}

	public Integer getTax_percentage() {
		return tax_percentage;
	}

	public void setTax_percentage(Integer tax_percentage) {
		this.tax_percentage = tax_percentage;
	}

	public Integer getTax_amount() {
		return tax_amount;
	}

	public void setTax_amount(Integer tax_amount) {
		this.tax_amount = tax_amount;
	}

	public Integer getService_charge_percentage() {
		return service_charge_percentage;
	}

	public void setService_charge_percentage(Integer service_charge_percentage) {
		this.service_charge_percentage = service_charge_percentage;
	}

	public Integer getService_charge_amount() {
		return service_charge_amount;
	}

	public void setService_charge_amount(Integer service_charge_amount) {
		this.service_charge_amount = service_charge_amount;
	}

	public Integer getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(Integer total_amount) {
		this.total_amount = total_amount;
	}

	public Integer getPayment_amount() {
		return payment_amount;
	}

	public void setPayment_amount(Integer payment_amount) {
		this.payment_amount = payment_amount;
	}

	public Integer getReturn_amount() {
		return return_amount;
	}

	public void setReturn_amount(Integer return_amount) {
		this.return_amount = return_amount;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public long getCashier_id() {
		return cashier_id;
	}

	public void setCashier_id(long cashier_id) {
		this.cashier_id = cashier_id;
	}

	public String getCashier_name() {
		return cashier_name;
	}

	public void setCashier_name(String cashier_name) {
		this.cashier_name = cashier_name;
	}

	public long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
