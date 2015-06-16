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
@Table(name="device")
public class Device{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	protected Long merchant_id;
	
	protected String uuid;
	
	@Temporal(TemporalType.TIMESTAMP)
	protected Date last_sync_date;
	
	public void setBean(Device bean) {
		
		this.id = bean.getId();
		this.merchant_id = bean.getMerchant_id();
		this.uuid = bean.getUuid();
		this.last_sync_date = bean.getLast_sync_date();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMerchant_id() {
		return merchant_id;
	}

	public void setMerchant_id(Long merchant_id) {
		this.merchant_id = merchant_id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getLast_sync_date() {
		return last_sync_date;
	}

	public void setLast_sync_date(Date last_sync_date) {
		this.last_sync_date = last_sync_date;
	}
}
