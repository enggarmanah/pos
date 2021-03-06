package com.app.posweb.server.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "inventory")
public class Inventory extends Base {
	
	private long product_id;
    private String product_name;
    private Float product_cost_price;
    private Float quantity;
    private Long bill_id;
    private String bill_reference_no;
    private Long supplier_id;
    private String supplier_name;
    private java.util.Date inventory_date;
    private String remarks;
    private String status;
    
    public void setBean(Inventory bean) {

		super.setBean(bean);
		
		this.product_id = bean.getProduct_id();
		this.product_name = bean.getProduct_name();
		this.product_cost_price = bean.getProduct_cost_price();
		this.quantity = bean.getQuantity();
		this.bill_id = bean.getBill_id();
		this.bill_reference_no = bean.getBill_reference_no();
		this.supplier_id = bean.getSupplier_id();
		this.supplier_name = bean.getSupplier_name();
		this.inventory_date = bean.getInventory_date();
		this.remarks = bean.getRemarks();
		this.status = bean.getStatus();
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

	public Float getProduct_cost_price() {
		return product_cost_price;
	}

	public void setProduct_cost_price(Float product_cost_price) {
		this.product_cost_price = product_cost_price;
	}

	public Float getQuantity() {
		return quantity;
	}

	public void setQuantity(Float quantity) {
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

	public java.util.Date getInventory_date() {
		return inventory_date;
	}

	public void setInventory_date(java.util.Date inventory_date) {
		this.inventory_date = inventory_date;
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
