package com.app.posweb.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.app.posweb.server.PersistenceManager;
import com.app.posweb.server.ServerUtil;
import com.app.posweb.server.model.Sync;

public class SyncDao {
	
	public Sync syncSync(Sync sync) {
		
		Sync obj = getSync(sync);
		
		if (obj != null) {
			
			sync.setId(obj.getId());
			sync = updateSync(sync);
			
		} else {
			
			sync = addSync(sync);
		}
		
		return sync;
	}
	
	public Sync addSync(Sync bean) {
		
		bean.setLast_sync_date(ServerUtil.getSystemStartDate());
		
		EntityManager em = PersistenceManager.getEntityManager();
		
		em.persist(bean);
		
		em.close();

		return bean;
	}
	
	public Sync updateSync(Sync bean) {

		EntityManager em = PersistenceManager.getEntityManager();
		
		Sync sync = em.find(Sync.class, bean.getId());
		sync.setBean(bean);
		
		em.close();
		
		return sync;
	}
	
	public Sync getSync(Sync sync) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT s FROM Sync s ");
		sql.append(" WHERE merchant_id = :merchantId ");
		sql.append(" AND sync_type = :syncType ");
		sql.append(" AND uuid = :uuid ");
		
		TypedQuery<Sync> query = em.createQuery(sql.toString(), Sync.class);
		
		query.setParameter("merchantId", sync.getMerchant_id());
		query.setParameter("syncType", sync.getSync_type());
		query.setParameter("uuid", sync.getUuid());
		
		Sync result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return result;
	}
	
	public Sync getSync(Long merchantId, String uuid, String syncType) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT s FROM Sync s ");
		sql.append(" WHERE merchant_id = :merchantId ");
		sql.append(" AND uuid = :uuid ");
		sql.append(" AND sync_type = :syncType ");
		
		TypedQuery<Sync> query = em.createQuery(sql.toString(), Sync.class);
		
		query.setParameter("merchantId", merchantId);
		query.setParameter("uuid", uuid);
		query.setParameter("syncType", syncType);
		
		Sync result = null;
		
		try {
			result = query.getSingleResult();
			
		} catch (NoResultException e) {
			
			Sync sync = new Sync();
			
			sync.setMerchant_id(merchantId);
			sync.setUuid(uuid);
			sync.setSync_type(syncType);
			sync.setLast_sync_date(ServerUtil.getSystemStartDate());
			
			result = addSync(sync);
		}
		
		em.close();

		return result;
	}
	
	public boolean isValidToken(Long merchantId, String uuid) {
		
		EntityManager em = PersistenceManager.getEntityManager();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT d FROM Sync d ");
		sql.append(" WHERE merchant_id = :merchantId ");
		sql.append(" AND uuid = :uuid ");
		
		TypedQuery<Sync> query = em.createQuery(sql.toString(), Sync.class);
		
		query.setParameter("merchantId", merchantId);
		query.setParameter("uuid", uuid);
		
		List<Sync> results = null;
		
		try {
			results = query.getResultList();
			
		} catch (NoResultException e) {
			// do nothing
		}
		
		em.close();

		return (!results.isEmpty());
	}
}
