package com.app.posweb.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.model.Sync;
import com.app.posweb.server.model.TransactionItem;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.shared.Constant;

public class TransactionItemDao {
	
	SyncDao syncDao = new SyncDao();
	
	public TransactionItem syncTransactionItem(TransactionItem transactionItem) {
		
		TransactionItem obj = getTransactionItem(transactionItem);
		
		if (obj != null) {
			
			transactionItem.setId(obj.getId());
			transactionItem = updateTransactionItem(transactionItem);
			
		} else {
			
			transactionItem = addTransactionItem(transactionItem);
		}
		
		return transactionItem;
	}
	
	public TransactionItem addTransactionItem(TransactionItem bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		em.persist(bean);
		em.close();

		return bean;
	}
	
	public TransactionItem updateTransactionItem(TransactionItem bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		TransactionItem transactionItem = em.find(TransactionItem.class, bean.getId());
		transactionItem.setBean(bean);
		
		em.close();
		
		return transactionItem;
	}
	
	public TransactionItem getTransactionItem(TransactionItem transactionItem) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT ti FROM TransactionItem ti ");
		sql.append(" WHERE merchant_id = :merchantId ");
		sql.append(" AND remote_id = :remoteId ");
		
		TypedQuery<TransactionItem> query = em.createQuery(sql.toString(), TransactionItem.class);
		
		query.setParameter("merchantId", transactionItem.getMerchant_id());
		query.setParameter("remoteId", transactionItem.getRemote_id());
		
		TransactionItem result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	public List<TransactionItem> getTransactionItems(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_TRANSACTION_ITEM);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT ti FROM Transactions AS t, TransactionItem AS ti WHERE  "
				+ "	ti.merchant_id = :merchantId AND ti.transaction_id = t.remote_id AND ti.merchant_id = t.merchant_id AND t.sync_date > :lastSyncDate");
		
		sql.append(" ORDER BY ti.id");
		
		TypedQuery<TransactionItem> query = em.createQuery(sql.toString(), TransactionItem.class);

		query.setParameter("merchantId", sync.getMerchant_id());
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		List<TransactionItem> result = query.getResultList();
		
		em.close();

		return result;
	}
	
	public boolean hasUpdate(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_TRANSACTION_ITEM);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT COUNT(ti.id) FROM Transactions AS t, TransactionItem AS ti WHERE  "
				+ "	ti.merchant_id = :merchantId AND ti.transaction_id = t.remote_id AND ti.merchant_id = t.merchant_id AND t.sync_date > :lastSyncDate");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("merchantId", sync.getMerchant_id());
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		long count = (long) query.getSingleResult();
		
		em.close();

		return (count > 0);
	}
}