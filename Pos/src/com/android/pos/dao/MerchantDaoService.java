package com.android.pos.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.pos.Constant;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.MerchantDao;
import com.android.pos.model.MerchantBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class MerchantDaoService {
	
	private MerchantDao merchantDao = DbUtil.getSession().getMerchantDao();
	
	public void addMerchant(Merchant merchant) {
		
		System.out.println("add merchant : " + DbUtil.getDb());
		merchantDao.insert(merchant);
	}
	
	public void updateMerchant(Merchant merchant) {
		
		System.out.println("update merchant : " + DbUtil.getDb());
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
	
	public Merchant getMerchantByLoginId(String loginId) {
		
		QueryBuilder<Merchant> qb = merchantDao.queryBuilder();
		qb.where(MerchantDao.Properties.LoginId.eq(loginId));

		Query<Merchant> q = qb.build();
		Merchant merchant = q.unique();

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
	
	public List<Merchant> getMerchants(String query, int lastIndex) {

		SQLiteDatabase db = DbUtil.getDb();
		
		String queryStr = "%" + CommonUtil.getNvlString(query) + "%";
		String status = Constant.STATUS_DELETED;
		String limit = Constant.QUERY_LIMIT;
		String lastIdx = String.valueOf(lastIndex);
		
		Cursor cursor = db.rawQuery("SELECT _id "
				+ " FROM merchant "
				+ " WHERE name like ? AND status <> ? "
				+ " ORDER BY name LIMIT ? OFFSET ? ",
				new String[] { queryStr, status, limit, lastIdx});
		
		List<Merchant> list = new ArrayList<Merchant>();
		
		while(cursor.moveToNext()) {
			
			Long id = cursor.getLong(0);
			Merchant item = getMerchant(id);
			list.add(item);
		}

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
				addMerchant(merchant);
			} else {
				updateMerchant(merchant);
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