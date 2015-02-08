package com.app.posweb.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.model.Merchant;
import com.app.posweb.server.model.SyncRequest;

public class MerchantDao {
	
	public Merchant syncMerchant(Merchant merchant) {
		
		Merchant obj = getMerchant(merchant);
		
		if (obj != null) {
			
			merchant.setId(obj.getId());
			merchant = updateMerchant(merchant);
			
		} else {
			
			merchant = addMerchant(merchant);
		}
		
		return merchant;
	}
	
	public Merchant addMerchant(Merchant bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		em.persist(bean);
		em.close();

		return bean;
	}
	
	public Merchant updateMerchant(Merchant bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Merchant merchant = em.find(Merchant.class, bean.getId());
		merchant.setBean(bean);
		
		em.close();
		
		return merchant;
	}
	
	public Merchant getMerchant(Merchant merchant) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT m FROM Merchant m ");
		sql.append(" WHERE remote_id = :remoteId ");
		
		TypedQuery<Merchant> query = em.createQuery(sql.toString(), Merchant.class);
		
		query.setParameter("remoteId", merchant.getRemote_id());
		
		Merchant result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	public List<Merchant> getMerchants(SyncRequest syncRequest) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT m FROM Merchant m WHERE update_date >= :lastSyncDate");
		
		sql.append(" ORDER BY m.name");
		
		TypedQuery<Merchant> query = em.createQuery(sql.toString(), Merchant.class);

		query.setParameter("lastSyncDate", syncRequest.getLast_sync_date());
		
		List<Merchant> result = query.getResultList();
		
		em.close();

		return result;
	}
}