package com.android.pos;

import com.android.pos.dao.ProductGroup;
import com.android.pos.model.ProductGroupBean;

public class BeanUtil {
	
	public static ProductGroupBean getBean(ProductGroup prdGroup) {
		
		ProductGroupBean bean = new ProductGroupBean();
		
		bean.setRemote_id(prdGroup.getId());
		bean.setName(prdGroup.getName());
		
		return bean;
	}
	
	public static void updateBean(ProductGroup productGroup, ProductGroupBean bean) {
		
		productGroup.setId(bean.getId());
		productGroup.setName(bean.getName());
		productGroup.setUploadStatus(Constant.STATUS_NO);
		productGroup.setCreateBy(bean.getCreate_by());
		productGroup.setCreateDate(bean.getCreate_date());
		productGroup.setUpdateBy(bean.getUpdate_by());
		productGroup.setUpdateDate(bean.getUpdate_date());
	}
}
