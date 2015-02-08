package com.android.pos.service;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.dao.TransactionItem;
import com.android.pos.dao.TransactionItemDao;
import com.android.pos.model.TransactionItemBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.DbUtil;
import com.android.pos.util.MerchantUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class TransactionItemDaoService {
	
	private TransactionItemDao transactionItemDao = DbUtil.getSession().getTransactionItemDao();
	
	public void addTransactionItem(TransactionItem transactionItem) {
		
		transactionItemDao.insert(transactionItem);
	}
	
	public void updateTransactionItem(TransactionItem transactionItem) {
		
		transactionItemDao.update(transactionItem);
	}
	
	public TransactionItem getTransactionItem(Long id) {
		
		return transactionItemDao.load(id);
	}
	
	public List<TransactionItemBean> getTransactionItemsForUpload() {
		
		Long merchantId = MerchantUtil.getMerchant().getId();
		
		QueryBuilder<TransactionItem> qb = transactionItemDao.queryBuilder();
		qb.where(TransactionItemDao.Properties.MerchantId.eq(merchantId),
				TransactionItemDao.Properties.UploadStatus.eq(Constant.STATUS_YES)).orderAsc(TransactionItemDao.Properties.Id);
		
		Query<TransactionItem> q = qb.build();
		
		ArrayList<TransactionItemBean> transactionItemBeans = new ArrayList<TransactionItemBean>();
		
		for (TransactionItem transactionItem : q.list()) {
			
			transactionItemBeans.add(BeanUtil.getBean(transactionItem));
		}
		
		return transactionItemBeans;
	}
	
	public void updateTransactionItems(List<TransactionItemBean> transactionItems) {
		
		for (TransactionItemBean bean : transactionItems) {
			
			boolean isAdd = false;
			
			TransactionItem transactionItem = transactionItemDao.load(bean.getRemote_id());
			
			if (transactionItem == null) {
				transactionItem = new TransactionItem();
				isAdd = true;
			}
			
			BeanUtil.updateBean(transactionItem, bean);
			
			if (isAdd) {
				transactionItemDao.insert(transactionItem);
			} else {
				transactionItemDao.update(transactionItem);
			}
		} 
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
}
