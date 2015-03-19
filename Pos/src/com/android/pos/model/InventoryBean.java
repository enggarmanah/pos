package com.android.pos.model;

public class InventoryBean extends BaseBean {

	private Long product_id;
	private String product_name;
	private Integer product_cost_price;
	private String quantity_str;
	private Integer quantity;
	private Long bill_id;
	private String bill_reference_no;
	private Long supplier_id;
	private String supplier_name;
	private java.util.Date delivery_date;
	private String remarks;
	private String status;

	public Long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Long productId) {
		this.product_id = productId;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	
	public Integer getProduct_cost_price() {
		return product_cost_price;
	}

	public void setProduct_cost_price(Integer product_cost_price) {
		this.product_cost_price = product_cost_price;
	}

	public String getQuantity_str() {
		return quantity_str;
	}

	public void setQuantity_str(String quantity_str) {
		this.quantity_str = quantity_str;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Long getBill_id() {
		return bill_id;
	}

	public void setBill_id(Long bill_id) {
		this.bill_id = bill_id;
	}

	public String getBill_reference_no() {
		return bill_reference_no;
	}

	public void setBill_reference_no(String bill_reference_no) {
		this.bill_reference_no = bill_reference_no;
	}

	public Long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(Long supplier_id) {
		this.supplier_id = supplier_id;
	}

	public String getSupplier_name() {
		return supplier_name;
	}

	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}

	public java.util.Date getDelivery_date() {
		return delivery_date;
	}

	public void setDelivery_date(java.util.Date delivery_date) {
		this.delivery_date = delivery_date;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
