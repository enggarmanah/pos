package com.app.posweb.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.model.UserAccess;
import com.app.posweb.server.model.SyncRequest;

public class UserAccessDao {
	
	public UserAccess syncUserAccess(UserAccess userAccess) {
		
		UserAccess obj = getUserAccess(userAccess);
		
		if (obj != null) {
			
			userAccess.setId(obj.getId());
			userAccess = updateUserAccess(userAccess);
			
		} else {
			
			userAccess = addUserAccess(userAccess);
		}
		
		return userAccess;
	}
	
	public UserAccess addUserAccess(UserAccess bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		em.persist(bean);
		em.close();

		return bean;
	}
	
	public UserAccess updateUserAccess(UserAccess bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		UserAccess userAccess = em.find(UserAccess.class, bean.getId());
		userAccess.setBean(bean);
		
		em.close();
		
		return userAccess;
	}
	
	public UserAccess getUserAccess(UserAccess userAccess) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT u FROM UserAccess u ");
		sql.append(" WHERE merchant_id = :merchantId ");
		sql.append(" AND remote_id = :remoteId ");
		
		TypedQuery<UserAccess> query = em.createQuery(sql.toString(), UserAccess.class);
		
		query.setParameter("merchantId", userAccess.getMerchant_id());
		query.setParameter("remoteId", userAccess.getRemote_id());
		
		UserAccess result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	public List<UserAccess> getUserAccesses(SyncRequest syncRequest) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT m FROM UserAccess m WHERE merchant_id = :merchantId AND update_date >= :lastSyncDate");
		
		sql.append(" ORDER BY m.id");
		
		TypedQuery<UserAccess> query = em.createQuery(sql.toString(), UserAccess.class);
		
		query.setParameter("merchantId", syncRequest.getMerchant_id());
		query.setParameter("lastSyncDate", syncRequest.getLast_sync_date());
		
		List<UserAccess> result = query.getResultList();
		
		em.close();

		return result;
	}
}