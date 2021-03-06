package com.app.posweb.server.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User extends Base {
	
	private String name;
    private String user_id;
    private String password;
    private String role;
    private Long employee_id;
    private String status;
	
	public void setBean(User bean) {
		
		super.setBean(bean);
		this.name = bean.getName();
		this.user_id = bean.getUser_id();
		this.password = bean.getPassword();
		this.role = bean.getRole();
		this.employee_id = bean.getEmployee_id();
		this.status = bean.getStatus();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public Long getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(Long employee_id) {
		this.employee_id = employee_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
