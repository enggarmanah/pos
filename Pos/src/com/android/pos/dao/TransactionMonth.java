package com.android.pos.dao;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class TransactionMonth implements Serializable {

	private Date month;
	private Long amount;

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
}
