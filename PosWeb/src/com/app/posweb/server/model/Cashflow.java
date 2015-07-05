package com.app.posweb.server.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cashflow")
public class Cashflow extends Base {

	private String type;
	private Long bill_id;
	private Long transaction_id;
	private java.util.Date cash_date;
	private Float cash_amount;
	private String remarks;
	private String status;
    
    public void setBean(Cashflow bean) {

		super.setBean(bean);
		
		type = bean.getType();
		bill_id = bean.getBill_id();
		transaction_id = bean.getTransaction_id();
		cash_date = bean.getCash_date();
		cash_amount = bean.getCash_amount();
		remarks = bean.getRemarks();
		status = bean.getStatus();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getBill_id() {
		return bill_id;
	}

	public void setBill_id(Long bill_id) {
		this.bill_id = bill_id;
	}

	public Long getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(Long transaction_id) {
		this.transaction_id = transaction_id;
	}

	public java.util.Date getCash_date() {
		return cash_date;
	}

	public void setCash_date(java.util.Date cash_date) {
		this.cash_date = cash_date;
	}

	public Float getCash_amount() {
		return cash_amount;
	}

	public void setCash_amount(Float cash_amount) {
		this.cash_amount = cash_amount;
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
