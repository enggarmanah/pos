package com.android.pos.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.pos.Constant;
import com.android.pos.dao.Inventory;
import com.android.pos.dao.InventoryDao;
import com.android.pos.model.InventoryBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class InventoryDaoService {
	
	private InventoryDao inventoryDao = DbUtil.getSession().getInventoryDao();
	
	public void addInventory(Inventory inventory) {
		
		inventoryDao.insert(inventory);
	}
	
	public void updateInventory(Inventory inventory) {
		
		inventoryDao.update(inventory);
	}
	
	public void deleteInventory(Inventory inventory) {

		Inventory entity = inventoryDao.load(inventory.getId());
		entity.setStatus(Constant.STATUS_DELETED);
		entity.setUploadStatus(Constant.STATUS_YES);
		inventoryDao.update(entity);
	}
	
	public Inventory getInventory(Long id) {
		
		return inventoryDao.load(id);
	}
	
	public List<Inventory> getInventories(String query, int lastIndex) {

		SQLiteDatabase db = DbUtil.getDb();
		
		String queryStr = CommonUtil.getSqlLikeString(query);
		String status = Constant.STATUS_DELETED;
		String limit = Constant.QUERY_LIMIT;
		String lastIdx = String.valueOf(lastIndex);
		
		Cursor cursor = db.rawQuery("SELECT _id "
				+ " FROM inventory "
				+ " WHERE (bill_reference_no like ? OR product_name like ? OR supplier_name like ? OR remarks like ? ) AND status <> ? "
				+ " ORDER BY delivery_date DESC LIMIT ? OFFSET ? ",
				new String[] { queryStr, queryStr, queryStr, queryStr, status, limit, lastIdx});
		
		List<Inventory> list = new ArrayList<Inventory>();
		
		while(cursor.moveToNext()) {
			
			Long id = cursor.getLong(0);
			Inventory item = getInventory(id);
			list.add(item);
		}

		return list;
	}
	
	public List<InventoryBean> getInventoriesForUpload() {

		QueryBuilder<Inventory> qb = inventoryDao.queryBuilder();
		qb.where(InventoryDao.Properties.UploadStatus.eq(Constant.STATUS_YES)).orderAsc(InventoryDao.Properties.DeliveryDate);
		
		Query<Inventory> q = qb.build();
		
		ArrayList<InventoryBean> inventoryBeans = new ArrayList<InventoryBean>();
		
		for (Inventory inventory : q.list()) {
			
			inventoryBeans.add(BeanUtil.getBean(inventory));
		}
		
		return inventoryBeans;
	}
	
	public void updateInventories(List<InventoryBean> inventorys) {
		
		for (InventoryBean bean : inventorys) {
			
			boolean isAdd = false;
			
			Inventory inventory = inventoryDao.load(bean.getRemote_id());
			
			if (inventory == null) {
				inventory = new Inventory();
				isAdd = true;
			}
			
			BeanUtil.updateBean(inventory, bean);
			
			if (isAdd) {
				inventoryDao.insert(inventory);
			} else {
				inventoryDao.update(inventory);
			}
		} 
	}
	
	public void updateInventoryStatus(List<SyncStatusBean> syncStatusBeans) {
		
		for (SyncStatusBean bean : syncStatusBeans) {
			
			Inventory inventory = inventoryDao.load(bean.getRemoteId());
			
			if (SyncStatusBean.SUCCESS.equals(bean.getStatus())) {
				inventory.setUploadStatus(Constant.STATUS_NO);
				inventoryDao.update(inventory);
			}
		} 
	}
}
