package com.app.posweb.server.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product extends Base {

	private Long product_group_id;
	private String code;
	private String name;
	private String type;
	private Integer price_1;
	private Integer price_2;
	private Integer price_3;
	private Integer cost_price;
	private String pic_required;
	private Integer commision;
	private Integer promo_price;
	private java.util.Date promo_start;
	private java.util.Date promo_end;
	private Integer stock;
	private Integer min_stock;
	private String status;
	
	public void setBean(Product bean) {

		super.setBean(bean);
		this.product_group_id = bean.getProduct_group_id();
		this.code = bean.getCode();
		this.name = bean.getName();
		this.type = bean.getType();
		this.price_1 = bean.getPrice_1();
		this.price_2 = bean.getPrice_2();
		this.price_3 = bean.getPrice_3();
		this.cost_price = bean.getCost_price();
		this.pic_required = bean.getPic_required();
		this.commision = bean.getCommision();
		this.promo_price = bean.getPromo_price();
		this.promo_start = bean.getPromo_start();
		this.promo_end = bean.getPromo_end();
		this.stock = bean.getStock();
		this.min_stock = bean.getMin_stock();
		this.status = bean.getStatus();
	}

	public Long getProduct_group_id() {
		return product_group_id;
	}

	public void setProduct_group_id(Long product_group_id) {
		this.product_group_id = product_group_id;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Integer getPrice_1() {
		return price_1;
	}

	public void setPrice_1(Integer price_1) {
		this.price_1 = price_1;
	}

	public Integer getPrice_2() {
		return price_2;
	}

	public void setPrice_2(Integer price_2) {
		this.price_2 = price_2;
	}

	public Integer getPrice_3() {
		return price_3;
	}

	public void setPrice_3(Integer price_3) {
		this.price_3 = price_3;
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
	
	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
	public Integer getMin_stock() {
		return min_stock;
	}

	public void setMin_stock(Integer min_stock) {
		this.min_stock = min_stock;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
