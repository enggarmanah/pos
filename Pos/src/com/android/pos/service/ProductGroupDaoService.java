package com.android.pos.service;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.dao.ProductGroup;
import com.android.pos.dao.ProductGroupDao;
import com.android.pos.model.ProductGroupBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.DbUtil;
import com.android.pos.util.MerchantUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class ProductGroupDaoService {
	
	private ProductGroupDao productGroupDao = DbUtil.getSession().getProductGroupDao();
	
	public void addProductGroup(ProductGroup productGroup) {
		
		productGroupDao.insert(productGroup);
	}
	
	public void updateProductGroup(ProductGroup productGroup) {
		
		productGroupDao.update(productGroup);
	}
	
	public void deleteProductGroup(ProductGroup productGroup) {

		ProductGroup entity = productGroupDao.load(productGroup.getId());
		entity.setStatus(Constant.STATUS_DELETED);
		entity.setUploadStatus(Constant.STATUS_YES);
		productGroupDao.update(entity);
	}
	
	public ProductGroup getProductGroup(Long id) {
		
		return productGroupDao.load(id);
	}
	
	public List<ProductGroup> getProductGroups() {

		QueryBuilder<ProductGroup> qb = productGroupDao.queryBuilder();
		qb.where(ProductGroupDao.Properties.MerchantId.eq(MerchantUtil.getMerchantId()),
				ProductGroupDao.Properties.Status.eq(Constant.STATUS_ACTIVE)).orderAsc(ProductGroupDao.Properties.Name);

		Query<ProductGroup> q = qb.build();
		List<ProductGroup> list = q.list();

		return list;
	}
	
	public List<ProductGroup> getProductGroups(String query) {

		QueryBuilder<ProductGroup> qb = productGroupDao.queryBuilder();
		qb.where(ProductGroupDao.Properties.MerchantId.eq(MerchantUtil.getMerchantId()),
				ProductGroupDao.Properties.Name.like("%" + query + "%"), 
				ProductGroupDao.Properties.Status.notEq(Constant.STATUS_DELETED)).orderAsc(ProductGroupDao.Properties.Name);

		Query<ProductGroup> q = qb.build();
		List<ProductGroup> list = q.list();

		return list;
	}
	
	public List<ProductGroupBean> getProductGroupsForUpload() {

		QueryBuilder<ProductGroup> qb = productGroupDao.queryBuilder();
		qb.where(ProductGroupDao.Properties.MerchantId.eq(MerchantUtil.getMerchantId()),
				ProductGroupDao.Properties.UploadStatus.eq(Constant.STATUS_YES)).orderAsc(ProductGroupDao.Properties.Name);
		
		Query<ProductGroup> q = qb.build();
		
		ArrayList<ProductGroupBean> productGroupBeans = new ArrayList<ProductGroupBean>();
		
		for (ProductGroup prdGroup : q.list()) {
			
			productGroupBeans.add(BeanUtil.getBean(prdGroup));
		}
		
		return productGroupBeans;
	}
	
	public void updateProductGroups(List<ProductGroupBean> productGroups) {
		
		for (ProductGroupBean bean : productGroups) {
			
			boolean isAdd = false;
			
			ProductGroup productGroup = productGroupDao.load(bean.getRemote_id());
			
			if (productGroup == null) {
				productGroup = new ProductGroup();
				isAdd = true;
			}
			
			BeanUtil.updateBean(productGroup, bean);
			
			if (isAdd) {
				productGroupDao.insert(productGroup);
			} else {
				productGroupDao.update(productGroup);
			}
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
