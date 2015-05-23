package com.android.pos.model;

public class PriceBean {
	
	private String type;
	private Integer value;
	
	public PriceBean(String type, Integer value) {
		this.type = type;
		this.value = value;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
}
