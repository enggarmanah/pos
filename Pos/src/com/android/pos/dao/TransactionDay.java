package com.android.pos.dao;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class TransactionDay implements Serializable {

	private Date date;
	private Long amount;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
}
