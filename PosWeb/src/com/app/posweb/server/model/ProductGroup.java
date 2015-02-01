package com.app.posweb.server.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="product_group")
public class ProductGroup extends Base {
	
	private String name;
	
	public String getName() {

		return name;
	}

	public void setName(String name) {
		
		this.name = name;
	}
}
