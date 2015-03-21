package com.app.posweb.server.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "merchant_access")
public class MerchantAccess extends Base {

	private String name;
    private String code;
    private String status;
    
    public void setBean(MerchantAccess bean) {

		super.setBean(bean);
		
		this.name = bean.getName();
		this.code = bean.getCode();
		this.status = bean.getStatus();
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
