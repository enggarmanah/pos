package com.app.posweb.server.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@MappedSuperclass
public class Base{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	protected Long merchant_id;
	
	protected Long remote_id;
	
	protected String ref_id;
	
	protected String create_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	protected Date create_date;
	
	protected String update_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	protected Date update_date;
	
	@Temporal(TemporalType.TIMESTAMP)
	protected Date sync_date;
	
	public void setBean(Base bean) {
		
		this.id = bean.getId();
		this.merchant_id = bean.getMerchant_id();
		this.remote_id = bean.getRemote_id();
		this.ref_id = bean.getRef_id();
		this.create_by = bean.getCreate_by();
		this.create_date = bean.getCreate_date();
		this.update_by = bean.getUpdate_by();
		this.update_date = bean.getUpdate_date();
		this.sync_date = bean.getSync_date();
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

	public Long getRemote_id() {
		return remote_id;
	}

	public void setRemote_id(Long remote_id) {
		this.remote_id = remote_id;
	}
	
	public String getRef_id() {
		return ref_id;
	}

	public void setRef_id(String ref_id) {
		this.ref_id = ref_id;
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
	
	public Date getSync_date() {
		return sync_date;
	}

	public void setSync_date(Date sync_date) {
		this.sync_date = sync_date;
	}
}
