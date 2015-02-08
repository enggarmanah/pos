package com.android.pos.service;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.dao.Product;
import com.android.pos.dao.ProductDao;
import com.android.pos.model.ProductBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class ProductDaoService {
	
	private ProductDao productDao = DbUtil.getSession().getProductDao();
	
	public void addProduct(Product product) {
		
		productDao.insert(product);
	}
	
	public void updateProduct(Product product) {
		
		productDao.update(product);
	}
	
	public void deleteProduct(Product product) {

		Product entity = productDao.load(product.getId());
		entity.setStatus(Constant.STATUS_DELETED);
		entity.setUploadStatus(Constant.STATUS_YES);
		productDao.update(entity);
	}
	
	public Product getProduct(Long id) {
		
		return productDao.load(id);
	}
	
	public List<Product> getProducts(String query) {

		QueryBuilder<Product> qb = productDao.queryBuilder();
		qb.where(ProductDao.Properties.Name.like("%" + query + "%"), 
				ProductDao.Properties.Status.notEq(Constant.STATUS_DELETED)).orderAsc(ProductDao.Properties.Name);

		Query<Product> q = qb.build();
		List<Product> list = q.list();

		return list;
	}
	
	public List<ProductBean> getProductsForUpload() {

		QueryBuilder<Product> qb = productDao.queryBuilder();
		qb.where(ProductDao.Properties.UploadStatus.eq(Constant.STATUS_YES)).orderAsc(ProductDao.Properties.Name);
		
		Query<Product> q = qb.build();
		
		ArrayList<ProductBean> productBeans = new ArrayList<ProductBean>();
		
		for (Product prdGroup : q.list()) {
			
			productBeans.add(BeanUtil.getBean(prdGroup));
		}
		
		return productBeans;
	}
	
	public void updateProducts(List<ProductBean> products) {
		
		for (ProductBean bean : products) {
			
			boolean isAdd = false;
			
			Product product = productDao.load(bean.getRemote_id());
			
			if (product == null) {
				product = new Product();
				isAdd = true;
			}
			
			BeanUtil.updateBean(product, bean);
			
			if (isAdd) {
				productDao.insert(product);
			} else {
				productDao.update(product);
			}
		} 
	}
	
	public void updateProductStatus(List<SyncStatusBean> syncStatusBeans) {
		
		for (SyncStatusBean bean : syncStatusBeans) {
			
			Product product = productDao.load(bean.getRemoteId());
			
			if (SyncStatusBean.SUCCESS.equals(bean.getStatus())) {
				product.setUploadStatus(Constant.STATUS_NO);
				productDao.update(product);
			}
		} 
	}
}
