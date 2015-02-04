package com.app.posweb.server.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.model.Discount;

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
	
	public List<Discount> getDiscounts(Date lastSyncDate) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT d FROM Discount d WHERE update_date >= :lastSyncDate");
		
		sql.append(" ORDER BY d.name");
		
		TypedQuery<Discount> query = em.createQuery(sql.toString(), Discount.class);
		query.setParameter("lastSyncDate", lastSyncDate);
		
		List<Discount> result = query.getResultList();
		
		em.close();

		return result;
	}
}