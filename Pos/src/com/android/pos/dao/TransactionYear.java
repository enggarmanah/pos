package com.android.pos.dao;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class TransactionYear implements Serializable {

	private Date year;
	private Long amount;

	public Date getYear() {
		return year;
	}

	public void setYear(Date year) {
		this.year = year;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
}
