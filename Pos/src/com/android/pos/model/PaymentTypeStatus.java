package com.android.pos.model;

import com.android.pos.CodeBean;

public class PaymentTypeStatus {
	
	CodeBean paymentType;
	String status;
	
	public CodeBean getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(CodeBean paymentType) {
		this.paymentType = paymentType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
