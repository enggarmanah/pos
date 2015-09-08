package com.android.pos.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.pos.CodeBean;
import com.android.pos.Constant;
import com.android.pos.dao.MerchantAccess;
import com.android.pos.dao.MerchantAccessDao;
import com.android.pos.model.MerchantAccessBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.CodeUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class MerchantAccessDaoService {
	
	private MerchantAccessDao merchantAccessDao = DbUtil.getSession().getMerchantAccessDao();
	
	public void addMerchantAccess(MerchantAccess merchantAccess) {
		
		if (CommonUtil.isEmpty(merchantAccess.getRefId())) {
			merchantAccess.setRefId(CommonUtil.generateRefId());
		}
		
		merchantAccessDao.insert(merchantAccess);
	}
	
	public void updateMerchantAccess(MerchantAccess merchantAccess) {
		
		merchantAccessDao.update(merchantAccess);
	}
	
	public void updateMerchantAccess(List<MerchantAccess> merchantAccesses) {
		
		for (MerchantAccess merchantAccess : merchantAccesses) {
			
			if (merchantAccess.getId() == null) {
				addMerchantAccess(merchantAccess);
			} else {
				updateMerchantAccess(merchantAccess);
			}
		}
	}
	
	public void deleteMerchantAccess(MerchantAccess merchantAccess) {

		MerchantAccess entity = merchantAccessDao.load(merchantAccess.getId());
		entity.setStatus(Constant.STATUS_DELETED);
		entity.setUploadStatus(Constant.STATUS_YES);
		merchantAccessDao.update(entity);
	}
	
	public MerchantAccess getMerchantAccess(Long id) {
		
		MerchantAccess merchantAccess = merchantAccessDao.load(id);
		
		if (merchantAccess != null) {
			merchantAccessDao.detach(merchantAccess);
		}
		
		return merchantAccess;
	}
	
	public List<MerchantAccess> getMerchantAccessList(Long merchantId) {

		QueryBuilder<MerchantAccess> qb = merchantAccessDao.queryBuilder();
		
		if (merchantId == null) {
			merchantId = Long.valueOf(-1);
		}
		
		qb.where(MerchantAccessDao.Properties.MerchantId.eq(merchantId)).orderAsc(MerchantAccessDao.Properties.Id);
		
		Query<MerchantAccess> q = qb.build();
		
		ArrayList<MerchantAccess> merchantAccessList = new ArrayList<MerchantAccess>();
		HashMap<String, MerchantAccess> merchantAccessMap = new HashMap<String, MerchantAccess>();
		
		for (MerchantAccess merchantAccess : q.list()) {
			merchantAccessMap.put(merchantAccess.getCode(), merchantAccess);
		}
		
		for (CodeBean codeBean : CodeUtil.getModuleAccesses()) {
			
			MerchantAccess merchantAccess = merchantAccessMap.get(codeBean.getCode());
			
			if (merchantAccess == null) {
				
				merchantAccess = new MerchantAccess();
				merchantAccess.setMerchantId(merchantId);
				merchantAccess.setCode(codeBean.getCode());
				merchantAccess.setName(codeBean.getOrder() + " " + codeBean.getLabel());
				merchantAccess.setStatus(Constant.STATUS_NO);
			}
			
			merchantAccessList.add(merchantAccess);
		}
		
		return merchantAccessList;
	}
	
	public List<MerchantAccessBean> getMerchantAccessesForUpload() {

		QueryBuilder<MerchantAccess> qb = merchantAccessDao.queryBuilder();
		qb.where(MerchantAccessDao.Properties.UploadStatus.eq(Constant.STATUS_YES)).orderAsc(MerchantAccessDao.Properties.Name);
		
		Query<MerchantAccess> q = qb.build();
		
		ArrayList<MerchantAccessBean> merchantAccessBeans = new ArrayList<MerchantAccessBean>();
		
		for (MerchantAccess merchantAccess : q.list()) {
			
			merchantAccessBeans.add(BeanUtil.getBean(merchantAccess));
		}
		
		return merchantAccessBeans;
	}
	
	public void updateMerchantAccesses(List<MerchantAccessBean> merchantAccesss) {
		
		DbUtil.getDb().beginTransaction();
		
		List<MerchantAccessBean> shiftedBeans = new ArrayList<MerchantAccessBean>();
		
		for (MerchantAccessBean bean : merchantAccesss) {
			
			boolean isAdd = false;
			
			MerchantAccess merchantAccess = merchantAccessDao.load(bean.getRemote_id());
			
			if (merchantAccess == null) {
				merchantAccess = new MerchantAccess();
				isAdd = true;
				
			} else if (!CommonUtil.compareString(merchantAccess.getRefId(), bean.getRef_id())) {
				MerchantAccessBean shiftedBean = BeanUtil.getBean(merchantAccess);
				shiftedBeans.add(shiftedBean);
			}
			
			BeanUtil.updateBean(merchantAccess, bean);
			
			Long oldId = merchantAccess.getId();
			Long newId = null;
			
			if (isAdd) {
				newId = merchantAccessDao.insert(merchantAccess);
			} else {
				merchantAccessDao.update(merchantAccess);
			}
			
			System.out.println(oldId + " -> " + newId);
		}
		
		for (MerchantAccessBean bean : shiftedBeans) {
			
			MerchantAccess merchantAccess = new MerchantAccess();
			BeanUtil.updateBean(merchantAccess, bean);
			
			merchantAccess.setId(null);
			merchantAccess.setUploadStatus(Constant.STATUS_YES);
			
			merchantAccessDao.insert(merchantAccess);
		}
		
		DbUtil.getDb().setTransactionSuccessful();
		DbUtil.getDb().endTransaction();
	}
	
	public void updateMerchantAccessStatus(List<SyncStatusBean> syncStatusBeans) {
		
		for (SyncStatusBean bean : syncStatusBeans) {
			
			MerchantAccess merchantAccess = merchantAccessDao.load(bean.getRemoteId());
			
			if (SyncStatusBean.SUCCESS.equals(bean.getStatus())) {
				merchantAccess.setUploadStatus(Constant.STATUS_NO);
				merchantAccessDao.update(merchantAccess);
			}
		} 
	}
	
	public boolean hasUpdate() {
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT COUNT(_id) FROM merchant_access WHERE upload_status = 'Y'", null);
			
		cursor.moveToFirst();
			
		Long count = cursor.getLong(0);
		
		cursor.close();
		
		return (count > 0);
	}
}
