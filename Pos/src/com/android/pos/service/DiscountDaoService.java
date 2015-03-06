package com.android.pos.service;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.dao.Discount;
import com.android.pos.dao.DiscountDao;
import com.android.pos.model.DiscountBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.DbUtil;
import com.android.pos.util.MerchantUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class DiscountDaoService {
	
	private static DiscountDao discountDao = DbUtil.getSession().getDiscountDao();
	
	public void addDiscount(Discount discount) {
		
		discountDao.insert(discount);
	}
	
	public void updateDiscount(Discount discount) {
		
		discountDao.update(discount);
	}
	
	public void deleteDiscount(Discount discount) {

		Discount entity = discountDao.load(discount.getId());
		entity.setStatus(Constant.STATUS_DELETED);
		entity.setUploadStatus(Constant.STATUS_YES);
		discountDao.update(entity);
	}
	
	public Discount getDiscount(Long id) {
		
		return discountDao.load(id);
	}
	
	public List<Discount> getDiscounts() {

		QueryBuilder<Discount> qb = discountDao.queryBuilder();
		qb.where(DiscountDao.Properties.MerchantId.eq(MerchantUtil.getMerchantId()),
				DiscountDao.Properties.Status.notEq(Constant.STATUS_DELETED)).orderAsc(DiscountDao.Properties.Percentage);

		Query<Discount> q = qb.build();
		List<Discount> list = q.list();

		return list;
	}
	
	public List<Discount> getDiscounts(String query) {

		QueryBuilder<Discount> qb = discountDao.queryBuilder();
		qb.where(DiscountDao.Properties.MerchantId.eq(MerchantUtil.getMerchantId()),
				DiscountDao.Properties.Name.like("%" + query + "%"), 
				DiscountDao.Properties.Status.notEq(Constant.STATUS_DELETED)).orderAsc(DiscountDao.Properties.Name);

		Query<Discount> q = qb.build();
		List<Discount> list = q.list();

		return list;
	}
	
	public List<DiscountBean> getDiscountsForUpload() {

		QueryBuilder<Discount> qb = discountDao.queryBuilder();
		qb.where(DiscountDao.Properties.MerchantId.eq(MerchantUtil.getMerchantId()),
				DiscountDao.Properties.UploadStatus.eq(Constant.STATUS_YES)).orderAsc(DiscountDao.Properties.Name);
		
		Query<Discount> q = qb.build();
		
		ArrayList<DiscountBean> discountBeans = new ArrayList<DiscountBean>();
		
		for (Discount prdGroup : q.list()) {
			
			discountBeans.add(BeanUtil.getBean(prdGroup));
		}
		
		return discountBeans;
	}
	
	public void updateDiscounts(List<DiscountBean> discounts) {
		
		for (DiscountBean bean : discounts) {
			
			boolean isAdd = false;
			
			Discount discount = discountDao.load(bean.getRemote_id());
			
			if (discount == null) {
				discount = new Discount();
				isAdd = true;
			}
			
			BeanUtil.updateBean(discount, bean);
			
			if (isAdd) {
				discountDao.insert(discount);
			} else {
				discountDao.update(discount);
			}
		} 
	}
	
	public void updateDiscountStatus(List<SyncStatusBean> syncStatusBeans) {
		
		for (SyncStatusBean bean : syncStatusBeans) {
			
			Discount discount = discountDao.load(bean.getRemoteId());
			
			if (SyncStatusBean.SUCCESS.equals(bean.getStatus())) {
				discount.setUploadStatus(Constant.STATUS_NO);
				discountDao.update(discount);
			}
		} 
	}
}
