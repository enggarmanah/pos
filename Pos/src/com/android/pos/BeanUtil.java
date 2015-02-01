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
}
