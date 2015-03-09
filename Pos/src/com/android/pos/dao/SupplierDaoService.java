package com.android.pos.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.pos.Constant;
import com.android.pos.dao.Supplier;
import com.android.pos.dao.SupplierDao;
import com.android.pos.model.SupplierBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class SupplierDaoService {
	
	private SupplierDao supplierDao = DbUtil.getSession().getSupplierDao();
	
	public void addSupplier(Supplier supplier) {
		
		supplierDao.insert(supplier);
	}
	
	public void updateSupplier(Supplier supplier) {
		
		supplierDao.update(supplier);
	}
	
	public void deleteSupplier(Supplier supplier) {

		Supplier entity = supplierDao.load(supplier.getId());
		entity.setStatus(Constant.STATUS_DELETED);
		entity.setUploadStatus(Constant.STATUS_YES);
		supplierDao.update(entity);
	}
	
	public Supplier getSupplier(Long id) {
		
		return supplierDao.load(id);
	}
	
	public List<Supplier> getSuppliers(String query, int lastIndex) {

		SQLiteDatabase db = DbUtil.getDb();
		
		String queryStr = "%" + CommonUtil.getNvlString(query) + "%";
		String status = Constant.STATUS_DELETED;
		String limit = Constant.QUERY_LIMIT;
		String lastIdx = String.valueOf(lastIndex);
		
		Cursor cursor = db.rawQuery("SELECT _id "
				+ " FROM supplier "
				+ " WHERE name like ? AND status <> ? "
				+ " ORDER BY name LIMIT ? OFFSET ? ",
				new String[] { queryStr, status, limit, lastIdx});
		
		List<Supplier> list = new ArrayList<Supplier>();
		
		while(cursor.moveToNext()) {
			
			Long id = cursor.getLong(0);
			Supplier item = getSupplier(id);
			list.add(item);
		}

		return list;
	}
	
	public List<SupplierBean> getSuppliersForUpload() {

		QueryBuilder<Supplier> qb = supplierDao.queryBuilder();
		qb.where(SupplierDao.Properties.UploadStatus.eq(Constant.STATUS_YES)).orderAsc(SupplierDao.Properties.Name);
		
		Query<Supplier> q = qb.build();
		
		ArrayList<SupplierBean> supplierBeans = new ArrayList<SupplierBean>();
		
		for (Supplier supplier : q.list()) {
			
			supplierBeans.add(BeanUtil.getBean(supplier));
		}
		
		return supplierBeans;
	}
	
	public void updateSuppliers(List<SupplierBean> suppliers) {
		
		for (SupplierBean bean : suppliers) {
			
			boolean isAdd = false;
			
			Supplier supplier = supplierDao.load(bean.getRemote_id());
			
			if (supplier == null) {
				supplier = new Supplier();
				isAdd = true;
			}
			
			BeanUtil.updateBean(supplier, bean);
			
			if (isAdd) {
				supplierDao.insert(supplier);
			} else {
				supplierDao.update(supplier);
			}
		} 
	}
	
	public void updateSupplierStatus(List<SyncStatusBean> syncStatusBeans) {
		
		for (SyncStatusBean bean : syncStatusBeans) {
			
			Supplier supplier = supplierDao.load(bean.getRemoteId());
			
			if (SyncStatusBean.SUCCESS.equals(bean.getStatus())) {
				supplier.setUploadStatus(Constant.STATUS_NO);
				supplierDao.update(supplier);
			}
		} 
	}
}
