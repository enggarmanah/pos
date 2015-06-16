package com.android.pos.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.pos.Constant;
import com.android.pos.dao.Customer;
import com.android.pos.dao.TransactionItem;
import com.android.pos.dao.TransactionItemDao;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.model.TransactionItemBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.CommonUtil;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class TransactionItemDaoService {
	
	private TransactionItemDao transactionItemDao = DbUtil.getSession().getTransactionItemDao();
	
	public void addTransactionItem(TransactionItem transactionItem) {
		
		if (CommonUtil.isEmpty(transactionItem.getRefId())) {
			transactionItem.setRefId(CommonUtil.generateRefId());
		}
		
		transactionItemDao.insert(transactionItem);
	}
	
	public void updateTransactionItem(TransactionItem transactionItem) {
		
		transactionItemDao.update(transactionItem);
	}
	
	public TransactionItem getTransactionItem(Long id) {
		
		return transactionItemDao.load(id);
	}
	
	public List<TransactionItemBean> getTransactionItemsForUpload() {
		
		QueryBuilder<TransactionItem> qb = transactionItemDao.queryBuilder();
		qb.where(TransactionItemDao.Properties.UploadStatus.eq(Constant.STATUS_YES)).orderAsc(TransactionItemDao.Properties.Id);
		
		Query<TransactionItem> q = qb.build();
		
		ArrayList<TransactionItemBean> transactionItemBeans = new ArrayList<TransactionItemBean>();
		
		for (TransactionItem transactionItem : q.list()) {
			
			transactionItemBeans.add(BeanUtil.getBean(transactionItem));
		}
		
		return transactionItemBeans;
	}
	
	public List<TransactionItem> getTransactionItemsByTransactionId(Long transactionId) {
		
		QueryBuilder<TransactionItem> qb = transactionItemDao.queryBuilder();
		qb.where(TransactionItemDao.Properties.TransactionId.eq(transactionId)).orderAsc(TransactionItemDao.Properties.Id);
		
		Query<TransactionItem> q = qb.build();
		
		return q.list();
	}
	
	public void updateTransactionItems(List<TransactionItemBean> transactionItems) {
		
		DbUtil.getDb().beginTransaction();
		
		List<TransactionItemBean> shiftedBeans = new ArrayList<TransactionItemBean>();
		
		for (TransactionItemBean bean : transactionItems) {
			
			boolean isAdd = false;
			
			TransactionItem transactionItem = transactionItemDao.load(bean.getRemote_id());
			
			if (transactionItem == null) {
				transactionItem = new TransactionItem();
				isAdd = true;
			
			} else if (!CommonUtil.compareString(transactionItem.getRefId(), bean.getRef_id())) {
				TransactionItemBean shiftedBean = BeanUtil.getBean(transactionItem);
				shiftedBeans.add(shiftedBean);
			}
			
			BeanUtil.updateBean(transactionItem, bean);
			
			if (isAdd) {
				transactionItemDao.insert(transactionItem);
			} else {
				transactionItemDao.update(transactionItem);
			}
		} 
		
		for (TransactionItemBean bean : shiftedBeans) {
			
			TransactionItem transactionItem = new TransactionItem();
			BeanUtil.updateBean(transactionItem, bean);
			
			transactionItem.setId(null);
			transactionItem.setUploadStatus(Constant.STATUS_YES);
			
			transactionItemDao.insert(transactionItem);
		}
		
		DbUtil.getDb().setTransactionSuccessful();
		DbUtil.getDb().endTransaction();
	}
	
	public void updateTransactionItemStatus(List<SyncStatusBean> syncStatusBeans) {
		
		for (SyncStatusBean bean : syncStatusBeans) {
			
			TransactionItem transactionItem = transactionItemDao.load(bean.getRemoteId());
			
			if (SyncStatusBean.SUCCESS.equals(bean.getStatus())) {
				transactionItem.setUploadStatus(Constant.STATUS_NO);
				transactionItemDao.update(transactionItem);
			}
		} 
	}
	
	public boolean hasUpdate() {
		
		SQLiteDatabase db = DbUtil.getDb();
		
		Cursor cursor = db.rawQuery("SELECT COUNT(_id) FROM transaction_item WHERE upload_status = 'Y'", null);
			
		cursor.moveToFirst();
			
		Long count = cursor.getLong(0);
		
		cursor.close();
		
		return (count > 0);
	}
	
	public List<TransactionItem> getCustomerTransactionItems(Customer customer, String query, int lastIndex) {

		SQLiteDatabase db = DbUtil.getDb();
		
		String customerId = String.valueOf(customer.getId());
		String queryStr = "%" + CommonUtil.getNvlString(query) + "%";
		String status = Constant.STATUS_DELETED;
		String limit = Constant.QUERY_LIMIT;
		String lastIdx = String.valueOf(lastIndex);
		
		Cursor cursor = db.rawQuery("SELECT "
				+ "   ti._id "
				+ " FROM "
				+ "   transactions t, transaction_item ti, product p, product_group pg "
				+ " WHERE "
				+ "   t._id = ti.transaction_id AND "
				+ "   p._id = ti.product_id AND "
				+ "   pg._id = p.product_group_id AND "
				+ "   t.customer_id = ? AND "
				+ "   (ti.product_name like ? OR pg.name like ?) AND "
				+ "   t.status <> ? "
				+ " ORDER BY "
				+ "   t.transaction_date DESC "
				+ " LIMIT ? OFFSET ? ",
				new String[] { customerId, queryStr, queryStr, status, limit, lastIdx});
		
		List<TransactionItem> list = new ArrayList<TransactionItem>();
		
		while(cursor.moveToNext()) {
			
			TransactionItem transactionItem = transactionItemDao.load(cursor.getLong(0));
			
			list.add(transactionItem);
		}
		
		cursor.close();
		
		return list;
	}
}
