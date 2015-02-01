package com.android.pos;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.dao.ProductGroup;
import com.android.pos.dao.ProductGroupDao;
import com.android.pos.model.ProductGroupBean;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class DataManager {
	
	private ProductGroupDao productGroupDao = DbHelper.getSession().getProductGroupDao();
	
	public List<ProductGroupBean> getProductGroups() {

		QueryBuilder<ProductGroup> qb = productGroupDao.queryBuilder();
		qb.orderAsc(ProductGroupDao.Properties.Name);
		
		Query<ProductGroup> q = qb.build();
		
		ArrayList<ProductGroupBean> prodGroupBeans = new ArrayList<ProductGroupBean>();
		
		for (ProductGroup prdGroup : q.list()) {
			
			prodGroupBeans.add(BeanUtil.getBean(prdGroup));
		}
		
		return prodGroupBeans;
	}
}
