package com.app.posweb.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.model.Sync;
import com.app.posweb.server.model.SyncRequest;
import com.app.posweb.server.model.User;
import com.app.posweb.shared.Constant;

public class UserDao {
	
	SyncDao syncDao = new SyncDao();
	
	public User syncUser(User user) {
		
		User obj = getUser(user);
		
		if (obj != null) {
			
			user.setId(obj.getId());
			user = updateUser(user);
			
		} else {
			
			user = addUser(user);
		}
		
		return user;
	}
	
	public User addUser(User bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		em.persist(bean);
		em.close();

		return bean;
	}
	
	public User updateUser(User bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		User user = em.find(User.class, bean.getId());
		user.setBean(bean);
		
		em.close();
		
		return user;
	}
	
	public User getUser(User user) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT u FROM User u ");
		sql.append(" WHERE merchant_id = :merchantId ");
		sql.append(" AND remote_id = :remoteId ");
		
		TypedQuery<User> query = em.createQuery(sql.toString(), User.class);
		
		query.setParameter("merchantId", user.getMerchant_id());
		query.setParameter("remoteId", user.getRemote_id());
		
		User result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	public List<User> getUsers(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_USER);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT u FROM User u WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		sql.append(" ORDER BY u.name");
		
		TypedQuery<User> query = em.createQuery(sql.toString(), User.class);
		
		query.setParameter("merchantId", sync.getMerchant_id());
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		List<User> result = query.getResultList();
		
		em.close();

		return result;
	}
	
	public boolean hasUpdate(SyncRequest syncRequest) {
		
		Sync sync = syncDao.getSync(syncRequest.getMerchant_id(), syncRequest.getUuid(), Constant.SYNC_USER);
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		StringBuffer sql = new StringBuffer("SELECT COUNT(u.id) FROM User u WHERE merchant_id = :merchantId AND sync_date > :lastSyncDate");
		
		Query query = em.createQuery(sql.toString());
		
		query.setParameter("merchantId", sync.getMerchant_id());
		query.setParameter("lastSyncDate", sync.getLast_sync_date());
		
		long count = (long) query.getSingleResult();
		
		em.close();

		return (count > 0);
	}
}