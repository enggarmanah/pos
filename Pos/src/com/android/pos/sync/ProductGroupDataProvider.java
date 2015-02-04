package com.android.pos.sync;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.dao.ProductGroup;
import com.android.pos.dao.ProductGroupDao;
import com.android.pos.model.ProductGroupBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class ProductGroupDataProvider {
	
	private ProductGroupDao productGroupDao = DbUtil.getSession().getProductGroupDao();
	
	public List<ProductGroupBean> getProductGroupsForUpload() {

		QueryBuilder<ProductGroup> qb = productGroupDao.queryBuilder();
		qb.where(ProductGroupDao.Properties.UploadStatus.eq(Constant.STATUS_YES)).orderAsc(ProductGroupDao.Properties.Name);
		
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
				productGroupDao.update(productGroup);
			}
		} 
	}
}
