package com.app.posweb.server.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "supplier")
public class Supplier extends Base {

	private String name;
    private String telephone;
    private String address;
    private String pic_name;
    private String pic_telephone;
    private String remarks;
    private String status;
    
    public void setBean(Supplier bean) {

		super.setBean(bean);
		
		this.name = bean.getName();
		this.telephone = bean.getTelephone();
		this.address = bean.getAddress();
		this.pic_name = bean.getPic_name();
		this.pic_telephone = bean.getPic_telephone();
		this.remarks = bean.getRemarks();
		this.status = bean.getStatus();
	}

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPic_name() {
		return pic_name;
	}

	public void setPic_name(String pic_name) {
		this.pic_name = pic_name;
	}

	public String getPic_telephone() {
		return pic_telephone;
	}

	public void setPic_telephone(String pic_telephone) {
		this.pic_telephone = pic_telephone;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
