package com.app.posweb.server.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.model.Bills;
import com.app.posweb.server.model.SyncRequest;

public class BillDao {
	
	public Bills syncBills(Bills bill) {
		
		Bills obj = getBills(bill);
		
		if (obj != null) {
			
			bill.setId(obj.getId());
			bill = updateBills(bill);
			
		} else {
			
			bill = addBills(bill);
		}
		
		return bill;
	}
	
	public Bills addBills(Bills bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		em.persist(bean);
		em.close();

		return bean;
	}
	
	public Bills updateBills(Bills bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Bills bill = em.find(Bills.class, bean.getId());
		bill.setBean(bean);
		
		em.close();
		
		return bill;
	}
	
	public Bills getBills(Bills bill) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT c FROM Bills c ");
		sql.append(" WHERE merchant_id = :merchantId ");
		sql.append(" AND remote_id = :remoteId ");
		
		TypedQuery<Bills> query = em.createQuery(sql.toString(), Bills.class);
		
		query.setParameter("merchantId", bill.getMerchant_id());
		query.setParameter("remoteId", bill.getRemote_id());
		
		Bills result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	public List<Bills> getBills(SyncRequest syncRequest) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT c FROM Bills c WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		sql.append(" ORDER BY bill_date");
		
		TypedQuery<Bills> query = em.createQuery(sql.toString(), Bills.class);
		
		query.setParameter("merchantId", syncRequest.getMerchant_id());
		query.setParameter("lastSyncDate", syncRequest.getLast_sync_date());
		
		List<Bills> result = query.getResultList();
		
		em.close();

		return result;
	}
	
	public boolean hasUpdate(SyncRequest syncRequest) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT COUNT(c.id) FROM Bills c WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("merchantId", syncRequest.getMerchant_id());
		query.setParameter("lastSyncDate", syncRequest.getLast_sync_date());
		
		long count = (long) query.getSingleResult();
		
		em.close();

		return (count > 0);
	}
	
	public void updateSyncDate(SyncRequest syncRequest, Date syncDate) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("UPDATE Bills b SET sync_date = :syncDate WHERE merchant_id = :merchantId AND sync_date = :lastSyncDate ");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("merchantId", syncRequest.getMerchant_id());
		query.setParameter("lastSyncDate", syncRequest.getSync_date());
		query.setParameter("syncDate", syncDate);
		
		query.executeUpdate();
		
		em.close();
	}
}