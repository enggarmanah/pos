package com.app.posweb.server.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.model.Discount;
import com.app.posweb.server.model.SyncRequest;

public class DiscountDao {
	
	public Discount syncDiscount(Discount discount) {
		
		Discount obj = getDiscount(discount);
		
		if (obj != null) {
			
			discount.setId(obj.getId());
			discount = updateDiscount(discount);
			
		} else {
			
			discount = addDiscount(discount);
		}
		
		return discount;
	}
	
	public Discount addDiscount(Discount bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		em.persist(bean);
		em.close();

		return bean;
	}
	
	public Discount updateDiscount(Discount bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Discount discount = em.find(Discount.class, bean.getId());
		discount.setBean(bean);
		
		em.close();
		
		return discount;
	}
	
	public Discount getDiscount(Discount discount) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT d FROM Discount d ");
		sql.append(" WHERE merchant_id = :merchantId ");
		sql.append(" AND remote_id = :remoteId ");
		
		TypedQuery<Discount> query = em.createQuery(sql.toString(), Discount.class);
		
		query.setParameter("merchantId", discount.getMerchant_id());
		query.setParameter("remoteId", discount.getRemote_id());
		
		Discount result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	public List<Discount> getDiscounts(SyncRequest syncRequest) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT d FROM Discount d WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		sql.append(" ORDER BY d.name");
		
		TypedQuery<Discount> query = em.createQuery(sql.toString(), Discount.class);
		
		query.setParameter("merchantId", syncRequest.getMerchant_id());
		query.setParameter("lastSyncDate", syncRequest.getLast_sync_date());
		
		List<Discount> result = query.getResultList();
		
		em.close();

		return result;
	}
	
	public boolean hasUpdate(SyncRequest syncRequest) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT COUNT(d.id) FROM Discount d WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
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
		
		sql.append("UPDATE Discount d SET sync_date = :syncDate WHERE merchant_id = :merchantId AND sync_date = :lastSyncDate ");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("merchantId", syncRequest.getMerchant_id());
		query.setParameter("lastSyncDate", syncRequest.getSync_date());
		query.setParameter("syncDate", syncDate);
		
		query.executeUpdate();
		
		em.close();
	}
}