package com.app.posweb.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	private Long remote_id;
	private long merchant_id;
	private String order_no;
    private java.util.Date order_date;
    private String order_type;
    private String order_reference;
    private String customer_name;
    private String status;

	public void setBean(Orders bean) {

		this.id = bean.getId();
		this.merchant_id = bean.getMerchant_id();
		this.remote_id = bean.getRemote_id();
		this.order_no = bean.getOrder_no();
		this.order_date = bean.getOrder_date();
		this.order_type = bean.getOrder_type();
		this.order_reference = bean.getOrder_reference();
		this.customer_name = bean.getCustomer_name();
		this.status = bean.getStatus();
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

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public java.util.Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(java.util.Date order_date) {
		this.order_date = order_date;
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
