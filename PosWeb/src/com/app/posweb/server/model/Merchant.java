package com.app.posweb.server.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "merchant")
public class Merchant {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	protected Long remote_id;
	
	private String name;
	private String type;
	private String address;
	private String telephone;
	private String contact_name;
	private String contact_telephone;
	private String login_id;
	private String password;
	private java.util.Date period_start;
	private java.util.Date period_end;
	private Integer tax_percentage;
	private Integer service_charge_percentage;
	private String status;
	
	protected String create_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	protected Date create_date;
	
	protected String update_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	protected Date update_date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRemote_id() {
		return remote_id;
	}

	public void setRemote_id(Long remote_id) {
		this.remote_id = remote_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public String getContact_telephone() {
		return contact_telephone;
	}

	public void setContact_telephone(String contact_telephone) {
		this.contact_telephone = contact_telephone;
	}

	public String getLogin_id() {
		return login_id;
	}

	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public java.util.Date getPeriod_start() {
		return period_start;
	}

	public void setPeriod_start(java.util.Date period_start) {
		this.period_start = period_start;
	}

	public java.util.Date getPeriod_end() {
		return period_end;
	}

	public void setPeriod_end(java.util.Date period_end) {
		this.period_end = period_end;
	}

	public Integer getTax_percentage() {
		return tax_percentage;
	}

	public void setTax_percentage(Integer tax_percentage) {
		this.tax_percentage = tax_percentage;
	}

	public Integer getService_charge_percentage() {
		return service_charge_percentage;
	}

	public void setService_charge_percentage(Integer service_charge_percentage) {
		this.service_charge_percentage = service_charge_percentage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreate_by() {
		return create_by;
	}

	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getUpdate_by() {
		return update_by;
	}

	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	
	public void setBean(Merchant bean) {

		this.id = bean.getId();
		
		this.name = bean.getName();
		this.type = bean.getType();
		this.address = bean.getAddress();
		this.telephone = bean.getTelephone();
		this.contact_name = bean.getContact_name();
		this.contact_telephone = bean.getContact_telephone();
		this.login_id = bean.getLogin_id();
		this.password = bean.getPassword();
		this.period_start = bean.getPeriod_start();
		this.period_end = bean.getPeriod_end();
		this.tax_percentage = bean.getTax_percentage();
		this.service_charge_percentage = bean.getService_charge_percentage();
		this.status = bean.getStatus();

		this.remote_id = bean.getRemote_id();
		this.create_by = bean.getCreate_by();
		this.create_date = bean.getCreate_date();
		this.update_by = bean.getUpdate_by();
		this.update_date = bean.getUpdate_date();
	}
}
