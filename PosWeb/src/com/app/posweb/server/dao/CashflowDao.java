package com.app.posweb.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.model.Cashflow;
import com.app.posweb.server.model.Sync;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.shared.Constant;

public class CashflowDao {
	
	SyncDao syncDao = new SyncDao();
	
	public Cashflow syncCashflow(Cashflow cashflow) {
		
		Cashflow obj = getCashflow(cashflow);
		
		if (obj != null) {
			
			cashflow.setId(obj.getId());
			cashflow = updateCashflow(cashflow);
			
		} else {
			
			cashflow = addCashflow(cashflow);
		}
		
		return cashflow;
	}
	
	public Cashflow addCashflow(Cashflow bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		em.persist(bean);
		em.close();

		return bean;
	}
	
	public Cashflow updateCashflow(Cashflow bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Cashflow cashflow = em.find(Cashflow.class, bean.getId());
		cashflow.setBean(bean);
		
		em.close();
		
		return cashflow;
	}
	
	public Cashflow getCashflow(Cashflow cashflow) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT c FROM Cashflow c ");
		sql.append(" WHERE merchant_id = :merchantId ");
		sql.append(" AND remote_id = :remoteId ");
		
		TypedQuery<Cashflow> query = em.createQuery(sql.toString(), Cashflow.class);
		
		query.setParameter("merchantId", cashflow.getMerchant_id());
		query.setParameter("remoteId", cashflow.getRemote_id());
		
		Cashflow result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	public List<Cashflow> getCashflows(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_CASHFLOW);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT c FROM Cashflow c WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		sql.append(" ORDER BY c.cash_date");
		
		TypedQuery<Cashflow> query = em.createQuery(sql.toString(), Cashflow.class);
		
		query.setParameter("merchantId", sync.getMerchant_id());
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		List<Cashflow> result = query.getResultList();
		
		em.close();

		return result;
	}
	
	public boolean hasUpdate(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_CASHFLOW);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT COUNT(c.id) FROM Cashflow c WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("merchantId", sync.getMerchant_id());
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		long count = (long) query.getSingleResult();
		
		em.close();

		return (count > 0);
	}
}