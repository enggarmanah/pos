package com.android.pos.model;

import java.util.Date;

public class DeviceBean {

	protected Long id;

	protected Long merchant_id;

	protected String uuid;

	protected Date last_sync_date;

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
