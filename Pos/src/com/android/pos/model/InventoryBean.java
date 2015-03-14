package com.android.pos.model;

public class InventoryBean extends BaseBean {

	private long productId;
	private String product_name;
	private Integer product_cost_price;
	private String quantity_str;
	private Integer quantity;
	private long bills_id;
	private String bills_reference_no;
	private long supplier_id;
	private String supplier_name;
	private java.util.Date delivery_date;
	private String remarks;
	private String status;

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
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

	public long getBills_id() {
		return bills_id;
	}

	public void setBills_id(long bills_id) {
		this.bills_id = bills_id;
	}

	public String getBills_reference_no() {
		return bills_reference_no;
	}

	public void setBills_reference_no(String bills_reference_no) {
		this.bills_reference_no = bills_reference_no;
	}

	public long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(long supplier_id) {
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
