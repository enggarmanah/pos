package com.app.posweb.server.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="product_group")
public class ProductGroup extends Base {
	
	private String name;
	private String status;
	
	public String getName() {

		return name;
	}

	public void setName(String name) {
		
		this.name = name;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setBean(ProductGroup bean) {
		
		super.setBean(bean);
		this.name = bean.getName();
		this.status = bean.getStatus();
	}
}
