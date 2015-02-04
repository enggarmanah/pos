package com.android.pos.sync;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.dao.Discount;
import com.android.pos.dao.DiscountDao;
import com.android.pos.model.DiscountBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class DiscountDataProvider {
	
	private DiscountDao discountDao = DbUtil.getSession().getDiscountDao();
	
	public List<DiscountBean> getDiscountsForUpload() {

		QueryBuilder<Discount> qb = discountDao.queryBuilder();
		qb.where(DiscountDao.Properties.UploadStatus.eq(Constant.STATUS_YES)).orderAsc(DiscountDao.Properties.Name);
		
		Query<Discount> q = qb.build();
		
		ArrayList<DiscountBean> prodGroupBeans = new ArrayList<DiscountBean>();
		
		for (Discount prdGroup : q.list()) {
			
			//prodGroupBeans.add(BeanUtil.getBean(prdGroup));
		}
		
		return prodGroupBeans;
	}
	
	public void updateDiscounts(List<DiscountBean> discounts) {
		
		for (DiscountBean bean : discounts) {
			
			Discount discount = discountDao.load(bean.getRemote_id());
			
			if (discount == null) {
				discount = new Discount();
			}
			
			//BeanUtil.updateBean(discount, bean);
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