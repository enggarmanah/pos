package com.app.posweb.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.model.MerchantAccess;
import com.app.posweb.server.model.SyncRequest;

public class MerchantAccessDao {
	
	public MerchantAccess syncMerchantAccess(MerchantAccess merchantAccess) {
		
		MerchantAccess obj = getMerchantAccess(merchantAccess);
		
		if (obj != null) {
			
			merchantAccess.setId(obj.getId());
			merchantAccess = updateMerchantAccess(merchantAccess);
			
		} else {
			
			merchantAccess = addMerchantAccess(merchantAccess);
		}
		
		return merchantAccess;
	}
	
	public MerchantAccess addMerchantAccess(MerchantAccess bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		em.persist(bean);
		em.close();

		return bean;
	}
	
	public MerchantAccess updateMerchantAccess(MerchantAccess bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		MerchantAccess merchantAccess = em.find(MerchantAccess.class, bean.getId());
		merchantAccess.setBean(bean);
		
		em.close();
		
		return merchantAccess;
	}
	
	public MerchantAccess getMerchantAccess(MerchantAccess merchantAccess) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT m FROM MerchantAccess m ");
		sql.append(" WHERE merchant_id = :merchantId ");
		sql.append(" AND remote_id = :remoteId ");
		
		TypedQuery<MerchantAccess> query = em.createQuery(sql.toString(), MerchantAccess.class);
		
		query.setParameter("merchantId", merchantAccess.getMerchant_id());
		query.setParameter("remoteId", merchantAccess.getRemote_id());
		
		MerchantAccess result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	public List<MerchantAccess> getMerchantAccesses(SyncRequest syncRequest) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT m FROM MerchantAccess m WHERE merchant_id = :merchantId AND update_date >= :lastSyncDate");
		
		sql.append(" ORDER BY m.id");
		
		TypedQuery<MerchantAccess> query = em.createQuery(sql.toString(), MerchantAccess.class);
		
		query.setParameter("merchantId", syncRequest.getMerchant_id());
		query.setParameter("lastSyncDate", syncRequest.getLast_sync_date());
		
		List<MerchantAccess> result = query.getResultList();
		
		em.close();

		return result;
	}
}