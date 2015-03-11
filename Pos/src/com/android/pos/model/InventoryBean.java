package com.android.pos.model;

public class InventoryBean extends BaseBean {

	private long productId;
	private String productName;
	private String quantityStr;
	private Integer quantity;
	private long billsId;
	private String billsReferenceNo;
	private long supplierId;
	private String supplierName;
	private java.util.Date deliveryDate;
	private String remarks;
	private String status;

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getQuantityStr() {
		return quantityStr;
	}

	public void setQuantityStr(String quantityStr) {
		this.quantityStr = quantityStr;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public long getBillsId() {
		return billsId;
	}

	public void setBillsId(long billsId) {
		this.billsId = billsId;
	}

	public String getBillsReferenceNo() {
		return billsReferenceNo;
	}

	public void setBillsReferenceNo(String billsReferenceNo) {
		this.billsReferenceNo = billsReferenceNo;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public java.util.Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(java.util.Date deliveryDate) {
		this.deliveryDate = deliveryDate;
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
