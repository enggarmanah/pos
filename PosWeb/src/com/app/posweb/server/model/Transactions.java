package com.app.posweb.server.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transactions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
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
	private Long customer_id;
	private String customer_name;
	private String status;
	private Date sync_date;

	public void setBean(Transactions bean) {

		this.id = bean.getId();
		this.merchant_id = bean.getMerchant_id();
		this.remote_id = bean.getRemote_id();
		this.transaction_no = bean.getTransaction_no();
		this.order_type = bean.getOrder_type();
		this.order_reference = bean.getOrder_reference();
		this.transaction_date = bean.getTransaction_date();
		this.bill_amount = bean.getBill_amount();
		this.discount_name = bean.getDiscount_name();
		this.discount_percentage = bean.getDiscount_percentage();
		this.discount_amount = bean.getDiscount_amount();
		this.tax_percentage = bean.getTax_percentage();
		this.tax_amount = bean.getTax_amount();
		this.service_charge_percentage = bean.getService_charge_percentage();
		this.service_charge_amount = bean.getService_charge_amount();
		this.total_amount = bean.getTotal_amount();
		this.payment_amount = bean.getPayment_amount();
		this.return_amount = bean.getReturn_amount();
		this.payment_type = bean.getPayment_type();
		this.cashier_id = bean.getCashier_id();
		this.cashier_name = bean.getCashier_name();
		this.customer_id = bean.getCustomer_id();
		this.customer_name = bean.getCustomer_name();
		this.status = bean.getStatus();
		this.sync_date = bean.getSync_date();
	}

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

	public Long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Long customer_id) {
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

	public Date getSync_date() {
		return sync_date;
	}

	public void setSync_date(Date sync_date) {
		this.sync_date = sync_date;
	}
}
