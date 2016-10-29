package com.app.posweb.server.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "discount")
public class Discount extends Base {

	private String name;
	private String type;
	private Float amount;
	private Float percentage;
	private String status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPercentage() {
		return percentage;
	}

	public void setPercentage(Float percentage) {
		this.percentage = percentage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public void setBean(Discount bean) {

		super.setBean(bean);
		
		this.name = bean.getName();
		this.type = bean.getType();
		this.amount = bean.getAmount();
		this.percentage = bean.getPercentage();
		this.status = bean.getStatus();
	}
}
