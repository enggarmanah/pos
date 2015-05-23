package com.app.posweb.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transaction_item")
public class TransactionItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	private long remote_id;
	private long merchant_id;
	private long transaction_id;
	private long product_id;
	private String product_name;
	private String product_type;
	private Integer price;
	private Integer cost_price;
	private Integer discount;
	private Integer quantity;
	private Integer commision;
	private Long employee_id;

	public void setBean(TransactionItem bean) {

		this.id = bean.getId();
		this.merchant_id = bean.getMerchant_id();
		this.remote_id = bean.getRemote_id();
		this.transaction_id = bean.getTransaction_id();
		this.product_id = bean.getProduct_id();
		this.product_name = bean.getProduct_name();
		this.product_type = bean.getProduct_type();
		this.price = bean.getPrice();
		this.cost_price = bean.getCost_price();
		this.discount = bean.getDiscount();
		this.quantity = bean.getQuantity();
		this.commision = bean.getCommision();
		this.employee_id = bean.getEmployee_id();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public long getRemote_id() {
		return remote_id;
	}

	public void setRemote_id(long remote_id) {
		this.remote_id = remote_id;
	}

	public long getMerchant_id() {
		return merchant_id;
	}

	public void setMerchant_id(long merchant_id) {
		this.merchant_id = merchant_id;
	}

	public long getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(long transaction_id) {
		this.transaction_id = transaction_id;
	}

	public long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getCost_price() {
		return cost_price;
	}

	public void setCost_price(Integer cost_price) {
		this.cost_price = cost_price;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public Integer getCommision() {
		return commision;
	}

	public void setCommision(Integer commision) {
		this.commision = commision;
	}

	public Long getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(Long employee_id) {
		this.employee_id = employee_id;
	}
}
