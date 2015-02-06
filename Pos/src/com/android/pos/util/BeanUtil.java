package com.android.pos.util;

import com.android.pos.Constant;
import com.android.pos.dao.Discount;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.ProductGroup;
import com.android.pos.model.DiscountBean;
import com.android.pos.model.MerchantBean;
import com.android.pos.model.ProductGroupBean;

public class BeanUtil {
	
	public static ProductGroupBean getBean(ProductGroup productGroup) {
		
		ProductGroupBean bean = new ProductGroupBean();
		
		bean.setMerchant_id(productGroup.getMerchantId());
		bean.setRemote_id(productGroup.getId());
		bean.setName(productGroup.getName());
		bean.setCreate_by(productGroup.getCreateBy());
		bean.setCreate_date(productGroup.getCreateDate());
		bean.setUpdate_by(productGroup.getUpdateBy());
		bean.setUpdate_date(productGroup.getUpdateDate());
		
		return bean;
	}
	
	public static void updateBean(ProductGroup productGroup, ProductGroupBean bean) {
		
		productGroup.setMerchantId(bean.getMerchant_id());
		productGroup.setId(bean.getRemote_id());
		productGroup.setName(bean.getName());
		productGroup.setUploadStatus(Constant.STATUS_NO);
		productGroup.setCreateBy(bean.getCreate_by());
		productGroup.setCreateDate(bean.getCreate_date());
		productGroup.setUpdateBy(bean.getUpdate_by());
		productGroup.setUpdateDate(bean.getUpdate_date());
	}
	
	public static DiscountBean getBean(Discount discount) {
		
		DiscountBean bean = new DiscountBean();
		
		bean.setMerchant_id(discount.getMerchantId());
		bean.setRemote_id(discount.getId());
		bean.setName(discount.getName());
		bean.setPercentage(discount.getPercentage());
		bean.setStatus(discount.getStatus());
		bean.setCreate_by(discount.getCreateBy());
		bean.setCreate_date(discount.getCreateDate());
		bean.setUpdate_by(discount.getUpdateBy());
		bean.setUpdate_date(discount.getUpdateDate());
		
		return bean;
	}
	
	public static void updateBean(Discount discount, DiscountBean bean) {
		
		discount.setMerchantId(bean.getMerchant_id());
		discount.setId(bean.getRemote_id());
		discount.setName(bean.getName());
		discount.setPercentage(bean.getPercentage());
		discount.setStatus(bean.getStatus());
		discount.setUploadStatus(Constant.STATUS_NO);
		discount.setCreateBy(bean.getCreate_by());
		discount.setCreateDate(bean.getCreate_date());
		discount.setUpdateBy(bean.getUpdate_by());
		discount.setUpdateDate(bean.getUpdate_date());
	}
	
public static MerchantBean getBean(Merchant merchant) {
		
		MerchantBean bean = new MerchantBean();
		
		bean.setRemote_id(merchant.getId());
		bean.setName(merchant.getName());
		bean.setType(merchant.getType());
		bean.setAddress(merchant.getAddress());
		bean.setContact_name(merchant.getContactName());
		bean.setContact_telephone(merchant.getContactTelephone());
		bean.setLogin_id(merchant.getLoginId());
		bean.setPassword(merchant.getPassword());
		bean.setPeriod_start(merchant.getPeriodStart());
		bean.setPeriod_end(merchant.getPeriodEnd());
		bean.setTax_percentage(merchant.getTaxPercentage());
		bean.setService_charge_percentage(merchant.getServiceChargePercentage());
		bean.setStatus(merchant.getStatus());
		bean.setCreate_by(merchant.getCreateBy());
		bean.setCreate_date(merchant.getCreateDate());
		bean.setUpdate_by(merchant.getUpdateBy());
		bean.setUpdate_date(merchant.getUpdateDate());
		
		return bean;
	}
	
	public static void updateBean(Merchant merchant, MerchantBean bean) {
		
		merchant.setId(bean.getRemote_id());
		merchant.setName(bean.getName());
		merchant.setType(bean.getType());
		merchant.setAddress(bean.getAddress());
		merchant.setContactName(bean.getContact_name());
		merchant.setContactTelephone(bean.getContact_telephone());
		merchant.setLoginId(bean.getLogin_id());
		merchant.setPassword(bean.getPassword());
		merchant.setPeriodStart(bean.getPeriod_start());
		merchant.setPeriodEnd(bean.getPeriod_end());
		merchant.setTaxPercentage(bean.getTax_percentage());
		merchant.setServiceChargePercentage(bean.getService_charge_percentage());
		merchant.setStatus(bean.getStatus());
		merchant.setUploadStatus(Constant.STATUS_NO);
		merchant.setCreateBy(bean.getCreate_by());
		merchant.setCreateDate(bean.getCreate_date());
		merchant.setUpdateBy(bean.getUpdate_by());
		merchant.setUpdateDate(bean.getUpdate_date());
	}
}
