package com.app.posweb.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.model.Sync;
import com.app.posweb.server.model.UserAccess;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.shared.Constant;

public class UserAccessDao {
	
	SyncDao syncDao = new SyncDao();
	
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
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_USER_ACCESS);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT u FROM UserAccess u WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		sql.append(" ORDER BY u.id");
		
		TypedQuery<UserAccess> query = em.createQuery(sql.toString(), UserAccess.class);
		
		query.setParameter("merchantId", sync.getMerchant_id());
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		List<UserAccess> result = query.setFirstResult((int) syncRequest.getIndex()).setMaxResults(Constant.SYNC_RECORD_LIMIT).getResultList();
		
		em.close();

		return result;
	}
		
	public boolean hasUpdate(SyncRequest syncRequest) {
		
		return getUserAccessesCount(syncRequest) > 0;
	}
	
	public long getUserAccessesCount(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_USER_ACCESS);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT COUNT(u.id) FROM UserAccess u WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("merchantId", sync.getMerchant_id());
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		long count = (long) query.getSingleResult();
		
		em.close();

		return count;
	}
}