package com.android.pos.service;

import java.util.ArrayList;
import java.util.List;

import com.android.pos.Constant;
import com.android.pos.dao.Transactions;
import com.android.pos.dao.TransactionsDao;
import com.android.pos.model.TransactionsBean;
import com.android.pos.model.SyncStatusBean;
import com.android.pos.util.BeanUtil;
import com.android.pos.util.DbUtil;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class TransactionsDaoService {
	
	private TransactionsDao transactionsDao = DbUtil.getSession().getTransactionsDao();
	
	public void addTransactions(Transactions transactions) {
		
		transactionsDao.insert(transactions);
	}
	
	public void updateTransactions(Transactions transactions) {
		
		transactionsDao.update(transactions);
	}
	
	public void deleteTransactions(Transactions transactions) {

		Transactions entity = transactionsDao.load(transactions.getId());
		entity.setStatus(Constant.STATUS_DELETED);
		entity.setUploadStatus(Constant.STATUS_YES);
		transactionsDao.update(entity);
	}
	
	public Transactions getTransactions(Long id) {
		
		return transactionsDao.load(id);
	}
	
	public List<TransactionsBean> getTransactionsForUpload() {

		QueryBuilder<Transactions> qb = transactionsDao.queryBuilder();
		qb.where(TransactionsDao.Properties.UploadStatus.eq(Constant.STATUS_YES)).orderAsc(TransactionsDao.Properties.Id);
		
		Query<Transactions> q = qb.build();
		
		ArrayList<TransactionsBean> transactionsBeans = new ArrayList<TransactionsBean>();
		
		for (Transactions prdGroup : q.list()) {
			
			transactionsBeans.add(BeanUtil.getBean(prdGroup));
		}
		
		return transactionsBeans;
	}
	
	public void updateTransactions(List<TransactionsBean> transactionss) {
		
		for (TransactionsBean bean : transactionss) {
			
			boolean isAdd = false;
			
			Transactions transactions = transactionsDao.load(bean.getRemote_id());
			
			if (transactions == null) {
				transactions = new Transactions();
				isAdd = true;
			}
			
			BeanUtil.updateBean(transactions, bean);
			
			if (isAdd) {
				transactionsDao.insert(transactions);
			} else {
				transactionsDao.update(transactions);
			}
		} 
	}
	
	public void updateTransactionsStatus(List<SyncStatusBean> syncStatusBeans) {
		
		for (SyncStatusBean bean : syncStatusBeans) {
			
			Transactions transactions = transactionsDao.load(bean.getRemoteId());
			
			if (SyncStatusBean.SUCCESS.equals(bean.getStatus())) {
				transactions.setUploadStatus(Constant.STATUS_NO);
				transactionsDao.update(transactions);
			}
		} 
	}
}
