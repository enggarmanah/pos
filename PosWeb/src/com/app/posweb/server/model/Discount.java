package com.app.posweb.server.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "discount")
public class Discount extends Base {

	private String name;
	private Integer percentage;
	private String status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPercentage() {
		return percentage;
	}

	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setBean(Discount bean) {

		super.setBean(bean);
		
		this.name = bean.getName();
		this.percentage = bean.getPercentage();
		this.status = bean.getStatus();
	}
}
