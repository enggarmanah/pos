package com.app.posweb.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.model.Sync;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.Transactions;
import com.app.posweb.shared.Constant;

public class TransactionsDao {
	
	SyncDao syncDao = new SyncDao();
	
	public Transactions syncTransactions(Transactions transactions) {
		
		Transactions obj = getTransactions(transactions);
		
		if (obj != null) {
			
			transactions.setId(obj.getId());
			transactions = updateTransactions(transactions);
			
		} else {
			
			transactions = addTransactions(transactions);
		}
		
		return transactions;
	}
	
	public Transactions addTransactions(Transactions bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		em.persist(bean);
		em.close();

		return bean;
	}
	
	public Transactions updateTransactions(Transactions bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Transactions transactions = em.find(Transactions.class, bean.getId());
		transactions.setBean(bean);
		
		em.close();
		
		return transactions;
	}
	
	public Transactions getTransactions(Transactions transactions) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT t FROM Transactions t ");
		sql.append(" WHERE merchant_id = :merchantId ");
		sql.append(" AND remote_id = :remoteId ");
		
		TypedQuery<Transactions> query = em.createQuery(sql.toString(), Transactions.class);
		
		query.setParameter("merchantId", transactions.getMerchant_id());
		query.setParameter("remoteId", transactions.getRemote_id());
		
		Transactions result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	public List<Transactions> getTransactions(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_TRANSACTIONS);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT t FROM Transactions t WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		sql.append(" ORDER BY t.id");
		
		TypedQuery<Transactions> query = em.createQuery(sql.toString(), Transactions.class);

		query.setParameter("merchantId", sync.getMerchant_id());
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		List<Transactions> result = query.setFirstResult((int) syncRequest.getIndex()).setMaxResults(Constant.SYNC_RECORD_LIMIT).getResultList();
		
		em.close();

		return result;
	}
	
	public boolean hasUpdate(SyncRequest syncRequest) {
		
		return getTransactionsCount(syncRequest) > 0;
	}
	
	public long getTransactionsCount(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_TRANSACTIONS);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT COUNT(t.id) FROM Transactions t WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("merchantId", sync.getMerchant_id());
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		long count = (long) query.getSingleResult();
		
		em.close();

		return count;
	}
}