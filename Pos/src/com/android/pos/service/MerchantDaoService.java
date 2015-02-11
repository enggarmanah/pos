package com.android.pos.service;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.MerchantDao;
import com.android.pos.model.MerchantBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class MerchantDaoService {
	
	private MerchantDao merchantDao = DbUtil.getSession().getMerchantDao();
	
	public void addMerchant(Merchant merchant) {
		
		merchantDao.insert(merchant);
	}
	
	public void updateMerchant(Merchant merchant) {
		
		merchantDao.update(merchant);
	}
	
	public void deleteMerchant(Merchant merchant) {

		Merchant entity = merchantDao.load(merchant.getId());
		entity.setStatus(Constant.STATUS_DELETED);
		entity.setUploadStatus(Constant.STATUS_YES);
		merchantDao.update(entity);
	}
	
	public Merchant getMerchant(Long id) {
		
		return merchantDao.load(id);
	}
	
	public Merchant validateMerchant(String loginId, String password) {
		
		QueryBuilder<Merchant> qb = merchantDao.queryBuilder();
		qb.where(MerchantDao.Properties.LoginId.eq(loginId), MerchantDao.Properties.Password.eq(password));

		Query<Merchant> q = qb.build();
		Merchant merchant = q.unique();
		
		if (merchant != null) {
			
			merchant.setIsLogin(true);
			merchantDao.update(merchant);
		}
		
		return merchant;
	}
	
	public Merchant getActiveMerchant() {
		
		QueryBuilder<Merchant> qb = merchantDao.queryBuilder();
		qb.where(MerchantDao.Properties.IsLogin.eq(true));

		Query<Merchant> q = qb.build();
		Merchant merchant = q.unique();

		return merchant;
	}
	
	public void logoutMerchant(Merchant merchant) {
		
		merchant.setIsLogin(false);
		merchantDao.update(merchant);
	}
	
	public List<Merchant> getMerchants(String query) {

		QueryBuilder<Merchant> qb = merchantDao.queryBuilder();
		qb.where(MerchantDao.Properties.Name.like("%" + query + "%"), 
				MerchantDao.Properties.Status.notEq(Constant.STATUS_DELETED)).orderAsc(MerchantDao.Properties.Name);

		Query<Merchant> q = qb.build();
		List<Merchant> list = q.list();

		return list;
	}
	
	public List<MerchantBean> getMerchantsForUpload() {

		QueryBuilder<Merchant> qb = merchantDao.queryBuilder();
		qb.where(MerchantDao.Properties.UploadStatus.eq(Constant.STATUS_YES)).orderAsc(MerchantDao.Properties.Name);
		
		Query<Merchant> q = qb.build();
		
		ArrayList<MerchantBean> merchantBeans = new ArrayList<MerchantBean>();
		
		for (Merchant prdGroup : q.list()) {
			
			merchantBeans.add(BeanUtil.getBean(prdGroup));
		}
		
		return merchantBeans;
	}
	
	public void updateMerchants(List<MerchantBean> merchants) {
		
		for (MerchantBean bean : merchants) {
			
			boolean isAdd = false;
			
			Merchant merchant = merchantDao.load(bean.getRemote_id());
			
			if (merchant == null) {
				merchant = new Merchant();
				isAdd = true;
			}
			
			BeanUtil.updateBean(merchant, bean);
			
			if (isAdd) {
				merchantDao.insert(merchant);
			} else {
				merchantDao.update(merchant);
			}
		} 
	}
	
	public void updateMerchantStatus(List<SyncStatusBean> syncStatusBeans) {
		
		for (SyncStatusBean bean : syncStatusBeans) {
			
			Merchant merchant = merchantDao.load(bean.getRemoteId());
			
			if (SyncStatusBean.SUCCESS.equals(bean.getStatus())) {
				merchant.setUploadStatus(Constant.STATUS_NO);
				merchantDao.update(merchant);
			}
		} 
	}
}
