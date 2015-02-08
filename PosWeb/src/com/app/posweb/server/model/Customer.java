package com.app.posweb.server.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer extends Base {

	private String name;
	private String telephone;
	private String email;
	private String email_status;
	private String address;
	private String status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail_status() {
		return email_status;
	}

	public void setEmail_status(String email_status) {
		this.email_status = email_status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setBean(Customer bean) {

		super.setBean(bean);
		
		this.name = bean.getName();
		this.telephone = bean.getTelephone();
		this.email = bean.getEmail();
		this.email_status = bean.getEmail_status();
		this.address = bean.getAddress();
		this.status = bean.getStatus();
	}
}
