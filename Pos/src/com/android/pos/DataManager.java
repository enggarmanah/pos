package com.android.pos;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.dao.ProductGroup;
import com.android.pos.dao.ProductGroupDao;
import com.android.pos.model.ProductGroupBean;
import com.android.pos.model.SyncStatusBean;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class DataManager {
	
	private ProductGroupDao productGroupDao = DbHelper.getSession().getProductGroupDao();
	
	public List<ProductGroupBean> getProductGroupsForUpload() {

		QueryBuilder<ProductGroup> qb = productGroupDao.queryBuilder();
		qb.orderAsc(ProductGroupDao.Properties.Name);
		
		Query<ProductGroup> q = qb.build();
		
		ArrayList<ProductGroupBean> prodGroupBeans = new ArrayList<ProductGroupBean>();
		
		for (ProductGroup prdGroup : q.list()) {
			
			prodGroupBeans.add(BeanUtil.getBean(prdGroup));
		}
		
		return prodGroupBeans;
	}
	
	public void updateProductGroups(List<ProductGroupBean> productGroups) {
		
		for (ProductGroupBean bean : productGroups) {
			
			ProductGroup productGroup = productGroupDao.load(bean.getRemote_id());
			
			if (productGroup == null) {
				productGroup = new ProductGroup();
			}
			
			BeanUtil.updateBean(productGroup, bean);
		} 
	}
	
	public void updateProductGroupStatus(List<SyncStatusBean> syncStatusBeans) {
		
		for (SyncStatusBean bean : syncStatusBeans) {
			
			ProductGroup productGroup = productGroupDao.load(bean.getRemoteId());
			
			if (SyncStatusBean.SUCCESS.equals(bean.getStatus())) {
				productGroup.setUploadStatus(Constant.STATUS_NO);
			}
		} 
	}
}
