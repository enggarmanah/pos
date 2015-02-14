package com.app.posweb.server.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product extends Base {

	private Long product_group_id;
	private String name;
	private String type;
	private Integer price;
	private Integer cost_price;
	private String pic_required;
	private Integer commision;
	private Integer promo_price;
	private java.util.Date promo_start;
	private java.util.Date promo_end;
	private String status;
	
	public void setBean(Product bean) {

		super.setBean(bean);
		this.product_group_id = bean.getProduct_group_id();
		this.name = bean.getName();
		this.type = bean.getType();
		this.price = bean.getPrice();
		this.cost_price = bean.getCost_price();
		this.pic_required = bean.getPic_required();
		this.commision = bean.getCommision();
		this.promo_price = bean.getPromo_price();
		this.promo_start = bean.getPromo_start();
		this.promo_end = bean.getPromo_end();
		this.status = bean.getStatus();
	}

	public Long getProduct_group_id() {
		return product_group_id;
	}

	public void setProduct_group_id(Long product_group_id) {
		this.product_group_id = product_group_id;
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getCost_price() {
		return cost_price;
	}

	public void setCost_price(Integer cost_price) {
		this.cost_price = cost_price;
	}

	public String getPic_required() {
		return pic_required;
	}

	public void setPic_required(String pic_required) {
		this.pic_required = pic_required;
	}

	public Integer getCommision() {
		return commision;
	}

	public void setCommision(Integer commision) {
		this.commision = commision;
	}

	public Integer getPromo_price() {
		return promo_price;
	}

	public void setPromo_price(Integer promo_price) {
		this.promo_price = promo_price;
	}

	public java.util.Date getPromo_start() {
		return promo_start;
	}

	public void setPromo_start(java.util.Date promo_start) {
		this.promo_start = promo_start;
	}

	public java.util.Date getPromo_end() {
		return promo_end;
	}

	public void setPromo_end(java.util.Date promo_end) {
		this.promo_end = promo_end;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
