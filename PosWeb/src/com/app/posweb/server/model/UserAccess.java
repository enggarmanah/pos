package com.app.posweb.server.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_access")
public class UserAccess extends Base {

	private long user_id;
    private String name;
    private String code;
    private String status;
    
    public void setBean(UserAccess bean) {

		super.setBean(bean);
		
		this.user_id = bean.getUser_id();
		this.name = bean.getName();
		this.code = bean.getCode();
		this.status = bean.getStatus();
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
